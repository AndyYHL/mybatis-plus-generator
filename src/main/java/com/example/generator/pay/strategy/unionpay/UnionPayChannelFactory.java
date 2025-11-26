package com.example.generator.pay.strategy.unionpay;

import com.example.generator.pay.config.RateConfig;
import com.example.generator.pay.factory.AbstractPayChannelFactory;
import com.example.generator.pay.strategy.PayStrategy;
import com.example.generator.pojo.enums.PayChannel;
import com.example.generator.pojo.enums.RateTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * UnionPayChannelFactory描述:银联通道
 * <p>
 * 包名称：com.example.generator.pay.strategy.unionpay
 * 类名称：UnionPayChannelFactory
 * 全路径：com.example.generator.pay.strategy.unionpay.UnionPayChannelFactory
 * 类描述：银联通道
 *
 * @author 31756-YHL
 * @date 2025年11月26日 11:06
 */
@Component
public class UnionPayChannelFactory extends AbstractPayChannelFactory {
    @Autowired
    private UnionPayStrategy unionPayStrategy;

    @Override
    public PayChannel getPayChannel() {
        return PayChannel.UNION_PAY;
    }

    @Override
    public PayStrategy createPayStrategy() {
        return unionPayStrategy;
    }

    @Override
    public RateConfig getRateConfig() {
        RateConfig config = new RateConfig();
        config.setChannelCode("union");
        config.setRateType(RateTypeEnum.LADDER); // 阶梯费率
        config.setLadderConfig(
                "[{\"min\":0,\"max\":1000,\"rate\":0.65}," +
                        "{\"min\":1000,\"max\":10000,\"rate\":0.58}," +
                        "{\"min\":10000,\"max\":999999999,\"rate\":0.5}]"
        );
        config.setPriority(3);
        config.setEnabled(true);
        return config;
    }
}