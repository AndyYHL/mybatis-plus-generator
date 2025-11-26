package com.example.generator.pay.strategy.wechat;

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
 * WechatPayStrategy描述:微信支付
 * <p>
 * 包名称：com.example.generator.pay.strategy.wechat
 * 类名称：WechatPayStrategy
 * 全路径：com.example.generator.pay.strategy.wechat.WechatPayStrategy
 * 类描述：微信支付
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:38
 */
@Slf4j
@Component
public class WechatPayStrategy extends AbstractPayStrategy {
    @Override
    protected PayResponse doPay(PayRequest request) {
        try {

            switch (request.getTradeType()) {
                case APP:
                    log.info("微信APP支付");
                    /*if (Objects.nonNull(appRes)) {
                        Map<String, Object> appParams = new HashMap<>();
                        appParams.put("appid", appRes.getAppid());
                        appParams.put("partnerid", appRes.getPartnerId());
                        appParams.put("prepayid", appRes.getPrepayId());
                        appParams.put("package", appRes.getPackageVal());
                        appParams.put("noncestr", appRes.getNonceStr());
                        appParams.put("timestamp", appRes.getTimestamp());
                        appParams.put("sign", appRes.getSign());
                        return PayResponse.builder().success(true).payParams(appParams).prepayId(appRes.getPrepayId()).build();
                    }*/
                    return PayResponse.failure(Constants.OPERATE_ERROR_MSG, Constants.OPERATE_ERROR_MSG + "微信APP支付");
                case JSAPI:
                    log.info("微信JSAPI支付");
                    /*if (Objects.nonNull(jsapiRes)) {
                        Map<String, Object> jsapiParams = new HashMap<>();
                        jsapiParams.put("appId", jsapiRes.getAppId());
                        jsapiParams.put("paySign", jsapiRes.getPaySign());
                        jsapiParams.put("signType", jsapiRes.getSignType());
                        jsapiParams.put("package", jsapiRes.getPackageVal());
                        jsapiParams.put("nonceStr", jsapiRes.getNonceStr());
                        jsapiParams.put("timeStamp", jsapiRes.getTimeStamp());
                        return PayResponse.builder().success(true).payParams(jsapiParams).build();
                    }*/
                    return PayResponse.failure(Constants.OPERATE_ERROR_MSG, Constants.OPERATE_ERROR_MSG + "微信JSAPI支付");
                case NATIVE:
                    log.info("微信NATIVE支付");
                    /*if (Objects.nonNull(nativeRes)) {
                        return PayResponse.builder().success(true).codeUrl(nativeRes.getCodeUrl()).build();
                    }*/
                    return PayResponse.failure(Constants.OPERATE_ERROR_MSG, Constants.OPERATE_ERROR_MSG + "微信NATIVE支付");
                case H5:
                    log.info("微信H5支付");
                    /*if (Objects.nonNull(h5Res)) {
                        return PayResponse.builder().success(true).codeUrl(h5Res.getH5Url()).build();
                    }*/
                    return PayResponse.failure(Constants.OPERATE_ERROR_MSG, Constants.OPERATE_ERROR_MSG + "微信H5支付");
                default:
                    return PayResponse.failure(Constants.OPERATE_ERROR_MSG, "不支持的微信交易类型");
            }
        } catch (Exception e) {
            log.error("微信支付失败: {}", e);
            return PayResponse.failure(Constants.OPERATE_ERROR_MSG, e.getMessage());
        }
    }

    @Override
    protected QueryResponse doQueryOrder(PayRequest request) {
        try {
                /*return QueryResponse.builder()
                        .success(true)
                        .orderNo(res.getOutTradeNo())
                        .transactionId(res.getTransactionId())
                        .payStatus(payStatus)
                        .payTime(payTime)
                        .build();*/
            log.info("微信支付查询失败");
            return QueryResponse.failure(Constants.OPERATE_ERROR_MSG, "微信支付查询失败");
        } catch (Exception e) {
            log.error("微信支付查询失败: {}", e);
            return QueryResponse.failure(Constants.OPERATE_ERROR_MSG, e.getMessage());
        }
    }

