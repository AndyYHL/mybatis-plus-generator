package com.example.generator.pojo.dto.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * BaseEntityDTO
 * </p>
 *
 * @author jdd
 * @since 2023-09-03
 */
@Getter
@Setter
public class BaseEntityDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 创建时间-
     */
    private LocalDateTime createTime;
    /**
     * 更新时间-
     */
    private LocalDateTime updateTime;
    /**
     * 删除时间-
     */
    private LocalDateTime deleteTime;
    /**
     * 创建人-
     */
    private String createUserId;
    /**
     * 更新人-
     */
    private String updateUserId;
    /**
     * 删除人-
     */
    private String deleteUserId;
    /**
     * 删除标识 1 未删除 -1删除-
     */
    private Integer deleted;
}
