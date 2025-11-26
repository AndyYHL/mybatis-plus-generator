package com.example.generator.service.standard;

import com.example.generator.pojo.container.ApiResponse;
import com.example.generator.pojo.param.PayParam;
import com.example.generator.pojo.request.PayRequest;
import com.example.generator.pojo.response.PayResponse;

/**
 * <p>
 * IPayService描述:支付
 * <p>
 * 包名称：com.example.generator.service.standard
 * 类名称：IPayService
 * 全路径：com.example.generator.service.standard.IPayService
 * 类描述：支付
 *
 * @author Administrator-YHL
 * @date 2023年11月22日 15:39
 */
public interface IPayService {
    Boolean pay(PayParam payParam);

    ApiResponse<PayResponse> payAA(PayRequest req);
}
