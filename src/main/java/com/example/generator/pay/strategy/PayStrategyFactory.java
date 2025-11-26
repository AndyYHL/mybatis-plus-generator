package com.example.generator.pay.strategy;

import com.example.generator.pojo.enums.PayChannel;
import com.example.generator.pojo.exception.BasicException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * PayStrategyFactory描述:支付
 * <p>
 * 包名称：com.example.generator.pay.strategy
 * 类名称：PayStrategyFactory
 * 全路径：com.example.generator.pay.strategy.PayStrategyFactory
 * 类描述：支付
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:53
 */
@Component
public class PayStrategyFactory {
    private final Map<PayChannel, PayStrategy> strategyMap = new ConcurrentHashMap<>();

    public PayStrategyFactory(List<PayStrategy> strategies) {
        for (PayStrategy strategy : strategies) {
            strategyMap.put(strategy.getPayChannel(), strategy);
        }
    }

    public PayStrategy getStrategy(PayChannel payChannel) {
        PayStrategy strategy = strategyMap.get(payChannel);
        if (strategy == null) {
            throw new BasicException("不支持的支付渠道：" + payChannel.getName());
        }
        return strategy;
    }

    public PayStrategy getStrategy(String channelCode) throws Throwable {
        PayChannel payChannel = PayChannel.findByCodeEnum(channelCode);
        if (payChannel == null) {
            throw new BasicException("不支持的支付渠道代码：" + channelCode);
        }
        return getStrategy(payChannel);
    }
}