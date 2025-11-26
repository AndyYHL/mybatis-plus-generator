package com.example.generator.service.standard;

import com.example.generator.pojo.annotation.RateLimiter;
import org.aspectj.lang.JoinPoint;

/**
 * <p>
 * RateLimiterKeyResolver描述:限流拦截
 * <p>
 * 包名称：com.example.generator.service.standard
 * 类名称：RateLimiterKeyResolver
 * 全路径：com.example.generator.service.standard.RateLimiterKeyResolver
 * 类描述：限流拦截
 *
 * @author Administrator-YHL
 * @date 2025年09月29日 09:53
 */
public interface RateLimiterKeyResolver {
    /**
     * 解析一个 Key
     *
     * @param rateLimiter 限流注解
     * @param joinPoint  AOP 切面
     * @return Key
     */
    String resolver(JoinPoint joinPoint, RateLimiter rateLimiter);
}