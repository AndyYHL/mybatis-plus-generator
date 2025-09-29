package com.example.generator.web.aspect;

import cn.hutool.core.text.CharSequenceUtil;
import com.example.generator.pojo.annotation.RateLimiter;
import com.example.generator.pojo.enums.BasicRespCode;
import com.example.generator.pojo.exception.BasicException;
import com.example.generator.service.impl.RateLimiterRedisService;
import com.example.generator.service.standard.RateLimiterKeyResolver;
import com.example.generator.util.CollectionToolUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * RateLimiterAspect描述:限流拦截器
 * <p>
 * 包名称：com.example.generator.web.aspect
 * 类名称：RateLimiterAspect
 * 全路径：com.example.generator.web.aspect.RateLimiterAspect
 * 类描述：限流拦截器
 *
 * @author Administrator-YHL
 * @date 2025年09月29日 09:47
 */
@Slf4j
@Aspect
public class RateLimiterAspect {
    /**
     * RateLimiterKeyResolver 集合
     */
    private final Map<Class<? extends RateLimiterKeyResolver>, RateLimiterKeyResolver> keyResolvers;

    private final RateLimiterRedisService rateLimiterRedisDAO;

    public RateLimiterAspect(List<RateLimiterKeyResolver> keyResolvers, RateLimiterRedisService rateLimiterRedisDAO) {
        this.keyResolvers = CollectionToolUtils.convertMap(keyResolvers, RateLimiterKeyResolver::getClass);
        this.rateLimiterRedisDAO = rateLimiterRedisDAO;
    }

    @Before("@annotation(rateLimiter)")
    public void beforePointCut(JoinPoint joinPoint, RateLimiter rateLimiter) {
        // 获得 IdempotentKeyResolver 对象
        RateLimiterKeyResolver keyResolver = keyResolvers.get(rateLimiter.keyResolver());
        Assert.notNull(keyResolver, "找不到对应的 RateLimiterKeyResolver");
        // 解析 Key
        String key = keyResolver.resolver(joinPoint, rateLimiter);

        // 获取 1 次限流
        boolean success = rateLimiterRedisDAO.tryAcquire(key,
                rateLimiter.count(), rateLimiter.time(), rateLimiter.timeUnit());
        if (!success) {
            log.info("[beforePointCut][方法({}) 参数({}) 请求过于频繁]", joinPoint.getSignature().toString(), joinPoint.getArgs());
            String message = CharSequenceUtil.blankToDefault(rateLimiter.message(),
                    BasicRespCode.TOO_MANY_REQUESTS.getDesc());
            throw new BasicException(BasicRespCode.TOO_MANY_REQUESTS.getCode(), "请求过于频繁，请稍后重试：" + message);
        }
    }
}