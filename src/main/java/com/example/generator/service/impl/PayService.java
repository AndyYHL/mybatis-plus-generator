package com.example.generator.service.impl;

import com.example.generator.pojo.param.PayParam;
import com.example.generator.service.abstraction.AbsPayMode;
import com.example.generator.service.standard.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * PayService描述:dd
 * <p>
 * 包名称：com.example.generator.service.impl
 * 类名称：PayService
 * 全路径：com.example.generator.service.impl.PayService
 * 类描述：dd
 *
 * @author Administrator-YHL
 * @date 2023年11月22日 15:40
 */
@Slf4j
@Service
public class PayService implements IPayService {
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Boolean pay(PayParam payParam) {
        Map<String, AbsPayMode> beanMap = this.applicationContext.getBeansOfType(AbsPayMode.class);
        // 按支付类型获取支付业务
        AbsPayMode absPayMode = beanMap.get(String.valueOf(payParam.getPayMode()));
        try {
            // 前置校验
            absPayMode.check(payParam);
            // 支付
            absPayMode.pay(payParam);
            // 扣减库存，修改订单状态
            absPayMode.change(payParam);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("出现异常", e);
            return Boolean.FALSE;
        }
    }
}
