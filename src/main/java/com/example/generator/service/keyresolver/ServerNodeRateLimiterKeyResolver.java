package com.example.generator.service.keyresolver;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.system.SystemUtil;
import com.example.generator.pojo.annotation.RateLimiter;
import com.example.generator.service.standard.RateLimiterKeyResolver;
import org.aspectj.lang.JoinPoint;

/**
 * <p>
 * ServerNodeRateLimiterKeyResolver描述:服务器Node级别
 * <p>
 * 包名称：com.example.generator.service.impl
 * 类名称：ServerNodeRateLimiterKeyResolver
 * 全路径：com.example.generator.service.keyresolver.ServerNodeRateLimiterKeyResolver
 * 类描述：服务器Node级别
 *
 * @author Administrator-YHL
 * @date 2025年09月29日 10:03
 */
public class ServerNodeRateLimiterKeyResolver implements RateLimiterKeyResolver {
    @Override
    public String resolver(JoinPoint joinPoint, RateLimiter rateLimiter) {
        String methodName = joinPoint.getSignature().toString();
        String argsStr = StrUtil.join(",", joinPoint.getArgs());
        String serverNode = String.format("%s@%d", SystemUtil.getHostInfo().getAddress(), SystemUtil.getCurrentPID());
        return SecureUtil.md5(methodName + argsStr + serverNode);
    }
}