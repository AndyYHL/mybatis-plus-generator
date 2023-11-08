package com.example.generator.web.api.channel;

import com.example.generator.pojo.constant.apicode.CommonApiCode;
import com.example.generator.pojo.constant.apicode.LoginApiCode;
import com.example.generator.pojo.constant.base.ApiConstant;
import com.example.generator.pojo.container.ApiRequest;
import com.example.generator.pojo.container.ApiResponse;
import com.example.generator.pojo.param.UserParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ILoginApi描述
 *
 * @author Administrator-YHL
 * @date 2023-11-8
 **/
@Tag(name = "登录-操作接口", description = "登录-操作描述")
public interface ILoginApi {
    /**
     * 登录
     *
     * @param req 登录
     * @return 登录
     */
    @Operation(summary = "登录", description = "登录")
    @PostMapping(value = {ApiConstant.API_PLATFORM_PREFIX + LoginApiCode.BASE_URL + LoginApiCode.DO_LOGIN})
    ApiResponse<String> doLogin(@RequestBody @Valid ApiRequest<UserParam> req);
    /**
     * 查询登录状态
     *
     * @param req 查询登录状态
     * @return 查询登录状态
     */
    @Operation(summary = "查询登录状态", description = "查询登录状态")
    @PostMapping(value = {ApiConstant.API_PLATFORM_PREFIX + LoginApiCode.BASE_URL + LoginApiCode.IS_LOGIN})
    ApiResponse<String> isLogin(@RequestBody @Valid ApiRequest<UserParam> req);
}
