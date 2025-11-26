package com.example.generator.event;

import com.example.generator.pojo.dto.MessageDTO;
import org.springframework.context.ApplicationEvent;

/**
 * MessageEvent描述 消息事件
 *
 * @author Administrator-YHL
 * @date 2023-10-25
 **/
public class MessageEvent extends ApplicationEvent {
    /**
     * 消息事件
     *
     * @param messageDTO
     */
    public MessageEvent(MessageDTO messageDTO) {
        super(messageDTO);
    }
}
