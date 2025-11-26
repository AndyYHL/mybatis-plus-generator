package com.example.generator.web.api.channel;

import com.example.generator.pojo.request.AccountReq;
import com.example.generator.pojo.vo.AccountVO;
import com.example.generator.web.api.common.IBaseApi;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * <p>
 * IAccountApi描述:账户API
 * <p>
 * 包名称：com.example.generator.web.api.channel
 * 类名称：IAccountApi
 * 全路径：com.example.generator.web.api.channel.IAccountApi
 * 类描述：账户API
 *
 * @author Administrator-YHL
 * @date 2025年09月30日 16:14
 */
@Tag(name = "账户", description = "账户")
public interface IAccountApi extends IBaseApi<AccountVO, AccountReq> {
    String PATH = "accountApi";
}