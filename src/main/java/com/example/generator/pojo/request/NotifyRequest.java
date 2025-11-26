package com.example.generator.pojo.request;

import com.example.generator.pojo.enums.PayChannel;
import com.example.generator.pojo.enums.TradeMethod;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * NotifyRequest描述:回调请求参数
 * <p>
 * 包名称：com.example.generator.pojo.request
 * 类名称：NotifyRequest
 * 全路径：com.example.generator.pojo.request.NotifyRequest
 * 类描述：回调请求参数
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:23
 */

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotifyRequest extends BaseRequest{
    /**
     * 支付渠道
     */
    private PayChannel payChannel;

    /**
     * 交易方式
     */
    private TradeMethod method;

    /**
     * 请求体
     */
    private HttpServletRequest servletRequest;
}