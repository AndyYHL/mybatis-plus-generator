package com.example.generator.pojo.response;

import com.example.generator.pojo.enums.PayStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * QueryResponse描述:查询响应
 * <p>
 * 包名称：com.example.generator.pojo.response
 * 类名称：QueryResponse
 * 全路径：com.example.generator.pojo.response.QueryResponse
 * 类描述：查询响应
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryResponse {
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 三方交易订单号
     */
    private String transactionId;

    /**
     * 状态
     */
    private PayStatus payStatus;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    public static QueryResponse failure(String errorCode, String errorMsg) {
        QueryResponse response = new QueryResponse();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setErrorMsg(errorMsg);
        return response;
    }
}