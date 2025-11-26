package com.example.generator.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xxxx
 */
@Getter
@AllArgsConstructor
public enum BasicRespCode {
    /**
     * 返回状态嘛
     */
    SUCCESS("000000", "请求成功"),
    FAIL("100001", "请求失败"),
    ERROR_PARAM("100002", "请求参数错误"),
    ERROR_API("100003", "请求API错误"),
    ERROR_SIGN("100004", "请求签名错误"),
    NO_LOGIN("100005", "用户未登录"),
    NO_AUTH("100006", "用户未授权"),
    NO_DATA("100007", "未查询数据"),
    AUTH_FAIL("100008", "认证失败"),
    TOO_MANY_REQUESTS ("100009", "请求过于频繁，请稍后重试"),
    PERMISSION_FAIL("200008", "无访问权限"),
    LOGIN_ERROR("200001", "登录失败，请检查用户名密码"),
    DUPLICATE_KEY("300001", "重复关键字,数据插入失败！"),
    BANKCARD_AUTH_FAIL("4000001", "银行卡认证失败"),
    ;
    private final String code;
    private final String desc;

}
