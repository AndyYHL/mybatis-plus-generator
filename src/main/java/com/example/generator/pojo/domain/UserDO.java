package com.example.generator.pojo.domain;

import lombok.Builder;
import lombok.Data;

/**
 * UserDO描述
 *
 * @author Administrator-YHL
 * @date 2023-10-20
 **/
@Data
@Builder
public class UserDO {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 默认权限全空
     */
    private Integer auth = 0B000;
}