    @Override
    protected CloseResponse doClose(PayRequest request) {
        try {
            /*CloseOrderRequest req = new CloseOrderRequest();
            req.setMchid(request.getMchId());
            req.setOutTradeNo(request.getOrderNo());

            log.info("关闭订单开始，请求参数:{}", req);
            new AppServiceExtension.Builder().config(
                    new RSAAutoCertificateConfig.Builder()
                            .merchantId(request.getMchId())
                            .privateKey(request.getPrivateKey())
                            .merchantSerialNumber(request.getSerialNo())
                            .apiV3Key(request.getApiV3Key())
                            .build()
            ).build().closeOrder(req);
            log.info("关闭订单结束");*/

            log.info("微信关闭订单结束");
            return CloseResponse.success(request.getOrderNo() + "微信关闭订单结束");
        } catch (Exception e) {
            log.error("微信关闭订单失败: {}", e);
            return CloseResponse.failure(Constants.OPERATE_ERROR_MSG, e.getMessage());
        }
    }

    @Override
    protected RefundResponse doRefund(RefundRequest request) {
        try {
            /*CreateRequest req = new CreateRequest();
            req.setOutTradeNo(request.getOrderNo());
            AmountReq amountReq = new AmountReq();
            amountReq.setTotal(request.getPayAmount().multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_UP).longValue());
            amountReq.setRefund(request.getRefundAmount().multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_UP).longValue());
            amountReq.setCurrency("CNY");
            req.setAmount(amountReq);
            req.setOutRefundNo(request.getRefundNo());
            req.setReason(request.getRefundReason());
            req.setNotifyUrl(request.getNotifyUrl());

            log.info("退款开始，请求参数:{}", req);
            Refund res = new RefundService.Builder().config(
                    new RSAAutoCertificateConfig.Builder()
                            .merchantId(request.getMchId())
                            .privateKey(request.getPrivateKey())
                            .merchantSerialNumber(request.getSerialNo())
                            .apiV3Key(request.getApiV3Key())
                            .build()
            ).build().create(req);
            log.info("退款结束，返回参数:{}", res);

            if (Objects.nonNull(res)) {
                return RefundResponse.builder().success(true).refundNo(res.getOutRefundNo()).build();
            }*/
            log.info("微信退款结束");
            return RefundResponse.failure(Constants.OPERATE_ERROR_MSG, "微信退款结束");
        } catch (Exception e) {
            log.error("微信退款失败: {}", e);
            return RefundResponse.failure(Constants.OPERATE_ERROR_MSG, e.getMessage());
        }
    }

    @Override
    protected RefundResponse doQueryRefund(RefundRequest request) {
        try {
            /*QueryByOutRefundNoRequest req = new QueryByOutRefundNoRequest();
            req.setOutRefundNo(request.getRefundNo());

            log.info("退款查询开始，请求参数:{}", req);
            Refund res = new RefundService.Builder().config(
                    new RSAAutoCertificateConfig.Builder()
                            .merchantId(request.getMchId())
                            .privateKey(request.getPrivateKey())
                            .merchantSerialNumber(request.getSerialNo())
                            .apiV3Key(request.getApiV3Key())
                            .build()
            ).build().queryByOutRefundNo(req);
            log.info("退款查询结束，返回参数:{}", res);

            if (Objects.nonNull(res)) {
                PayStatus payStatus = PayStatus.WAITING;
                BigDecimal amount = null;
                LocalDateTime refundTime = null;
                if (Status.SUCCESS == res.getStatus()) {
                    // 成功
                    refundTime = LocalDateTime.parse(res.getSuccessTime());
                    payStatus = PayStatus.SUCCESS;
                } else if (Status.PROCESSING == res.getStatus()) {
                    // 处理中
                    payStatus = PayStatus.WAITING;
                } else if (Status.CLOSED == res.getStatus()) {
                    // 已关闭
                    payStatus = PayStatus.CANCELED;
                } else if (Status.ABNORMAL == res.getStatus()) {
                    // 失败
                    payStatus = PayStatus.FAILED;
                }

                return RefundResponse.builder()
                        .success(true)
                        .orderNo(res.getOutTradeNo())
                        .refundNo(res.getOutRefundNo())
                        .refundId(res.getRefundId())
                        .refundStatus(payStatus)
                        .refundTime(refundTime)
                        .build();
            }*/
            log.info("微信退款查询结束");
            return RefundResponse.failure(Constants.OPERATE_ERROR_MSG, "微信退款查询结束");
        } catch (Exception e) {
            log.error("微信退款查询失败: {}", e);
            return RefundResponse.failure(Constants.OPERATE_ERROR_MSG, e.getMessage());
        }
    }

