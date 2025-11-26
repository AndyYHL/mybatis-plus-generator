package com.example.generator.service.abstraction;

import com.example.generator.pojo.param.PayParam;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * AbsPayMode描述:抽象
 * <p>
 * 包名称：com.example.generator.service.abstraction
 * 类名称：AbsPayMode
 * 全路径：com.example.generator.service.abstraction.AbsPayMode
 * 类描述：抽象
 *
 * @author Administrator-YHL
 * @date 2023年11月22日 15:35
 */
@Slf4j
public abstract class AbsPayMode {
    public void check(PayParam payParam) {
        log.info("完成订单{}的前置校验", payParam.getOrderId());
    }

    public abstract void pay(PayParam payParam);

    public void change(PayParam payParam) {
        log.info("完成对商品{}的扣减库存", payParam.getProductId());
        log.info("完成订单{}的订单状态修改", payParam.getOrderId());
    }
}
