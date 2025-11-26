package com.example.generator.pay.factory;

import com.example.generator.pay.config.RateConfig;
import com.example.generator.pay.strategy.PayStrategy;
import com.example.generator.pojo.enums.PayChannel;

/**
 * <p>
 * AbstractPayChannelFactory描述:工厂
 * <p>
 * 包名称：com.example.generator.pay.factory
 * 类名称：AbstractPayChannelFactory
 * 全路径：com.example.generator.pay.factory.AbstractPayChannelFactory
 * 类描述：工厂
 *
 * @author 31756-YHL
 * @date 2025年11月26日 11:01
 */
public abstract class AbstractPayChannelFactory {
    /**
     * 获取支付渠道类型
     */
    public abstract PayChannel getPayChannel();

    /**
     * 创建支付策略
     */
    public abstract PayStrategy createPayStrategy();

    /**
     * 获取费率配置
     */
    public abstract RateConfig getRateConfig();

    /**
     * 渠道健康检查
     */
    public boolean healthCheck() {
        return true;
    }
}