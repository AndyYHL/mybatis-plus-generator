package com.example.generator.pay.strategy;

import com.example.generator.pojo.constant.Constants;
import com.example.generator.pojo.request.PayRequest;
import com.example.generator.pojo.request.RefundRequest;
import com.example.generator.pojo.response.CloseResponse;
import com.example.generator.pojo.response.PayResponse;
import com.example.generator.pojo.response.QueryResponse;
import com.example.generator.pojo.response.RefundResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * AbstractPayStrategy描述:抽象
 * <p>
 * 包名称：com.example.generator.pay.strategy
 * 类名称：AbstractPayStrategy
 * 全路径：com.example.generator.pay.strategy.AbstractPayStrategy
 * 类描述：抽象
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:35
 */
@Slf4j
public abstract class AbstractPayStrategy implements PayStrategy {
    @Override
    public PayResponse pay(PayRequest request) {
        try {
            log.info("开始支付，渠道：{}，订单号：{}", getPayChannel().getName(), request.getOrderNo());
            return doPay(request);
        } catch (Exception e) {
            log.error("支付异常，渠道：{}，订单号：{}", getPayChannel().getName(), request.getOrderNo(), e);
            return PayResponse.failure(Constants.OPERATE_ERROR_MSG, "支付处理异常：" + e.getMessage());
        }
    }

    @Override
    public QueryResponse queryOrder(PayRequest request) {
        try {
            log.info("查询订单，渠道：{}，订单号：{}", getPayChannel().getName(), request.getOrderNo());
            return doQueryOrder(request);
        } catch (Exception e) {
            log.error("查询订单异常，渠道：{}，订单号：{}", getPayChannel().getName(), request.getOrderNo(), e);
            return QueryResponse.failure(Constants.OPERATE_ERROR_MSG, "查询订单异常：" + e.getMessage());
        }
    }

    @Override
    public CloseResponse close(PayRequest request) {
        try {
            log.info("关闭订单，渠道：{}，订单号：{}", getPayChannel().getName(), request.getOrderNo());
            return doClose(request);
        } catch (Exception e) {
            log.error("关闭订单异常，渠道：{}，订单号：{}", getPayChannel().getName(), request.getOrderNo(), e);
            return CloseResponse.failure(Constants.OPERATE_ERROR_MSG, "关闭订单异常：" + e.getMessage());
        }
    }

    @Override
    public RefundResponse refund(RefundRequest request) {
        try {
            log.info("开始退款，渠道：{}，退款单号：{}", getPayChannel().getName(), request.getRefundNo());
            return doRefund(request);
        } catch (Exception e) {
            log.error("退款异常，渠道：{}，退款单号：{}", getPayChannel().getName(), request.getRefundNo(), e);
            return RefundResponse.failure(Constants.OPERATE_ERROR_MSG, "退款处理异常：" + e.getMessage());
        }
    }

    @Override
    public RefundResponse queryRefund(RefundRequest request) {
        try {
            log.info("开始退款查询，渠道：{}，退款单号：{}", getPayChannel().getName(), request.getRefundNo());
            return doQueryRefund(request);
        } catch (Exception e) {
            log.error("退款查询异常，渠道：{}，退款单号：{}", getPayChannel().getName(), request.getRefundNo(), e);
            return RefundResponse.failure(Constants.OPERATE_ERROR_MSG, "退款查询处理异常：" + e.getMessage());
        }
    }

    protected abstract PayResponse doPay(PayRequest request);

    protected abstract QueryResponse doQueryOrder(PayRequest request);

    protected abstract CloseResponse doClose(PayRequest request);

    protected abstract RefundResponse doRefund(RefundRequest request);

    protected abstract RefundResponse doQueryRefund(RefundRequest request);
}