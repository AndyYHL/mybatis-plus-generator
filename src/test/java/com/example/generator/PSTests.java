package com.example.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * PSTests描述:
 * <p>
 * 包名称：com.example.generator
 * 类名称：PSTests
 * 全路径：com.example.generator.PSTests
 * 类描述：
 *
 * @author Administrator-YHL
 * @date 2024年04月15日 10:59
 */
public class PSTests {
    private static final String JDBC_URL = "jdbc:mysql://192.168.70.23:3306/cross_order?serverTimezone=Hongkong&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false";
    private static final String JDBC_USER_NAME = "toyou";
    private static final String JDBC_PASSWORD = "Toyou_123";

    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        String finalPath = "D:\\jjjj";
        FastAutoGenerator.create(JDBC_URL, JDBC_USER_NAME, JDBC_PASSWORD)
                .globalConfig((scanner, builder) -> builder
                        .author(scanner.apply("请输入作者名称？"))
                        .outputDir(finalPath + "/src/main/java")
                        .enableSpringdoc()
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig((scanner, builder) -> builder
                        .parent(scanner.apply("请输入父包名？com.example"))
                        //父包模块名
                        .moduleName(scanner.apply("请输入模块名？generator"))
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig((scanner, builder) -> builder
                        .addInclude(getTables(scanner.apply("请输入要生成的表名，多个英文逗号分隔？所有输入 all")))
                        .entityBuilder()
                        .enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 处理 all 情况
     *
     * @param tables
     * @return
     */
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
