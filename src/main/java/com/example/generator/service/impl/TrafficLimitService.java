package com.example.generator.service.impl;

import com.example.generator.pojo.props.TrafficLimitProps;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * TrafficLimitService描述:流量控制服务
 * 基于Redisson的RRateLimiter实现分布式令牌桶限流
 * <p>
 * 包名称：com.example.generator.service.impl
 * 类名称：TrafficLimitService
 * 全路径：com.example.generator.service.impl.TrafficLimitService
 * 类描述：流量控制服务
 *
 * @author Administrator-YHL
 * @date 2025年09月29日 10:24
 */
@Slf4j
@Service
public class TrafficLimitService {
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private TrafficLimitProps trafficLimitProps;

    /**
     * 用于缓存RRateLimiter对象的Map，避免重复创建
     */
    private final ConcurrentHashMap<String, RRateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    /**
     * 尝试获取1个令牌，默认超时时间100ms
     *
     * @param appId 应用ID
     * @return 是否获取成功
     */
    public boolean tryAcquire(String appId) {
        return tryAcquire(appId, 1, 100, TimeUnit.MILLISECONDS);
    }

    /**
     * 尝试获取指定数量的令牌
     *
     * @param appId     应用ID
     * @param permits   要获取的令牌数量
     * @param timeout   超时时间
     * @param timeUnit  时间单位
     * @return 是否获取成功
     */
    public boolean tryAcquire(String appId, int permits, long timeout, TimeUnit timeUnit) {
        if (appId == null || appId.isEmpty()) {
            appId = "default";
        }

        RRateLimiter rateLimiter = getRateLimiter(appId);
        try {
            // 尝试获取令牌
            return rateLimiter.tryAcquire(permits, timeout, timeUnit);
        } catch (Exception e) {
            log.error("获取令牌时发生异常, appId: {}, 错误: {}", appId, e.getMessage(), e);
            // 异常时默认放行，保证系统可用性
            return true;
        }
    }

    /**
     * 获取或创建RRateLimiter实例
     *
     * @param appId 应用ID
     * @return RRateLimiter实例
     */
    private RRateLimiter getRateLimiter(String appId) {
        return rateLimiterMap.computeIfAbsent(appId, key -> {
            //String rateLimiterKey = TrafficLimitProps.getTrafficLimitKey(key);
            String rateLimiterKey = trafficLimitProps.getKey();
            RRateLimiter rateLimiter = redissonClient.getRateLimiter(rateLimiterKey);

            // 从配置中获取该应用的令牌桶参数
            /*int rate = TrafficLimitProps.getRate(key);
            int capacity = TrafficLimitProps.getCapacity(key);*/
            int rate = trafficLimitProps.getRate();
            int capacity = trafficLimitProps.getCapacity();

            try {
                // 尝试设置令牌桶的速率和容量
                // RateType.OVERALL: 所有实例共享同一个速率
                boolean initialized = rateLimiter.trySetRate(RateType.OVERALL, rate, capacity, RateIntervalUnit.SECONDS);
                if (initialized) {
                    log.info("成功初始化令牌桶, appId: {}, 恢复速率: {}/s, 桶容量: {}", key, rate, capacity);
                }
            } catch (Exception e) {
                log.error("初始化令牌桶失败, appId: {}, 错误: {}", key, e.getMessage(), e);
            }
            return rateLimiter;
        });
    }

    /**
     * 使用示例
     * private boolean checkTrafficLimit(String appId) {
     *     // 尝试获取1个令牌
     *     boolean acquired = trafficLimitService.tryAcquire(appId);
     *
     *     if (!acquired) {
     *         log.warn("流量限制被触发, appId: {}", appId);
     *         // 这里可以返回429 Too Many Requests
     *     }
     *
     *     return acquired;
     * }
     */
}