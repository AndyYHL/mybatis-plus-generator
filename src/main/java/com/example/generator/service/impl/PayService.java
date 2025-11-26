package com.example.generator.service.impl;

import com.example.generator.pay.service.AggregatePay;
import com.example.generator.pojo.container.ApiResponse;
import com.example.generator.pojo.param.PayParam;
import com.example.generator.pojo.request.PayRequest;
import com.example.generator.pojo.response.PayResponse;
import com.example.generator.service.abstraction.AbsPayMode;
import com.example.generator.service.standard.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    @Autowired
    private AggregatePay aggregatePay;

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

    @Override
    public ApiResponse<PayResponse> payAA(PayRequest req) {
        // 创建支付请求
        PayRequest request = new PayRequest();

        // 公共参数
        request.setAppId("wxd678efxxxxxxxxxxxxxxxxx");
        request.setMchId("123xxxxxxxx");
        request.setSerialNo("1DDE55AD98Exxxxxxxxxx");
        request.setPrivateKey("XXXXXXXXXXXXXXXXXXXXXXXXX");
        request.setPublicKey("XXXXXXXXXXXXXXXXXXXXXXXXXX");
        request.setApiV3Key("XXXXXXXXXXXXXXXXXXXXXX");

        // 订单参数
        request.setPayChannel(req.getPayChannel());
        request.setTradeType(req.getTradeType());
        request.setOrderNo("ORDER_" + System.currentTimeMillis());
        request.setAmount(BigDecimal.valueOf(100)); // 1元
        request.setDescription("测试商品");
        request.setClientIp("127.0.0.1");

        // 发起支付
        PayResponse response = aggregatePay.pay(request);

        if (response.getSuccess()) {
            // 支付创建成功，返回支付参数给前端
            System.out.println("支付参数：" + response.getPayParams());
        } else {
            // 处理支付失败
            System.out.println("支付失败：" + response.getErrorMsg());
        }
        return ApiResponse.success(response);
    }
}
