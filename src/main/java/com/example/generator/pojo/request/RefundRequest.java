package com.example.generator.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * <p>
 * RefundRequest描述:退款请求
 * <p>
 * 包名称：com.example.generator.pojo.request
 * 类名称：RefundRequest
 * 全路径：com.example.generator.pojo.request.RefundRequest
 * 类描述：退款请求
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:30
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class RefundRequest extends BaseRequest {
    /**
     * 交易订单号
     */
    private String orderNo;

    /**
     * 退款单号
     */
    private String refundNo;

    /**
     * 交易金额
     */
    private BigDecimal payAmount;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 退款回调地址
     */
    private String notifyUrl;
}