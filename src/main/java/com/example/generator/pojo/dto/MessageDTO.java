package com.example.generator.pojo.dto;

import lombok.Data;

/**
 * MessageDTO描述
 *
 * @author Administrator-YHL
 * @date 2023-10-25
 **/
@Data
public class MessageDTO {
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String body;
}
