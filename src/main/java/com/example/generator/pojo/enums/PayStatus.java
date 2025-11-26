package com.example.generator.pojo.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * <p>
 * PayStatus描述:支付状态
 * <p>
 * 包名称：com.example.generator.pojo.enums
 * 类名称：PayStatus
 * 全路径：com.example.generator.pojo.enums.PayStatus
 * 类描述：支付状态
 *
 * @author 31756-YHL
 * @date 2025年11月26日 09:16
 */
@Getter
@AllArgsConstructor
public enum PayStatus {
    WAITING(0, "待支付"),
    SUCCESS(1, "成功"),
    FAILED(2, "失败"),
    CANCELED(3, "已取消"),
    REFUND(4, "已退款");

    /**
     * 编码
     */
    private final Integer code;

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
    public static boolean enumNoneMatch(Integer code) {
        return Arrays.stream(values()).noneMatch(m -> code.equals(m.getCode()));
    }

    /**
     * 根据code 获取本身枚举
     *
     * @param code 编码值
     * @return 枚举
     */
    public static PayStatus findByCodeEnum(Integer code) throws Throwable {
        return Arrays.stream(values()).filter(r -> r.getCode().equals(code)).findFirst().orElseThrow((Supplier<Throwable>) () -> new NullPointerException("空指针"));
    }
}