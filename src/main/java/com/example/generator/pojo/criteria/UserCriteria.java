package com.example.generator.pojo.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * UserCriteria描述
 *
 * @author Administrator-YHL
 * @date 2023-10-18
 **/
@Data
@Tag(name = "UserCriteria", description = "用户保存")
public class UserCriteria {
    /**
     * 用户ID
     */
    @Schema(name = "userId", description = "用户ID")
    private String userId;
    /**
     * 用户名
     */
    @Schema(name = "userName", description = "用户名")
    private String userName;
}
