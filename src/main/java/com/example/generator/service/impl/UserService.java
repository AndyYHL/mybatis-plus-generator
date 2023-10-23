package com.example.generator.service.impl;

import com.alibaba.fastjson2.JSON;
import com.example.generator.pojo.domain.UserDO;
import com.example.generator.service.standard.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

/**
 * UserService描述
 *
 * @author Administrator-YHL
 * @date 2023-10-18
 **/
@Slf4j
@Service
public class UserService implements IUserService {
    @Override
    public UserDO selectOne(Object object) {
        log.info("请求参数：[{}]", JSON.toJSONString(object));
        long startTimeMill = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).minusMinutes(30).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long endTimeMill = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        log.info("查询的时间间隔：[{}] - [{}]", startTimeMill, endTimeMill);
        UserDO userDO = new UserDO();
        userDO.setUserId(String.valueOf(startTimeMill).concat("-用户ID"));
        userDO.setUserName(String.valueOf(endTimeMill).concat("-用户名称"));
        return userDO;
    }
}
