package com.example.generator.pojo.dto;

import com.example.generator.pojo.dto.base.BaseEntityDTO;
import lombok.Data;

/**
 * MessageDTO描述
 *
 * @author Administrator-YHL
 * @date 2023-10-25
 **/
@Data
public class MessageDTO extends BaseEntityDTO {
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String body;
}
