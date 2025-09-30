package com.example.generator.pojo.dto;

import com.example.generator.pojo.dto.base.BaseEntityDTO;
import lombok.Data;

/**
 * UserParam描述
 *
 * @author Administrator-YHL
 * @date 2023-10-18
 **/
@Data
public class UserDTO extends BaseEntityDTO {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户类型
     */
    private Integer userType;
}