    @Override
    public NotifyResponse handleNotify(NotifyRequest request) {
        try {
            log.info("微信回调处理开始...");
            /*HttpServletRequest servletRequest = request.getServletRequest();
            // 读取请求体的信息
            ServletInputStream inputStream = servletRequest.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s);
            }
            log.info("微信回调处理，请求体参数:{}", stringBuffer);

            // 初始化 NotificationParser
            NotificationParser parser = new NotificationParser(
                    new RSAAutoCertificateConfig.Builder()
                            .merchantId(request.getMchId())
                            .privateKey(request.getPrivateKey())
                            .merchantSerialNumber(request.getSerialNo())
                            .apiV3Key(request.getApiV3Key())
                            .build()
            );
            RequestParam requestParam = new RequestParam.Builder()
                    .serialNumber(servletRequest.getHeader("Wechatpay-Serial"))
                    .nonce(servletRequest.getHeader("Wechatpay-Nonce"))
                    .signature(servletRequest.getHeader("Wechatpay-Signature"))
                    .timestamp(servletRequest.getHeader("Wechatpay-Timestamp"))
                    .signType(servletRequest.getHeader("Wechatpay-Signature-Type"))
                    .body(stringBuffer.toString())
                    .build();

            log.info("微信回调处理，RequestParam:{}", requestParam);

            switch (request.getMethod()) {
                case TradeMethod.PAY:
                    Transaction transaction = parser.parse(requestParam, Transaction.class);
                    log.info("微信支付回调返回参数:{}", transaction);
                    NotifyResponse payResponse = NotifyResponse.builder().success(true).orderNo(transaction.getOutTradeNo()).build();
                    if (Transaction.TradeStateEnum.SUCCESS == transaction.getTradeState()) {
                        // SUCCESS：支付成功
                        payResponse.setTransactionId(transaction.getTransactionId());
                        payResponse.setTime(LocalDateTime.parse(transaction.getSuccessTime()));
                        payResponse.setStatus(PayStatus.SUCCESS);
                    } else if (Transaction.TradeStateEnum.CLOSED == transaction.getTradeState() || Transaction.TradeStateEnum.REVOKED == transaction.getTradeState() || Transaction.TradeStateEnum.PAYERROR == transaction.getTradeState()) {
                        // CLOSED：已关闭、REVOKED：已撤销、PAYERROR：支付失败
                        payResponse.setStatus(PayStatus.FAILED);
                    } else {
                        // REFUND：转入退款、NOTPAY：未支付、USERPAYING：用户支付中
                        log.info("支付中的暂不处理：{}", transaction);
                    }

                    log.info("微信支付回调结束:{}", payResponse);
                    return payResponse;
                case TradeMethod.REFUND:
                    RefundNotification refund = parser.parse(requestParam, RefundNotification.class);
                    log.info("微信退款回调返回参数:{}", refund);
                    NotifyResponse refundResponse = NotifyResponse.builder().success(true).refundNo(refund.getOutRefundNo()).build();
                    if (Status.SUCCESS == refund.getRefundStatus()) {
                        // SUCCESS：退款成功
                        refundResponse.setRefundId(refund.getRefundId());
                        refundResponse.setOrderNo(refund.getOutTradeNo());
                        refundResponse.setTransactionId(refund.getTransactionId());
                        refundResponse.setTime(LocalDateTime.parse(refund.getSuccessTime()));
                        refundResponse.setStatus(PayStatus.SUCCESS);
                    } else {
                        // ABNORMAL：退款异常、CLOSED：退款关闭
                        refundResponse.setRefundId(refund.getRefundId());
                        refundResponse.setOrderNo(refund.getOutTradeNo());
                        refundResponse.setTransactionId(refund.getTransactionId());
                        refundResponse.setTime(LocalDateTime.parse(refund.getCreateTime()));
                        refundResponse.setStatus(PayStatus.FAILED);
                    }

                    log.info("微信退款回调结束:{}", refundResponse);
                    return refundResponse;
                default:
                    return NotifyResponse.failure(Constants.OPERATE_ERROR_MSG, "不支持的回调方式");
            }*/
            return NotifyResponse.failure(Constants.OPERATE_ERROR_MSG, "微信回调不支持的回调方式");
        } catch (Exception e) {
            log.error("微信回调处理失败: {}", e);
            return NotifyResponse.failure(Constants.OPERATE_ERROR_MSG, e.getMessage());
        }
    }

    @Override
    public PayChannel getPayChannel() {
        return PayChannel.WECHAT_PAY;
    }
}