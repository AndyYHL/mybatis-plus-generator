package com.example.generator.pojo.param;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * <p>
 * PayParam描述:支付
 * <p>
 * 包名称：com.example.generator.pojo.param
 * 类名称：PayParam
 * 全路径：com.example.generator.pojo.param.PayParam
 * 类描述：支付
 *
 * @author Administrator-YHL
 * @date 2023年11月22日 15:36
 */
@Data
@Tag(name = "PayParam", description = "支付参数")
public class PayParam {
    private Integer payMode;

    private Long productId;

    private Long orderId;
}
