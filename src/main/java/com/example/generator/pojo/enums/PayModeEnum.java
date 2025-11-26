package com.example.generator.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <p>
 * PayModeEnum描述:支付渠道
 * <p>
 * 包名称：com.example.generator.pojo.enums
 * 类名称：PayModeEnum
 * 全路径：com.example.generator.pojo.enums.PayModeEnum
 * 类描述：支付渠道
 *
 * @author Administrator-YHL
 * @date 2023年11月22日 15:33
 */
@Getter
@AllArgsConstructor
public enum PayModeEnum {
    WECHAT(10, "微信支付"),
    ZHIFUBAO(20, "支付宝");
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
    public static PayModeEnum findByCodeEnum(Integer code) {
        return Arrays.stream(values()).filter(r -> r.getCode().equals(code)).findFirst().orElseThrow();
    }
}
