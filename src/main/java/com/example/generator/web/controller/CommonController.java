package com.example.generator.web.controller;

import com.alibaba.fastjson2.JSON;
import com.example.generator.pojo.container.ApiPageRequest;
import com.example.generator.pojo.container.ApiPageResponse;
import com.example.generator.pojo.container.ApiRequest;
import com.example.generator.pojo.container.ApiResponse;
import com.example.generator.pojo.criteria.UserCriteria;
import com.example.generator.pojo.domain.UserDO;
import com.example.generator.pojo.param.UserParam;
import com.example.generator.pojo.vo.UserVO;
import com.example.generator.service.standard.IUserService;
import com.example.generator.web.api.common.ICommonApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * SystemController描述
 *
 * @author Administrator-YHL
 * @date 2023-10-18
 **/
@Slf4j
@RestController
public class CommonController implements ICommonApi {
    @Autowired
    private IUserService userService;

    @Override
    public ApiResponse<Boolean> saveOrUpdate(ApiRequest<UserParam> req) {
        log.info("请求参数:[{}]", JSON.toJSONString(req));
        return ApiResponse.failTrace(req.getTrace(), "请求失败");
    }

    @Override
    public ApiResponse<List<UserVO>> selectList(ApiRequest<UserCriteria> req) {
        log.info("请求参数:[{}]", JSON.toJSONString(req));
        return ApiResponse.success(Collections.emptyList());
    }

    @Override
    public ApiPageResponse<UserVO> selectPageList(ApiPageRequest<UserCriteria> req) {
        log.info("请求参数:[{}]", JSON.toJSONString(req));
        List<UserVO> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserVO userVO = new UserVO();
            userVO.setUserId("用户ID" + System.currentTimeMillis() + i);
            userVO.setUserName("用户名称" + System.currentTimeMillis() + i);
            list.add(userVO);
        }
        this.funUser(list, UserVO::getUserName);
        return ApiPageResponse.successTrace(req.getTrace(), list);
    }

    // TODO 函数设置值
    private void funUser(List<UserVO> list, Function<UserVO, String> fun) {
        list.forEach(r -> {
            String name = fun.apply(r);
            log.info("函数获取:{}", name);
        });
    }

    @Override
    public ApiResponse<UserVO> selectDataDetail(ApiRequest<UserCriteria> req) {
        log.info("请求参数:[{}]", JSON.toJSONString(req));
        UserDO userDO = this.userService.selectOne(req);
        return ApiResponse.success(JSON.parseObject(JSON.toJSONString(userDO), UserVO.class));
    }

    @Override
    public ApiResponse<Boolean> updateStatus(ApiRequest<UserParam> req) {
        log.info("请求参数:[{}]", JSON.toJSONString(req));
        return ApiResponse.success(true);
    }
}
