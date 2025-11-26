package com.example.generator.pojo.response;

import com.example.generator.pojo.enums.PayStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * RefundResponse描述:退款响应
 * <p>
 * 包名称：com.example.generator.pojo.response
 * 类名称：RefundResponse
 * 全路径：com.example.generator.pojo.response.RefundResponse
 * 类描述：退款响应
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundResponse {
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 交易订单号
     */
    private String orderNo;

    /**
     * 退款单号
     */
    private String refundNo;

    /**
     * 退款三方订单号
     */
    private String refundId;

    /**
     * 状态
     */
    private PayStatus refundStatus;

    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    public static RefundResponse failure(String errorCode, String errorMsg) {
        return RefundResponse.builder().success(false).errorCode(errorCode).errorMsg(errorMsg).build();
    }
}