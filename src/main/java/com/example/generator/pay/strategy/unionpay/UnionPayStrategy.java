package com.example.generator.pay.strategy.unionpay;

import com.example.generator.pay.strategy.AbstractPayStrategy;
import com.example.generator.pojo.constant.Constants;
import com.example.generator.pojo.enums.PayChannel;
import com.example.generator.pojo.request.NotifyRequest;
import com.example.generator.pojo.request.PayRequest;
import com.example.generator.pojo.request.RefundRequest;
import com.example.generator.pojo.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>
 * UnionPayStrategy描述:云闪付
 * <p>
 * 包名称：com.example.generator.pay.strategy.unionpay
 * 类名称：UnionPayStrategy
 * 全路径：com.example.generator.pay.strategy.unionpay.UnionPayStrategy
 * 类描述：云闪付
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:51
 */
@Slf4j
@Component
public class UnionPayStrategy extends AbstractPayStrategy {
    @Override
    protected PayResponse doPay(PayRequest request) {
        try {
            // TODO 业务实现
            return PayResponse.failure(Constants.OPERATE_ERROR_MSG, Constants.OPERATE_ERROR_MSG + "云闪付支付失败");
        } catch (Exception e) {
            throw new RuntimeException("云闪付支付失败", e);
        }
    }

    @Override
    protected QueryResponse doQueryOrder(PayRequest request) {
        try {
            // TODO 业务实现
            return QueryResponse.failure(Constants.OPERATE_ERROR_MSG, Constants.OPERATE_ERROR_MSG + "云闪付查询失败");
        } catch (Exception e) {
            throw new RuntimeException("云闪付查询失败", e);
        }
    }

    @Override
    protected CloseResponse doClose(PayRequest request) {
        try {
            // TODO 业务实现
            return CloseResponse.failure(Constants.OPERATE_ERROR_MSG, Constants.OPERATE_ERROR_MSG + "云闪付关单失败");
        } catch (Exception e) {
            throw new RuntimeException("云闪付关单失败", e);
        }
    }

    @Override
    protected RefundResponse doRefund(RefundRequest request) {
        try {
            // TODO 业务实现
            return RefundResponse.failure(Constants.OPERATE_ERROR_MSG, Constants.OPERATE_ERROR_MSG + "云闪付退款失败");
        } catch (Exception e) {
            throw new RuntimeException("云闪付退款失败", e);
        }
    }

    @Override
    protected RefundResponse doQueryRefund(RefundRequest request) {
        try {
            // TODO 业务实现
            return RefundResponse.failure(Constants.OPERATE_ERROR_MSG, Constants.OPERATE_ERROR_MSG + "云闪付查询退款失败");
        } catch (Exception e) {
            throw new RuntimeException("云闪付查询退款失败", e);
        }
    }

    @Override
    public PayChannel getPayChannel() {
        return PayChannel.UNION_PAY;
    }

    @Override
    public NotifyResponse handleNotify(NotifyRequest request) {
        try {
            // TODO 业务实现
            return NotifyResponse.failure(Constants.OPERATE_ERROR_MSG, Constants.OPERATE_ERROR_MSG + "云闪付回调失败");
        } catch (Exception e) {
            throw new RuntimeException("云闪付回调失败", e);
        }
    }
}