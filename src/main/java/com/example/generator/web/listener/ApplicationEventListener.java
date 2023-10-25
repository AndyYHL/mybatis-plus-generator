package com.example.generator.web.listener;

import com.example.generator.event.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * ApplicationEventListener描述
 *
 * @author Administrator-YHL
 * @date 2023-10-25
 **/
@Slf4j
@Component
public class ApplicationEventListener implements ApplicationListener<MessageEvent> {
    /**
     * 当容器中发布此事件以后，方法触发
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(MessageEvent event) {
        // TODO Auto-generated method stub
        System.out.println("收到事件：" + event);
    }
}
