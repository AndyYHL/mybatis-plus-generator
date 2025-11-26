package com.example.generator.web.handle;

import com.alibaba.fastjson2.JSON;
import com.example.generator.event.MessageEvent;
import com.example.generator.pojo.dto.MessageDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * MessageHandler描述
 *
 * @author Administrator-YHL
 * @date 2023-10-25
 **/
@Slf4j
@Component
public class MessageHandler implements Serializable {
    @Resource
    private ApplicationContext applicationContext;

    public void handle(MessageDTO messageDTO) {
        log.info("handle messageEvent:{}", JSON.toJSONString(messageDTO));
        this.applicationContext.publishEvent(new MessageEvent(messageDTO));
    }
}
