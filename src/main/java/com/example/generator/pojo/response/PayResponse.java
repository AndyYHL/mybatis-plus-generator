package com.example.generator.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * PayResponse描述:支付响应
 * <p>
 * 包名称：com.example.generator.pojo.response
 * 类名称：PayResponse
 * 全路径：com.example.generator.pojo.response.PayResponse
 * 类描述：支付响应
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayResponse {
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 支付所需参数（前端调起支付所需）
     */
    private Map<String, Object> payParams;

    /**
     * 预支付交易会话标识
     */
    private String prepayId;

    /**
     * 支付跳转URL
     */
    private String payUrl;

    /**
     * 二维码链接
     */
    private String codeUrl;

    /**
     * 计算后的实际费率
     */
    private BigDecimal actualFee;

    /**
     * 路由原因
     */
    private String routeReason;

    public static PayResponse failure(String errorCode, String errorMsg) {
        PayResponse response = new PayResponse();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setErrorMsg(errorMsg);
        return response;
    }
}