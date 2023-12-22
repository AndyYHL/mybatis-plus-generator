package com.example.generator.service.impl;

import com.example.generator.pojo.param.PayParam;
import com.example.generator.service.abstraction.AbsPayMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * WeChatService描述:微信
 * <p>
 * 包名称：com.example.generator.service.impl
 * 类名称：WeChatService
 * 全路径：com.example.generator.service.impl.WeChatService
 * 类描述：微信
 *
 * @author Administrator-YHL
 * @date 2023年11月22日 15:37
 */
@Slf4j
@Service("1")
public class WeChatService extends AbsPayMode {
    @Override
    public void pay(PayParam payParam) {
        log.info("调用微信支付");
    }
}
