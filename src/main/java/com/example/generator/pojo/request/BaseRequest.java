package com.example.generator.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * BaseRequest描述:支付公共请求参数
 * <p>
 * 包名称：com.example.generator.pojo.request
 * 类名称：BaseRequest
 * 全路径：com.example.generator.pojo.request.BaseRequest
 * 类描述：支付公共请求参数
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:20
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseRequest {
    /**
     * appid
     */
    private String appId;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户证书序列号
     */
    private String serialNo;

    /**
     * 商户API私钥
     */
    private String privateKey;

    /**
     * 商户API公钥
     */
    private String publicKey;

    /**
     * 商户APIV3密钥
     */
    private String apiV3Key;
}