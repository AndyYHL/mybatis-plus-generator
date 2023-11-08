package com.example.generator.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.generator.pojo.container.ApiRequest;
import com.example.generator.pojo.container.ApiResponse;
import com.example.generator.pojo.param.UserParam;
import com.example.generator.web.api.channel.ILoginApi;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginController描述
 *
 * @author Administrator-YHL
 * @date 2023-11-8
 **/
@RestController
public class LoginController implements ILoginApi {
    @Override
    public ApiResponse<String> doLogin(ApiRequest<UserParam> req) {
        UserParam param = req.getParam();
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("admin".equals(param.getUserName()) && "admin".equals(param.getUserId())) {
            StpUtil.login(10001);
            return ApiResponse.success("登录成功");
        }
        return ApiResponse.fail("登录失败");
    }

    @Override
    public ApiResponse<String> isLogin(ApiRequest<UserParam> req) {
        return ApiResponse.success("当前会话是否登录：".concat(String.valueOf(StpUtil.isLogin())));
    }
}
