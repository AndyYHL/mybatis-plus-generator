package com.example.generator.pojo.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * <p>
 * TradeType描述:交易方式
 * <p>
 * 包名称：com.example.generator.pojo.enums
 * 类名称：TradeType
 * 全路径：com.example.generator.pojo.enums.TradeType
 * 类描述：交易方式
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:17
 */
@Getter
@AllArgsConstructor
public enum TradeType {
    APP("app", "APP支付"),
    JSAPI("jsapi", "公众号/小程序支付"),
    NATIVE("native", "扫码支付"),
    H5("h5", "H5支付");

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
    public static TradeType findByCodeEnum(String code) throws Throwable {
        return Arrays.stream(values()).filter(r -> r.getCode().equals(code)).findFirst().orElseThrow((Supplier<Throwable>) () -> new NullPointerException("空指针"));
    }
}