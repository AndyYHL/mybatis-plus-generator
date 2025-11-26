package com.example.generator.pay.selector;

import com.example.generator.pay.config.RouteConfig;
import com.example.generator.pojo.exception.BasicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>
 * PaymentRuleEngine描述:规则引擎
 * <p>
 * 包名称：com.example.generator.pay.selector
 * 类名称：PaymentRuleEngine
 * 全路径：com.example.generator.pay.selector.PaymentRuleEngine
 * 类描述：规则引擎
 *
 * @author 31756-YHL
 * @date 2025年11月26日 10:59
 */
@Component
@Slf4j
public class PaymentRuleEngine {
    /**
     * 应用路由规则
     * 规则优先级:
     * 1. 黑名单/白名单过滤
     * 2. 金额范围限制
     * 3. 费率最优选择
     * 4. 渠道优先级
     * 5. 负载均衡
     */
    public RouteConfig applyRoutingRules(List<RouteConfig> routes,
                                         BigDecimal amount) {
        return routes.stream()
                // 过滤不可用渠道
                .filter(r -> r.getRateConfig().getEnabled())
                // 按实际费用升序排序
                .sorted(Comparator.comparing(RouteConfig::getActualFee)
                        .thenComparing(r -> r.getRateConfig().getPriority()))
                // 选择最优渠道
                .findFirst()
                .orElseThrow(() -> new BasicException("无可用支付渠道"));
    }

    /**
     * 负载均衡策略(加权轮询)
     */
    public RouteConfig applyLoadBalancing(List<RouteConfig> candidates) {
        // 在费用相同的情况下，使用权重进行负载均衡
        int totalWeight = candidates.stream()
                .mapToInt(RouteConfig::getWeight)
                .sum();
        int random = ThreadLocalRandom.current().nextInt(totalWeight);

        int currentWeight = 0;
        for (RouteConfig route : candidates) {
            currentWeight += route.getWeight();
            if (random < currentWeight) {
                route.setRouteReason("负载均衡选择");
                return route;
            }
        }
        return candidates.get(0);
    }
}