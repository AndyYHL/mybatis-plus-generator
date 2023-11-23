package com.example.generator.web.controller;

import com.example.generator.pojo.param.PayParam;
import com.example.generator.service.standard.IPayService;
import com.example.generator.service.standard.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * PayController描述:支付
 * <p>
 * 包名称：com.example.generator.web.controller
 * 类名称：PayController
 * 全路径：com.example.generator.web.controller.PayController
 * 类描述：支付
 *
 * @author Administrator-YHL
 * @date 2023年11月22日 15:38
 */
@Tag(name = "支付-操作接口", description = "支付-操作描述")
@RestController
public class PayController {
    @Autowired
    private IPayService payService;

    @PostMapping("/pay")
    public Boolean pay(@RequestBody PayParam payParam) {
        return this.payService.pay(payParam);
    }
}
