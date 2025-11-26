package com.example.generator.pojo.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * <p>
 * TradeMethod描述:交易类型
 * <p>
 * 包名称：com.example.generator.pojo.enums
 * 类名称：TradeMethod
 * 全路径：com.example.generator.pojo.enums.TradeMethod
 * 类描述：交易类型
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:18
 */
@Getter
@AllArgsConstructor
public enum TradeMethod {
    PAY("pay", "支付"),
    REFUND("refund", "退款");

    /**
     * 编码
     */
    private final String code;

    /**
     * 说明
     */
    private final String name;

    /**
     * 判断传入的枚举是否存在当前枚举中
     *
     * @param code 编码值
     * @return 一个都没有匹配到返回 true
     */
    public static boolean enumNoneMatch(String code) {
        return Arrays.stream(values()).noneMatch(m -> code.equals(m.getCode()));
    }

    /**
     * 根据code 获取本身枚举
     *
     * @param code 编码值
     * @return 枚举
     */
    public static TradeMethod findByCodeEnum(String code) throws Throwable {
        return Arrays.stream(values()).filter(r -> r.getCode().equals(code)).findFirst().orElseThrow((Supplier<Throwable>) () -> new NullPointerException("空指针"));
    }
}