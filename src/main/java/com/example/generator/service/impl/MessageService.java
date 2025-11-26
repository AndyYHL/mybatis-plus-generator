package com.example.generator.service.impl;

import com.alibaba.fastjson2.JSON;
import com.example.generator.event.MessageEvent;
import com.example.generator.service.standard.IMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * MessageService描述
 *
 * @author Administrator-YHL
 * @date 2023-10-25
 **/
@Slf4j
@Service
public class MessageService implements IMessageService {

    @EventListener(value = MessageEvent.class)
    public void listenMessage(MessageEvent messageEvent) {
        log.info("通过事件调用消息内容:[{}]", JSON.toJSONString(messageEvent.getSource()));
    }
}
