package com.example.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.generator.pojo.domain.AccountDO;
import com.example.generator.repository.IAccountMapper;
import com.example.generator.service.standard.IAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * AccountService描述:实现
 * <p>
 * 包名称：com.example.generator.service.impl
 * 类名称：AccountService
 * 全路径：com.example.generator.service.impl.AccountService
 * 类描述：实现
 *
 * @author Administrator-YHL
 * @date 2025年09月30日 16:24
 */
@Slf4j
@Service
public class AccountService extends ServiceImpl<IAccountMapper, AccountDO> implements IAccountService {
}