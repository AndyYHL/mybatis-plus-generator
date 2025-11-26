package com.example.generator.pojo.response;

import com.example.generator.pojo.enums.PayStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * NotifyResponse描述:回调返回参数
 * <p>
 * 包名称：com.example.generator.pojo.response
 * 类名称：NotifyResponse
 * 全路径：com.example.generator.pojo.response.NotifyResponse
 * 类描述：回调返回参数
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyResponse {
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 交易订单号
     */
    private String orderNo;

    /**
     * 三方交易订单号
     */
    private String transactionId;

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
    private PayStatus status;

    /**
     * 时间
     */
    private LocalDateTime time;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    public static NotifyResponse failure(String errorCode, String errorMsg) {
        return NotifyResponse.builder().success(false).errorCode(errorCode).errorMsg(errorMsg).build();
    }
}