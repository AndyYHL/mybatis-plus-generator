package com.example.generator.service.standard;

import com.example.generator.pojo.domain.UserDO;

/**
 * IUserService描述
 *
 * @author Administrator-YHL
 * @date 2023-10-18
 **/
public interface IUserService {
    /**
     * 查询单个用户
     *
     * @return
     */
    UserDO selectOne(Object object);
}
