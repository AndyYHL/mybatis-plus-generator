package com.example.generator.pay.strategy.wechat;

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
 * WechatPayChannelFactory描述:微信渠道
 * <p>
 * 包名称：com.example.generator.pay.factory
 * 类名称：WechatPayChannelFactory
 * 全路径：com.example.generator.pay.strategy.wechat.WechatPayChannelFactory
 * 类描述：微信渠道
 *
 * @author 31756-YHL
 * @date 2025年11月26日 11:01
 */
@Component
public class WechatPayChannelFactory extends AbstractPayChannelFactory {
    @Autowired
    private WechatPayStrategy wechatPayStrategy;

    @Override
    public PayChannel getPayChannel() {
        return PayChannel.WECHAT_PAY;
    }

    @Override
    public PayStrategy createPayStrategy() {
        return wechatPayStrategy;
    }

    @Override
    public RateConfig getRateConfig() {
        // 从数据库或配置中心加载费率配置
        RateConfig config = new RateConfig();
        config.setChannelCode("wechat");
        config.setRateType(RateTypeEnum.PERCENT);
        config.setRateValue(new BigDecimal("0.6")); // 0.6%
        config.setMinFee(new BigDecimal("0.01"));
        config.setPriority(1);
        config.setEnabled(true);
        return config;
    }
}