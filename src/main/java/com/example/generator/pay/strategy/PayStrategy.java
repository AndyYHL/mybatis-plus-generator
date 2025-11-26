package com.example.generator.pay.strategy;

import com.example.generator.pojo.enums.PayChannel;
import com.example.generator.pojo.request.NotifyRequest;
import com.example.generator.pojo.request.PayRequest;
import com.example.generator.pojo.request.RefundRequest;
import com.example.generator.pojo.response.*;

/**
 * <p>
 * PayStrategy描述:支付策略
 * <p>
 * 包名称：com.example.generator.pay.strategy
 * 类名称：PayStrategy
 * 全路径：com.example.generator.pay.strategy.PayStrategy
 * 类描述：支付策略
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:34
 */
public interface PayStrategy {
    /**
     * 获取支付渠道
     */
    PayChannel getPayChannel();

    /**
     * 统一支付
     */
    PayResponse pay(PayRequest request);

    /**
     * 查询订单
     */
    QueryResponse queryOrder(PayRequest request);

    /**
     * 关闭订单
     */
    CloseResponse close(PayRequest request);

    /**
     * 退款
     */
    RefundResponse refund(RefundRequest request);

    /**
     * 退款查询
     */
    RefundResponse queryRefund(RefundRequest request);

    /**
     * 处理回调
     */
    NotifyResponse handleNotify(NotifyRequest request);
}