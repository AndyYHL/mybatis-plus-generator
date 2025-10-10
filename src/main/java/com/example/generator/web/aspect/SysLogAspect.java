package com.example.generator.web.aspect;

import com.alibaba.fastjson2.JSON;
import com.example.generator.pojo.exception.BasicException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * SysLogAspect描述
 *
 * @author Administrator-YHL
 * @date 2023-10-24
 **/
@Slf4j
@Aspect
@Component
@SuppressWarnings(value = "all")
@EnableAspectJAutoProxy
public class SysLogAspect {
    /**
     * 切入点：表示在哪个类的哪个方法进行切入。配置有切入点表达式
     */
    //@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping)")
    @Pointcut("execution (* com.example.generator.web.controller..*.*(..))")
    public void pointcutExpression() {
        log.info("配置切入点");
    }

    /**
     * 1 前置通知     * @param joinPoint
     */
    @Before("pointcutExpression()")
    public void beforeMethod(@NotNull JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        log.info("【请求时间】:{},【请求 URL】：{},【请求 IP】：{},【请求参数】：{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")), request.getRequestURL(), request.getRemoteAddr(), JSON.toJSONString(joinPoint.getArgs()));
    }

    /**
     * 2 后置通知     * 在方法执行之后执行的代码. 无论该方法是否出现异常
     */
    @After("pointcutExpression()")
    public void afterMethod(JoinPoint joinPoint) {
        log.warn("后置通知执行了，有异常也会执行");
    }

    /**
     * 3 返回通知     * 在方法法正常结束受执行的代码     * 返回通知是可以访问到方法的返回值的!     * @param joinPoint     * @param returnValue
     */
    @SneakyThrows
    @AfterReturning(value = "pointcutExpression()", returning = "returnValue")
    public void afterRunningMethod(@NotNull JoinPoint joinPoint, Object returnValue) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        log.info("【返回时间】:{},【请求 URL】：{},【返回参数】：{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")), request.getRequestURL(), JSON.toJSONString(returnValue));
    }

    /**
     * 4 异常通知     * 在目标方法出现异常时会执行的代码.     * 可以访问到异常对象; 且可以指定在出现特定异常时在执行通知代码     * @param joinPoint     * @param e
     */
    @AfterThrowing(value = "pointcutExpression()", throwing = "e")
    public void afterThrowingMethod(JoinPoint joinPoint, Exception e) {
        log.error("异常通知, 出现异常 " + e);
        e.printStackTrace();
        throw new BasicException(e.getMessage());
    }

    /**
     * 环绕通知需要携带 ProceedingJoinPoint 类型的参数.      * 环绕通知类似于动态代理的全过程: ProceedingJoinPoint 类型的参数可以决定是否执行目标方法.     * 且环绕通知必须有返回值, 返回值即为目标方法的返回值
     */
    @Around("pointcutExpression()")
    public Object aroundMethod(@NotNull ProceedingJoinPoint pjd) {
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
            log.warn("环绕异常通知, 出现异常 " + e);
        }        //后置通知
        if (!"initBinder".equalsIgnoreCase(methodName)) {
            long constTime = clock.getTime();
            log.info("[" + className + "]" + "-" + "[" + methodName + "]" + " 花费时间：" + constTime + "ms");
            if (constTime > 500) {
                log.error("[" + className + "]" + "-" + "[" + methodName + "]" + " 花费时间过长，请检查: " + constTime + "ms");
            }
        }
        return result;
    }
    /*@Around("pointcutExpression()")
    public Object aroundMethod(ProceedingJoinPoint pjd) {
        StopWatch clock = new StopWatch();
        UserDTO userDTO = LoginHelper.getLoginUser();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String uri = request.getRequestURI();//获取请求路径
        //返回的结果
        Object result = null;
        //方法名称
        String className = pjd.getTarget().getClass().getName();
        String methodName = pjd.getSignature().getName();
        try {
            // 计时开始
            clock.start();
            //前置通知
            MDC.put("TRACE_ID", String.format("%s(%s)", userDTO.getUserId(), userDTO.getUserName()));
            MDC.put("reqFile", String.format("%s-%s-%s-%s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")),
                    userDTO.getUserId(), userDTO.getUserName(), uri.replaceAll("/", "-")));
            log.info("url:【{}】,req:【{}】", uri, JSON.toJSONString(pjd.getArgs()));
            // 执行目标方法
            result = pjd.proceed();
            //返回通知
            clock.stop();
        } catch (Throwable e) {
            //异常通知
            log.warn("SysLogAspect: getLoginUser :{} ",e.getMessage());
        } finally {
            if (!methodName.equalsIgnoreCase("initBinder")) {
                long constTime = clock.getTime();
                log.info("[" + className + "]" + "-" + "[" + methodName + "]" + " 花费时间：" + constTime + "ms");
                if (constTime > 500) {
                    log.error("[" + className + "]" + "-" + "[" + methodName + "]" + " 花费时间过长，请检查: " + constTime + "ms");
                }
            }
            MDC.clear();
        }
        return result;
    }*/
}
