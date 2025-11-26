package com.example.generator.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * CloseResponse描述:关单返回参数
 * <p>
 * 包名称：com.example.generator.pojo.response
 * 类名称：CloseResponse
 * 全路径：com.example.generator.pojo.response.CloseResponse
 * 类描述：关单返回参数
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloseResponse {
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    public static CloseResponse success(String orderNo) {
        CloseResponse response = new CloseResponse();
        response.setSuccess(true);
        response.setOrderNo(orderNo);
        return response;
    }

    public static CloseResponse failure(String errorCode, String errorMsg) {
        CloseResponse response = new CloseResponse();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setErrorMsg(errorMsg);
        return response;
    }
}