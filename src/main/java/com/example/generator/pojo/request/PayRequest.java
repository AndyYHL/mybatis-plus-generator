package com.example.generator.pojo.request;

import com.example.generator.pojo.enums.PayChannel;
import com.example.generator.pojo.enums.TradeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * PayRequest描述:支付请求
 * <p>
 * 包名称：com.example.generator.pojo.request
 * 类名称：PayRequest
 * 全路径：com.example.generator.pojo.request.PayRequest
 * 类描述：支付请求
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:26
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PayRequest extends BaseRequest {
    /**
     * 支付渠道
     */
    private PayChannel payChannel;

    /**
     * 交易类型
     */
    private TradeType tradeType;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单金额（单位：分）
     */
    private BigDecimal amount;

    /**
     * 订单描述
     */
    private String description;

    /**
     * 用户IP
     */
    private String clientIp;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 附加数据
     */
    private String attach;

    /**
     * 通知地址
     */
    private String notifyUrl;

    /**
     * 用户标识
     */
    private String openId;

    /**
     * 场景类型：Wap、iOS、Android
     */
    private String h5Type;

}