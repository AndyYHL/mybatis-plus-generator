package com.example.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.example.generator.pojo.domain.BaseEntity;
import com.google.common.collect.Maps;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * MpGenerator描述 代码生成器演示
 *
 * @author Administrator-YHL
 * @date 2023-10-20
 **/
@SpringBootTest
public class MpGenerator {
    public static void main(String[] args) {
        List<String> tables = new ArrayList<>();
        tables.add("jdd_role");
        tables.add("jdd_user_config");
        String path = System.getProperty("user.dir");
        String finalPath = "D:\\jjjj";
        FastAutoGenerator.create("jdbc:postgresql://xxx:5432/jdd2db?currentSchema=jdd2db",
                        "xxx",
                        "xxx")
                .globalConfig((scanner, builder) -> {
                    //作者名
                    builder.author(scanner.apply("请输入作者名称？"))
                            //输出路径(写到java目录)
                            .outputDir(finalPath + "\\src\\main\\java")
                            //开启 kotlin 模式 默认值:false
                            // .enableKotlin()
                            //开启swagger 默认值:false
                            .enableSpringdoc()
                            // 禁止打开输出目录
                            .disableOpenDir()
                            //时间策略 DateType.ONLY_DATE 默认值: DateType.TIME_PACK
                            .dateType(DateType.TIME_PACK)
                            //注释日期
                            .commentDate("yyyy-MM-dd");

                })
                .packageConfig((scanner, builder) -> {
                    // 父包名
                    builder.parent(scanner.apply("请输入父包名？com.example"))
                            //父包模块名
                            .moduleName(scanner.apply("请输入模块名？generator"))
                            //Entity 包名
                            .entity("pojo.domain")
                            //Service 包名
                            .service("service.standard")
                            //Service Impl 包名
                            .serviceImpl("service.impl")
                            //Controller 包名
                            .controller("web.controller")
                            //Mapper 包名
                            .mapper("repository")
                            //Mapper XML 包名
                            .xml("mapper")
                            //路径配置信息
                            .pathInfo(Collections.singletonMap(OutputFile.xml, finalPath + "\\src\\main\\resources\\mapper"));
                })
                .strategyConfig((scanner, builder) -> {
                    // 设置需要生成的表名
                    builder.addInclude(getTables(scanner.apply("请输入要生成的表名，多个英文逗号分隔？所有输入 all")))
                            // 设置过滤表前缀
                            .addTablePrefix(scanner.apply("请设置过滤表前缀？"))
                            // 增加过滤表前缀
                            .addTablePrefix(scanner.apply("请增加过滤表前缀？"))
                            // service 策略配置
                            .serviceBuilder()
                            //继承
                            .superServiceClass(IService.class)
                            .formatServiceFileName("I%sService")
                            //继承
                            .superServiceImplClass(ServiceImpl.class)
                            .formatServiceImplFileName("%sService")
                            .enableFileOverride()

                            //实体类策略配置
                            .entityBuilder()
                            .enableLombok()
                            // 继承的类
                            .superClass(BaseEntity.class)
                            //不实现 Serializable 接口，不生产 SerialVersionUID
                            .disableSerialVersionUID()
                            // 乐观锁实体类名称
                            .versionPropertyName(scanner.apply("请输入字段中的乐观锁名称？"))
                            .logicDeletePropertyName(scanner.apply("请输入数据库中的逻辑删除字段入实体名称"))
                            //数据库表映射到实体的命名策略：下划线转驼峰命
                            .naming(NamingStrategy.underline_to_camel)
                            //数据库表字段映射到实体的命名策略：下划线转驼峰命
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .logicDeleteColumnName("deleted")
                            .convertFileName(fromFileName -> fromFileName + "DO")

                            .addTableFills(
                                    new Column("create_time", FieldFill.INSERT),
                                    new Column("update_time", FieldFill.INSERT_UPDATE)
                            )   //添加表字段填充，"create_time"字段自动填充为插入时间，"modify_time"字段自动填充为插入修改时间
                            .enableFileOverride()
                            // 开启生成实体时生成字段注解
                            //.enableTableFieldAnnotation()


                            //Controller策略配置
                            .controllerBuilder()
                            // 映射路径使用连字符格式，而不是驼峰
                            .enableHyphenStyle()
                            .formatFileName("%sController")
                            .enableFileOverride()
                            //开启生成 @RestController 控制器
                            .enableRestStyle()

                            //Mapper策略配置
                            .mapperBuilder()
                            //生成通用的resultMap
                            .enableBaseResultMap()
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .enableFileOverride()
                            //开启 @Mapper 注解
                            .enableMapperAnnotation()
                            .formatXmlFileName("%sMapper");
                })
                .templateConfig(builder -> {
                    //实体类使用我们自定义模板- 禁用模板
                    builder.disable(TemplateType.ENTITY);
                    //设置实体模板路径(JAVA)
                    builder.entity("/templates/entity.java");
                    //设置 service 模板路径
                    builder.service("/templates/service.java");
                    //设置 serviceImpl 模板路径
                    builder.serviceImpl("/templates/serviceImpl.java");
                    //设置 mapper 模板路径
                    builder.mapper("/templates/mapper.java");
                    //设置 mapperXml 模板路径
                    builder.xml("/templates/mapper.xml");
                    //设置 controller 模板路径
                    builder.controller("/templates/controller.java");
                })
                .injectionConfig(consumer -> {
                    Map<String, String> customFile = Maps.newHashMap();
                    customFile.put("DO.java", "/templates/entityDO.java.ftl");
                    customFile.put("DTO.java", "/templates/entityDTO.java.ftl");
                    customFile.put("VO.java", "/templates/entityVO.java.ftl");
                    consumer.customFile(customFile);
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
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
