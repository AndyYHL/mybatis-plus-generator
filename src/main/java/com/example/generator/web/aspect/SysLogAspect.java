package com.example.generator.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * SysLogAspect描述
 *
 * @author Administrator-YHL
 * @date 2023-10-24
 **/
@Slf4j
@Aspect
@Component
public class SysLogAspect {
    /**
     * 切入点：表示在哪个类的哪个方法进行切入。配置有切入点表达式
     */
    @Pointcut("execution(* com.example.generator.web.controller.*.*.*(..))")
    public void pointcutExpression() {
        log.debug("配置切入点");
    }

    /**
     * 1 前置通知     * @param joinPoint
     */
    @Before("pointcutExpression()")
    public void beforeMethod(JoinPoint joinPoint) {
        log.debug("前置通知执行了");
    }

    /**
     * 2 后置通知     * 在方法执行之后执行的代码. 无论该方法是否出现异常
     */
    @After("pointcutExpression()")
    public void afterMethod(JoinPoint joinPoint) {
        log.debug("后置通知执行了，有异常也会执行");
    }

    /**
     * 3 返回通知     * 在方法法正常结束受执行的代码     * 返回通知是可以访问到方法的返回值的!     * @param joinPoint     * @param returnValue
     */
    @AfterReturning(value = "pointcutExpression()", returning = "returnValue")
    public void afterRunningMethod(JoinPoint joinPoint, Object returnValue) {
        log.debug("返回通知执行，执行结果：" + returnValue);
    }

    /**
     * 4 异常通知     * 在目标方法出现异常时会执行的代码.     * 可以访问到异常对象; 且可以指定在出现特定异常时在执行通知代码     * @param joinPoint     * @param e
     */
    @AfterThrowing(value = "pointcutExpression()", throwing = "e")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception e) {
        log.debug("异常通知, 出现异常 " + e);
    }

    /**
     * 环绕通知需要携带 ProceedingJoinPoint 类型的参数.      * 环绕通知类似于动态代理的全过程: ProceedingJoinPoint 类型的参数可以决定是否执行目标方法.     * 且环绕通知必须有返回值, 返回值即为目标方法的返回值
     */
    @Around("pointcutExpression()")
    public Object aroundMethod(ProceedingJoinPoint pjd) {
        StopWatch clock = new StopWatch();
        //返回的结果
        Object result = null;
        //方法名称
        String className = pjd.getTarget().getClass().getName();
        String methodName = pjd.getSignature().getName();
        try {
            // 计时开始
            clock.start();
            //前置通知
            // 执行目标方法
            result = pjd.proceed();
            //返回通知
            clock.stop();
        } catch (Throwable e) {
            //异常通知
            e.printStackTrace();
        }        //后置通知
        if (!methodName.equalsIgnoreCase("initBinder")) {
            long constTime = clock.getTime();
            log.info("[" + className + "]" + "-" + "[" + methodName + "]" + " 花费时间：" + constTime + "ms");
            if (constTime > 500) {
                log.error("[" + className + "]" + "-" + "[" + methodName + "]" + " 花费时间过长，请检查: " + constTime + "ms");
            }
        }
        return result;
    }
}
