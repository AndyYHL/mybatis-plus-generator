package com.example.generator.pay.selector;

import com.alibaba.fastjson2.JSON;
import com.example.generator.pay.config.LadderRate;
import com.example.generator.pay.config.RateConfig;
import com.example.generator.pay.config.RouteConfig;
import com.example.generator.pay.service.RateConfigService;
import com.example.generator.pojo.enums.PayChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * PaymentRouteSelector描述:支付路由选择
 * <p>
 * 包名称：com.example.generator.pay.selector
 * 类名称：PaymentRouteSelector
 * 全路径：com.example.generator.pay.selector.PaymentRouteSelector
 * 类描述：支付路由选择
 *
 * @author 31756-YHL
 * @date 2025年11月26日 10:58
 */
@Service
@Slf4j
public class PaymentRouteSelector {
    @Autowired
    private RateConfigService rateConfigService;

    @Autowired
    private PaymentRuleEngine paymentRuleEngine;

    /**
     * 选择最优支付路由
     * @param amount 支付金额
     * @param availableChannels 可用渠道列表
     * @return 最优路由配置
     */
    public RouteConfig selectOptimalRoute(BigDecimal amount,
                                          List<PayChannel> availableChannels) {
        // 1. 获取所有渠道的费率配置
        List<RateConfig> rateConfigs = rateConfigService
                .getEnabledRateConfigs(availableChannels);

        // 2. 计算每个渠道的实际费用
        List<RouteConfig> routeConfigs = rateConfigs.stream()
                .map(config -> {
                    RouteConfig route = new RouteConfig();
                    route.setChannelCode(config.getChannelCode());
                    route.setRateConfig(config);
                    route.setActualFee(calculateFee(amount, config));
                    return route;
                })
                .collect(Collectors.toList());

        // 3. 应用路由规则引擎(费率优先、优先级、负载均衡等)
        RouteConfig selectedRoute = paymentRuleEngine
                .applyRoutingRules(routeConfigs, amount);

        log.info("费率路由选择结果: 金额={}, 渠道={}, 手续费={}",
                amount, selectedRoute.getChannelCode(), selectedRoute.getActualFee());

        return selectedRoute;
    }

    /**
     * 计算手续费
     */
    private BigDecimal calculateFee(BigDecimal amount, RateConfig config) {
        BigDecimal fee;
        switch (config.getRateType()) {
            case FIXED:
                fee = config.getRateValue();
                break;
            case PERCENT:
                fee = amount.multiply(config.getRateValue())
                        .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                break;
            case LADDER:
                fee = calculateLadderFee(amount, config.getLadderConfig());
                break;
            default:
                fee = BigDecimal.ZERO;
        }

        // 应用最低/最高限制
        if (config.getMinFee() != null && fee.compareTo(config.getMinFee()) < 0) {
            fee = config.getMinFee();
        }
        if (config.getMaxFee() != null && fee.compareTo(config.getMaxFee()) > 0) {
            fee = config.getMaxFee();
        }

        return fee;
    }

    /**
     * 阶梯费率计算
     */
    private BigDecimal calculateLadderFee(BigDecimal amount, String ladderConfig) {
        // 解析阶梯配置: [{"min":0,"max":1000,"rate":0.6}, ...]
        List<LadderRate> ladders = JSON.parseArray(ladderConfig, LadderRate.class);
        return ladders.stream()
                .filter(l -> amount.compareTo(l.getMin()) >= 0
                        && amount.compareTo(l.getMax()) < 0)
                .findFirst()
                .map(l -> amount.multiply(l.getRate())
                        .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP))
                .orElse(BigDecimal.ZERO);
    }
}