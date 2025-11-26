package com.example.generator.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * <p>
 * AccountVO描述:账户VO
 * <p>
 * 包名称：com.example.generator.pojo.vo
 * 类名称：AccountVO
 * 全路径：com.example.generator.pojo.vo.AccountVO
 * 类描述：账户VO
 *
 * @author Administrator-YHL
 * @date 2025年09月30日 16:12
 */
@Data
@Tag(name = "AccountVO", description = "账户返回")
public class AccountVO {
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