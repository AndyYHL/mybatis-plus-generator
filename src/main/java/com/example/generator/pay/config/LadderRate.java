package com.example.generator.pay.config;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * LadderRate描述:阶梯
 * <p>
 * 包名称：com.example.generator.pay.config
 * 类名称：LadderRate
 * 全路径：com.example.generator.pay.config.LadderRate
 * 类描述：阶梯
 *
 * @author 31756-YHL
 * @date 2025年11月26日 11:12
 */
@Data
public class LadderRate {
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal rate;
}