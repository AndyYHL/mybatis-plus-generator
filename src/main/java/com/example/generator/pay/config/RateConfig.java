package com.example.generator.pay.config;

import com.example.generator.pojo.enums.RateTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * RateConfig描述:费率
 * <p>
 * 包名称：com.example.generator.pay.config
 * 类名称：RateConfig
 * 全路径：com.example.generator.pay.config.RateConfig
 * 类描述：费率
 *
 * @author 31756-YHL
 * @date 2025年11月26日 10:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateConfig {
    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 费率类型：FIXED-固定费率, PERCENT-百分比费率, LADDER-阶梯费率
     */
    private RateTypeEnum rateType;

    /**
     * 费率值(百分比: 0.6 表示 0.6%, 固定: 2 表示 2元)
     */
    private BigDecimal rateValue;

    /**
     * 最低手续费
     */
    private BigDecimal minFee;

    /**
     * 最高手续费
     */
    private BigDecimal maxFee;

    /**
     * 优先级(数值越小优先级越高)
     */
    private Integer priority;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 阶梯费率配置(JSON格式)
     */
    private String ladderConfig;
}