package com.example.generator.service.impl;

import com.example.generator.pojo.param.PayParam;
import com.example.generator.service.abstraction.AbsPayMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ZhiFuBaoService描述:支付宝
 * <p>
 * 包名称：com.example.generator.service.impl
 * 类名称：ZhiFuBaoService
 * 全路径：com.example.generator.service.impl.ZhiFuBaoService
 * 类描述：支付宝
 *
 * @author Administrator-YHL
 * @date 2023年11月22日 15:37
 */
@Slf4j
@Service("2")
public class ZhiFuBaoService extends AbsPayMode {

    @Override
    public void pay(PayParam payParam) {
        log.info("调用支付宝支付");
    }
}
