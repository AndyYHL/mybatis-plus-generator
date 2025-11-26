package com.example.generator.pay.strategy.alipay;

import com.example.generator.pay.config.RateConfig;
import com.example.generator.pay.factory.AbstractPayChannelFactory;
import com.example.generator.pay.strategy.PayStrategy;
import com.example.generator.pojo.enums.PayChannel;
import com.example.generator.pojo.enums.RateTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * <p>
 * AliPayChannelFactory描述:支付宝渠道工厂
 * <p>
 * 包名称：com.example.generator.pay.strategy.alipay
 * 类名称：AliPayChannelFactory
 * 全路径：com.example.generator.pay.strategy.alipay.AliPayChannelFactory
 * 类描述：支付宝渠道工厂
 *
 * @author 31756-YHL
 * @date 2025年11月26日 11:04
 */
@Component
public class AliPayChannelFactory extends AbstractPayChannelFactory {
    @Autowired
    private AlipayStrategy aliPayStrategy;

    @Override
    public PayChannel getPayChannel() {
        return PayChannel.ALI_PAY;
    }

    @Override
    public PayStrategy createPayStrategy() {
        return aliPayStrategy;
    }

    @Override
    public RateConfig getRateConfig() {
        RateConfig config = new RateConfig();
        config.setChannelCode("alipay");
        config.setRateType(RateTypeEnum.PERCENT);
        config.setRateValue(new BigDecimal("0.55")); // 0.55%
        config.setMinFee(new BigDecimal("0.01"));
        config.setPriority(2);
        config.setEnabled(true);
        return config;
    }
}