package com.example.generator;

import cn.dev33.satoken.SaManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 */
@Slf4j
@SpringBootApplication
public class MybatisPlusGeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusGeneratorApplication.class, args);
        log.info("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }
}
