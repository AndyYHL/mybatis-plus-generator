package com.example.generator.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.generator.pojo.domain.AccountDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * IAccountMapper描述:数据库调用
 * <p>
 * 包名称：com.example.generator.repository
 * 类名称：IAccountMapper
 * 全路径：com.example.generator.repository.IAccountMapper
 * 类描述：数据库调用
 *
 * @author Administrator-YHL
 * @date 2025年09月30日 16:26
 */
@Mapper
public interface IAccountMapper extends BaseMapper<AccountDO> {
}