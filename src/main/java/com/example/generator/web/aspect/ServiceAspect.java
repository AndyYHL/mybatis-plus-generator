package com.example.generator.web.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

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
public class ServiceAspect {
    /**
     * @annotation()：针对否个注解来定义切面，如下根据@GetMapping进行切面 比如：@GetMapping、@PostMapping、@DeleteMapping
     */
    @Pointcut("execution(* com.example.generator.service..*.*(..))")
    public void pointCut() {
        log.info("@Pointcut：定义一个切面，所关注的某件事入口\n");
    }

    /**
     * @Before：在之前做什么，指定的方法在切面切入目标方法之前执行 pointcut()：定义切面入口方法
     */
    @Before("pointCut()")
    public void before() {
        //业务逻辑
    }

    /**
     * @After：指定的方法在切面切入目标方法之后执行、和@Before注解对应 pointcut()：定义切面入口方法
     */
    @After("pointCut()")
    public void after() {
        //业务逻辑
    }


    /**
     * @AfterReturning ：和 `@After` 有些类似，区别在于 `@AfterReturning` 注解可以用来捕获切入方法执行完之后的返回值，对返回值进行业务逻辑上的增强处理
     * pointcut：切面方法名
     * returning:被切方法的返回值，必须要和参数保持一致，否则会检测不到
     */
    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void afterReturning(String result) {
        //业务逻辑
    }

    /**
     * @AfterThrowing：被切方法执行时抛出异常时会进入 pointcut：切面方法名
     * throwing：属性的值必须要和参数一致
     */
    @AfterThrowing(pointcut = "pointCut()", throwing = "ex")
    public void afterThrowing(Throwable ex) {
        //业务处理
    }
}
