package com.example.generator.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * <p>
 * AccountReq描述:账户信息
 * <p>
 * 包名称：com.example.generator.pojo.request
 * 类名称：AccountReq
 * 全路径：com.example.generator.pojo.request.AccountReq
 * 类描述：账户信息
 *
 * @author Administrator-YHL
 * @date 2025年09月30日 16:15
 */
@Data
@Tag(name = "AccountReq", description = "账户请求")
public class AccountReq {
    /**
     * 账户编号
     */
    @Schema(name = "accountNo", description = "账户编号")
    private String accountNo;
    /**
     * 账户名称
     */
    @Schema(name = "accountName", description = "账户名称")
    private String accountName;
}