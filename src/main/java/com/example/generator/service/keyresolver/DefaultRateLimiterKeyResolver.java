package com.example.generator.service.keyresolver;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.generator.pojo.annotation.RateLimiter;
import com.example.generator.service.standard.RateLimiterKeyResolver;
import org.aspectj.lang.JoinPoint;

/**
 * <p>
 * DefaultRateLimiterKeyResolver描述:全局
 * <p>
 * 包名称：com.example.generator.service.impl
 * 类名称：DefaultRateLimiterKeyResolver
 * 全路径：com.example.generator.service.keyresolver.DefaultRateLimiterKeyResolver
 * 类描述：全局
 *
 * @author Administrator-YHL
 * @date 2025年09月29日 10:01
 */
public class DefaultRateLimiterKeyResolver implements RateLimiterKeyResolver {
    @Override
    public String resolver(JoinPoint joinPoint, RateLimiter rateLimiter) {
        String methodName = joinPoint.getSignature().toString();
        String argsStr = StrUtil.join(",", joinPoint.getArgs());
        return SecureUtil.md5(methodName + argsStr);
    }
}