package com.example.generator.service.keyresolver;

import cn.hutool.core.util.ArrayUtil;
import com.example.generator.pojo.annotation.RateLimiter;
import com.example.generator.service.standard.RateLimiterKeyResolver;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * <p>
 * ExpressionIdempotentKeyResolver描述:自定义表达式
 * <p>
 * 包名称：com.example.generator.service.impl
 * 类名称：ExpressionIdempotentKeyResolver
 * 全路径：com.example.generator.service.keyresolver.ExpressionIdempotentKeyResolver
 * 类描述：自定义表达式
 *
 * @author Administrator-YHL
 * @date 2025年09月29日 10:03
 */
public class ExpressionIdempotentKeyResolver implements RateLimiterKeyResolver {

    private final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    private final ExpressionParser expressionParser = new SpelExpressionParser();

    @Override
    public String resolver(JoinPoint joinPoint, RateLimiter rateLimiter) {
        // 获得被拦截方法参数名列表
        Method method = getMethod(joinPoint);
        Object[] args = joinPoint.getArgs();
        String[] parameterNames = this.parameterNameDiscoverer.getParameterNames(method);
        // 准备 Spring EL 表达式解析的上下文
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        if (ArrayUtil.isNotEmpty(parameterNames)) {
            for (int i = 0; i < parameterNames.length; i++) {
                evaluationContext.setVariable(parameterNames[i], args[i]);
            }
        }

        // 解析参数
        Expression expression = expressionParser.parseExpression(rateLimiter.keyArg());
        return expression.getValue(evaluationContext, String.class);
    }

    private static Method getMethod(JoinPoint point) {
        // 处理，声明在类上的情况
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        if (!method.getDeclaringClass().isInterface()) {
            return method;
        }

        // 处理，声明在接口上的情况
        try {
            return point.getTarget().getClass().getDeclaredMethod(
                    point.getSignature().getName(), method.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}