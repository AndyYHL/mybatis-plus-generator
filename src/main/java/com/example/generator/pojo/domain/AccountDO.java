package com.example.generator.pojo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.generator.pojo.annotation.BusinessId;
import com.example.generator.pojo.domain.base.BaseEntity;
import lombok.Data;

/**
 * <p>
 * AccountDO描述:账户表
 * <p>
 * 包名称：com.example.generator.pojo.domain
 * 类名称：AccountDO
 * 全路径：com.example.generator.pojo.domain.AccountDO
 * 类描述：账户表
 *
 * @author Administrator-YHL
 * @date 2023年11月24日 13:33
 */
@Data
@TableName("account")
public class AccountDO extends BaseEntity {
    /**
     * 账户编号
     */
    @BusinessId
    private String accountNo;
    /**
     * 账户名称
     */
    private String accountName;
}
