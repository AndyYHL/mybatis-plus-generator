package com.example.generator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolConfig描述
 *
 * @author Administrator-YHL
 * @date 2023-9-18
 **/
@Configuration
@EnableScheduling
public class ThreadPoolConfig {
    /**
     * 线程池核心池的大小
     */
    private static final int corePoolSize = 20;

    /**
     * @Primary 优先使用该全局配置线程池
     * 如果不加@primary @async注解默认采用SimpleAsyncTaskExecutor
     * 不加@primary 可使用@async("threadPoolTaskExecutor")指定线程池
     */
    @Primary
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskExecutor() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        /** 核心线程数，默认为1 **/
        threadPoolTaskScheduler.setPoolSize(corePoolSize);
        /**
         * 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
         *
         * AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常
         * CallerRunsPolicy:主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，可以有效降低向线程池内添加任务的速度
         * DiscardOldestPolicy:抛弃旧的任务、暂不支持；会导致被丢弃的任务无法再次被执行
         * DiscardPolicy:抛弃当前任务、暂不支持；会导致被丢弃的任务无法再次被执行
         */
        threadPoolTaskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolTaskScheduler.setThreadNamePrefix("task-scheduling-");
        // 等待时长
        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
        // 调度器shutdown被调用时等待当前被调度的任务完成
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化会自动执行afterPropertiesSet()初始化
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }
}
