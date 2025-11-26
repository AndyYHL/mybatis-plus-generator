
package com.example.generator.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <p>
 * Authority描述:权限编码
 * <p>
 * 包名称：com.example.generator.pojo.enums
 * 类名称：Authority
 * 全路径：com.example.generator.pojo.enums.Authority
 * 类描述：权限编码
 *
 * @author Administrator-YHL
 * @date 2023年11月14日 13:53
 */
@Getter
@AllArgsConstructor
public enum AuthorityEnum {
    /**
     * 读权限 100 = 4
     */
    READABLE(0B100, "可读"),
    /**
     * 读写权限 0B011 = 8
     */
    READABLE_WRITABLE(0B1000, "读写权限"),
    /**
     * 写权限 0B010 = 2
     */
    WRITABLE(0B010, "可写"),
    /**
     * 可运行 0B001 = 1
     */
    RUNNABLE(0B001, "可运行");

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
    public static AuthorityEnum findByCodeEnum(Integer code) {
        return Arrays.stream(values()).filter(r -> r.getCode().equals(code)).findFirst().orElseThrow();
    }
}
