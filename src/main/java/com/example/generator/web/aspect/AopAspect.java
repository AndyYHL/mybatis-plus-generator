package com.example.generator.web.aspect;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * AopAspect描述
 *
 * @author Administrator-YHL
 * @date 2023-10-25
 **/
@Slf4j
@Aspect
@Component
@SuppressWarnings(value = "all")
@EnableAspectJAutoProxy
public class AopAspect {
    /**
     * @annotation()：针对否个注解来定义切面，如下根据@GetMapping进行切面 比如：@GetMapping、@PostMapping、@DeleteMapping
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void pointCutApi() {
        log.info("@Pointcut：pointCutApi定义一个切面，所关注的某件事入口");
    }

    @Pointcut("execution(* com.example.generator.web.api..*(..))")
    public void pointCutApi2() {
        log.info("@Pointcut：pointCutApi2定义一个切面，所关注的某件事入口");
    }

    /**
     * @Before：在之前做什么，指定的方法在切面切入目标方法之前执行 pointcut()：定义切面入口方法
     */
    @Before("pointCutApi() || pointCutApi2()")
    public void before() {
        //业务逻辑
    }

    /**
     * @After：指定的方法在切面切入目标方法之后执行、和@Before注解对应 pointcut()：定义切面入口方法
     */
    @After("pointCutApi() || pointCutApi2()")
    public void after() {
        //业务逻辑
    }


    /**
     * @AfterReturning ：和 `@After` 有些类似，区别在于 `@AfterReturning` 注解可以用来捕获切入方法执行完之后的返回值，对返回值进行业务逻辑上的增强处理
     * pointcut：切面方法名
     * returning:被切方法的返回值，必须要和参数保持一致，否则会检测不到
     */
    @AfterReturning(pointcut = "pointCutApi() || pointCutApi2()", returning = "result")
    public void afterReturning(String result) {
        //业务逻辑
    }

    /**
     * @AfterThrowing：被切方法执行时抛出异常时会进入 pointcut：切面方法名
     * throwing：属性的值必须要和参数一致
     */
    @AfterThrowing(pointcut = "pointCutApi() || pointCutApi2()", throwing = "ex")
    public void afterThrowing(Throwable ex) {
        //业务处理
    }

    /**
     * 环绕通知需要携带 ProceedingJoinPoint 类型的参数.      * 环绕通知类似于动态代理的全过程: ProceedingJoinPoint 类型的参数可以决定是否执行目标方法.     * 且环绕通知必须有返回值, 返回值即为目标方法的返回值
     */
    @Around("pointCutApi() || pointCutApi2()")
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
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            //获取请求路径
            String uri = request.getRequestURI();
            String contentType = request.getContentType();
            Object[] objects = pjd.getArgs();
            for (Object o : objects) {
                if (o instanceof HttpServletRequest || o instanceof HttpServletResponse) {
                    log.info("url:【{}】,req:【{JAVA系统对象}】", uri);
                } else if (StringUtils.isNotBlank(contentType) && contentType.contains("application/json")) {
                    log.info("url:【{}】,req:【{}】", uri, JSON.toJSONString(pjd.getArgs()));
                } else {
                    log.info("url:【{}】,req:【{}】", uri, pjd.getArgs());
                }
            }
            // 执行目标方法
            result = pjd.proceed();
            //返回通知
            clock.stop();
        } catch (Throwable e) {
            //异常通知
            log.error(JSON.toJSONString(e.getStackTrace()));
        }
        //后置通知
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
