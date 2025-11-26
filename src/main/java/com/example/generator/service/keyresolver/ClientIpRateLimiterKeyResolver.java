package com.example.generator.service.keyresolver;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.generator.pojo.annotation.RateLimiter;
import com.example.generator.service.standard.RateLimiterKeyResolver;
import com.example.generator.util.ServletUtils;
import org.aspectj.lang.JoinPoint;

/**
 * <p>
 * ClientIpRateLimiterKeyResolver描述:用户IP级别
 * <p>
 * 包名称：com.example.generator.service.impl
 * 类名称：ClientIpRateLimiterKeyResolver
 * 全路径：com.example.generator.service.keyresolver.ClientIpRateLimiterKeyResolver
 * 类描述：用户IP级别
 *
 * @author Administrator-YHL
 * @date 2025年09月29日 10:02
 */
public class ClientIpRateLimiterKeyResolver implements RateLimiterKeyResolver {
    @Override
    public String resolver(JoinPoint joinPoint, RateLimiter rateLimiter) {
        String methodName = joinPoint.getSignature().toString();
        String argsStr = StrUtil.join(",", joinPoint.getArgs());
        String clientIp = ServletUtils.getClientIP();
        return SecureUtil.md5(methodName + argsStr + clientIp);
    }
}