package com.example.generator.service.keyresolver;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.generator.pojo.annotation.RateLimiter;
import com.example.generator.pojo.dto.UserDTO;
import com.example.generator.pojo.helper.LoginHelper;
import com.example.generator.service.standard.RateLimiterKeyResolver;
import org.aspectj.lang.JoinPoint;

/**
 * <p>
 * UserRateLimiterKeyResolver描述:用户ID级别
 * <p>
 * 包名称：com.example.generator.service.impl
 * 类名称：UserRateLimiterKeyResolver
 * 全路径：com.example.generator.service.keyresolver.UserRateLimiterKeyResolver
 * 类描述：用户ID级别
 *
 * @author Administrator-YHL
 * @date 2025年09月29日 10:02
 */
public class UserRateLimiterKeyResolver implements RateLimiterKeyResolver {
    @Override
    public String resolver(JoinPoint joinPoint, RateLimiter rateLimiter) {
        UserDTO userDTO = LoginHelper.getLoginUser();
        String methodName = joinPoint.getSignature().toString();
        String argsStr = CharSequenceUtil.join(",", joinPoint.getArgs());
        String userId = userDTO.getUserId();
        Integer userType = userDTO.getUserType();
        return SecureUtil.md5(methodName + argsStr + userId + userType);
    }
}