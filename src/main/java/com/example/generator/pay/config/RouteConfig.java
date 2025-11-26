package com.example.generator.pay.config;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * RouteConfig描述:支付路由
 * <p>
 * 包名称：com.example.generator.pay.config
 * 类名称：RouteConfig
 * 全路径：com.example.generator.pay.config.RouteConfig
 * 类描述：支付路由
 *
 * @author 31756-YHL
 * @date 2025年11月26日 10:56
 */
@Data
public class RouteConfig {
    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 渠道权重(用于负载均衡)
     */
    private Integer weight;

    /**
     * 计算后的实际费率
     */
    private BigDecimal actualFee;

    /**
     * 原始费率配置
     */
    private RateConfig rateConfig;

    /**
     * 路由原因
     */
    private String routeReason;
}