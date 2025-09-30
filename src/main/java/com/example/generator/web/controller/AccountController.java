package com.example.generator.web.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.generator.pojo.domain.AccountDO;
import com.example.generator.pojo.request.AccountReq;
import com.example.generator.pojo.vo.AccountVO;
import com.example.generator.service.standard.IAccountService;
import com.example.generator.web.api.channel.IAccountApi;
import com.example.generator.web.controller.base.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * TradeController描述:交易
 * <p>
 * 包名称：com.example.generator.web.controller
 * 类名称：AccountController
 * 全路径：com.example.generator.web.controller.AccountController
 * 类描述：交易
 *
 * @author Administrator-YHL
 * @date 2025年09月30日 16:11
 */
@RestController
@Tag(name = "账户", description = "账户")
@RequestMapping(path = IAccountApi.PATH, produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController extends BaseController<AccountVO, AccountReq, AccountDO> implements IAccountApi {
    @Autowired
    private IAccountService accountService;

    @Override
    public IService<AccountDO> getService() {
        return this.accountService;
    }
}