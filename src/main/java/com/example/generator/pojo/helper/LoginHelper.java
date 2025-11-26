package com.example.generator.pojo.helper;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

/**
 * 登录鉴权助手
 * <p>
 * user_type 为 用户类型 同一个用户表 可以有多种用户类型 例如 pc,app
 * deivce 为 设备类型 同一个用户类型 可以有 多种设备类型 例如 web,ios
 * 可以组成 用户类型与设备类型多对多的 权限灵活控制
 * <p>
 * 多用户体系 针对 多种用户类型 但权限控制不一致
 * 可以组成 多用户类型表与多设备类型 分别控制权限
 *
 * @author jdd
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginHelper {
    /**
     * 用户KEY
     */
    public static final String LOGIN_USER_KEY = "loginUser";

    /**
     * 登录系统
     * LoginHelper.login(userLoginVO, userLoginVO::getUserId);
     * userLoginVO.setToken(LoginHelper.getToken());
     *
     * @param loginUser 登录用户信息
     */
    public static <T> void login(T loginUser, Supplier<Object> userIdSupplier) {
        SaHolder.getStorage().set(LOGIN_USER_KEY, loginUser);
        StpUtil.login(userIdSupplier.get());
        setLoginUser(loginUser);
    }

    /**
     * 登录系统 基于 设备类型
     * 针对相同用户体系不同设备
     *
     * @param loginUser 登录用户信息
     */
    public static <T> void loginByDevice(T loginUser, Supplier<Object> userIdSupplier, String deviceType) {
        SaHolder.getStorage().set(LOGIN_USER_KEY, loginUser);
        StpUtil.login(userIdSupplier.get(), deviceType);
        setLoginUser(loginUser);
    }

    /**
     * 设置用户数据(多级缓存)
     */
    private static <T> void setLoginUser(T loginUser) {
        StpUtil.getTokenSession().set(LOGIN_USER_KEY, loginUser);
    }

    /**
     * 获取用户(多级缓存)
     * UserLoginVO userLoginVO = LoginHelper.getLoginUser();
     */
    @SuppressWarnings(value = "all")
    public static <T> T getLoginUser() {
        T loginUser = (T) SaHolder.getStorage().get(LOGIN_USER_KEY);
        if (loginUser != null) {
            return loginUser;
        }
        loginUser = (T) StpUtil.getTokenSession().get(LOGIN_USER_KEY);
        if (loginUser != null)
            SaHolder.getStorage().set(LOGIN_USER_KEY, loginUser);
        return loginUser;
    }

    /**
     * 获取用户id
     */
    public static Object getUserId() {
        return StpUtil.getLoginId();
    }

    /**
     * 获取当前token
     *
     * @return
     */
    public static String getToken() {
        return StpUtil.getTokenValue();
    }

    /**
     * 根据token获取用户
     *
     * @param token
     * @param <T>
     * @return
     */
    @SuppressWarnings(value = "all")
    public static <T> T getTokenUser(String token) {
        T loginUser = (T) StpUtil.getTokenSession().get(token);
        if (loginUser != null)
            SaHolder.getStorage().set(LOGIN_USER_KEY, loginUser);
        return loginUser;
    }

    /**
     * 退出
     */
    public static void logout() {
        StpUtil.logout();
    }
}
