
package com.example.generator.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
     * 读权限 0B100 = 4
     */
    READABLE(0B100, "可读"),
    /**
     * 读权限 0B010 = 2
     */
    WRITABLE(0B010, "可写"),
    /**
     * 读权限 0B001 = 1
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
}
