package com.example.generator.pay.service;

import com.example.generator.pay.strategy.PayStrategy;
import com.example.generator.pay.strategy.PayStrategyFactory;
import com.example.generator.pojo.enums.PayChannel;
import com.example.generator.pojo.request.NotifyRequest;
import com.example.generator.pojo.request.PayRequest;
import com.example.generator.pojo.request.RefundRequest;
import com.example.generator.pojo.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * PayService描述:支付
 * <p>
 * 包名称：com.example.generator.pay.service
 * 类名称：PayService
 * 全路径：com.example.generator.pay.service.PayService
 * 类描述：支付
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:56
 */
@Slf4j
@Service
public class AggregatePay {
    @Autowired
    private PayStrategyFactory payStrategyFactory;

    /**
     * @Description： 支付
     * @Date： 2025/11/21
     * @Param： [request]
     **/
    public PayResponse pay(PayRequest request) {
        PayStrategy strategy = payStrategyFactory.getStrategy(request.getPayChannel());
        return strategy.pay(request);
    }

    /**
     * @Description： 查询订单
     * @Date： 2025/11/21
     * @Param： [request]
     **/
    public QueryResponse queryOrder(PayRequest request) {
        PayStrategy strategy = payStrategyFactory.getStrategy(request.getPayChannel());
        return strategy.queryOrder(request);
    }

    /**
     * @Description： 关闭订单
     * @Date： 2025/11/21
     * @Param： [request]
     **/
    public CloseResponse close(PayRequest request) {
        PayStrategy strategy = payStrategyFactory.getStrategy(request.getPayChannel());
        return strategy.close(request);
    }

    /**
     * @Description： 退款
     * @Date： 2025/11/21
     * @Param： [payChannel, request]
     **/
    public RefundResponse refund(PayChannel payChannel, RefundRequest request) {
        PayStrategy strategy = payStrategyFactory.getStrategy(payChannel);
        return strategy.refund(request);
    }

    /**
     * @Description： 退款查询
     * @Date： 2025/11/21
     * @Param： [payChannel, request]
     **/
    public RefundResponse queryRefund(PayChannel payChannel, RefundRequest request) {
        PayStrategy strategy = payStrategyFactory.getStrategy(payChannel);
        return strategy.queryRefund(request);
    }

    /**
     * @Description： 处理回调
     * @Date： 2025/11/21
     * @Param： [request]
     **/
    public NotifyResponse handleNotify(NotifyRequest request) {
        PayStrategy strategy = payStrategyFactory.getStrategy(request.getPayChannel());
        return strategy.handleNotify(request);
    }
}