package com.example.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.PostgreSqlKeyWordsHandler;
import com.example.generator.pojo.domain.base.BaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.junit.platform.commons.util.StringUtils;

import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * CodeGenerator描述 代码生成器 ，先修改下面的常量配置参数，然后执行 main方法
 *
 * @author Administrator-YHL
 * @date 2023-10-23
 **/
public class PostGreSqlCodeGenerator {

    // 数据库连接配置
    private static final String JDBC_URL = "jdbc:postgresql://xxx:5432/jdd2db?currentSchema=jdd2db";
    private static final String JDBC_USER_NAME = "xxx";
    private static final String JDBC_PASSWORD = "xxx";

    // 生成代码入口main方法
    public static void main(String[] args) {
        // 项目存储地址
        String path = System.getProperty("user.dir");
        String finalPath = "D:\\jjjj";
        // 1.数据库配置
        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig.Builder(JDBC_URL, JDBC_USER_NAME, JDBC_PASSWORD)
                .typeConvert(new PostgreSqlTypeConvert())
                .keyWordsHandler(new PostgreSqlKeyWordsHandler())
                .typeConvertHandler(((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.NUMERIC) {
                        return DbColumnType.BIG_DECIMAL;
                    }
                    return typeRegistry.getColumnType(metaInfo);
                }));

        // 1.1.快速生成器
        FastAutoGenerator fastAutoGenerator = FastAutoGenerator.create(dataSourceConfigBuilder);

        // 2.全局配置
        // 覆盖已生成文件
        // 不打开生成文件目录
        // 指定输出目录,注意使用反斜杠
        // 设置注释的作者
        // 设置注释的日期格式
        // 使用java8新的时间类型
        fastAutoGenerator.globalConfig((scanner, globalConfigBuilder) -> {
            //作者名
            globalConfigBuilder.author(scanner.apply("请输入作者名称？"))
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
        });

        // 3.包配置
        // 设置父包名
        // 设置父包模块名
        // 设置MVC下各个模块的包名
        // 设置XML资源文件的目录
        fastAutoGenerator.packageConfig((scanner, packageConfigBuilder) -> {
            // 父包名
            packageConfigBuilder.parent(scanner.apply("请输入父包名？com.example"))
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
        });

        // 4.模板配置
        // 使用Freemarker引擎模板，默认的是Velocity引擎模板
        AbstractTemplateEngine templateEngine = new FreemarkerTemplateEngine();
        fastAutoGenerator.templateEngine(templateEngine);
        // 5.注入配置 TODO

        // 6.策略配置
        // 设置需要生成的表名
        // 设置过滤表前缀
        fastAutoGenerator.strategyConfig((scanner, strategyConfigBuilder) -> {
            strategyConfigBuilder.enableCapitalMode()
                    .enableSkipView()
                    .disableSqlFilter()
                    .addInclude(getTables(scanner.apply("请输入要生成的表名，多个英文逗号分隔？所有输入 all")))
                    .addTablePrefix(scanner.apply("请设置过滤表前缀？"));
        });
        System.out.println("start 生成基础配置");
        // 生成基础配置
        basics(fastAutoGenerator);
        System.out.println("end 生成基础配置");

        System.out.println("start 生成扩展配置");
        // 生成扩展配置
        extend(fastAutoGenerator);
        System.out.println("end 生成扩展配置");
    }

    /**
     * 生成基础配置
     *
     * @param fastAutoGenerator
     */
    protected static void basics(FastAutoGenerator fastAutoGenerator) {
        // 6.1.Entity策略配置
        // 生成实体时生成字段的注解，包括@TableId注解等
        // 数据库表和字段映射到实体的命名策略，为下划线转驼峰
        // 全局主键类型为None
        // 实体名称格式化为XXXEntity
        fastAutoGenerator.strategyConfig((scanner, strategyConfigBuilder) -> {
            strategyConfigBuilder.entityBuilder().enableLombok()
                    // 继承的类
                    .superClass(BaseEntity.class)
                    //不实现 Serializable 接口，不生产 SerialVersionUID
                    .disableSerialVersionUID()
                    // 乐观锁实体类名称
                    .versionPropertyName(Optional.ofNullable(scanner.apply("请输入字段中的乐观锁名称？")).filter(StringUtils::isNotBlank).orElse("version"))
                    .logicDeletePropertyName(Optional.ofNullable(scanner.apply("请输入数据库中的逻辑删除字段入实体名称")).filter(StringUtils::isNotBlank).orElse("deleted"))
                    //数据库表映射到实体的命名策略：下划线转驼峰命
                    .naming(NamingStrategy.underline_to_camel)
                    //数据库表字段映射到实体的命名策略：下划线转驼峰命hh
                    .columnNaming(NamingStrategy.underline_to_camel).idType(IdType.AUTO)
                    //开启生成实体时生成字段注解
                    .enableTableFieldAnnotation()
                    .formatFileName("%sDO")
                    //.convertFileName(fromFileName -> fromFileName + "DO")
                    //添加表字段填充，"create_time"字段自动填充为插入时间，"modify_time"字段自动填充为插入修改时间
                    .addTableFills(
                            new Column("create_time", FieldFill.INSERT),
                            new Column("update_time", FieldFill.INSERT_UPDATE)
                    );
        });

        // 6.2.Controller策略配置
        // 开启生成@RestController控制器
        fastAutoGenerator.strategyConfig(strategyConfigBuilder ->
                        strategyConfigBuilder.controllerBuilder()
                                // 映射路径使用连字符格式，而不是驼峰
                                .enableRestStyle()
                                .formatFileName("%sController")
                // 禁止覆盖
                //.enableFileOverride()
        );

        // 6.3.Service策略配置
        // 格式化service接口和实现类的文件名称，去掉默认的ServiceName前面的I
        fastAutoGenerator.strategyConfig(strategyConfigBuilder ->
                        strategyConfigBuilder.serviceBuilder()
                                //继承
                                .superServiceClass(IService.class)
                                .formatServiceFileName("I%sService")
                                .superServiceImplClass(ServiceImpl.class)
                                .formatServiceImplFileName("%sService")
                // 禁止覆盖
                //.enableFileOverride()
        );

        // 6.4.Mapper策略配置
        // 格式化 mapper文件名,格式化xml实现类文件名称
        fastAutoGenerator.strategyConfig(strategyConfigBuilder ->
                        strategyConfigBuilder.mapperBuilder()
                                .superClass(BaseMapper.class)
                                .mapperAnnotation(Mapper.class)
                                //生成通用的resultMap
                                .enableBaseResultMap()
                                .enableBaseColumnList()
                                .formatMapperFileName("%sMapper")
                                .formatXmlFileName("%sMapper")
                // 禁止覆盖
                //.enableFileOverride()
        );
        // 6.5 模板配置
        fastAutoGenerator.templateConfig(templateConfigBuilder -> {
            //实体类使用我们自定义模板- 禁用模板
            templateConfigBuilder.disable(TemplateType.ENTITY);
            //设置实体模板路径(JAVA)
            templateConfigBuilder.entity("/templates/entity.java");
            //设置 service 模板路径
            templateConfigBuilder.service("/templates/service.java");
            //设置 serviceImpl 模板路径
            templateConfigBuilder.serviceImpl("/templates/serviceImpl.java");
            //设置 mapper 模板路径
            templateConfigBuilder.mapper("/templates/mapper.java");
            //设置 mapperXml 模板路径
            templateConfigBuilder.xml("/templates/mapper.xml");
            //设置 controller 模板路径
            templateConfigBuilder.controller("/templates/controller.java");
        });
        // 7.生成代码
        fastAutoGenerator.execute();
    }

    /**
     * 生成扩展配置
     *
     * @param fastAutoGenerator
     */
    protected static void extend(FastAutoGenerator fastAutoGenerator) {
        // 6.1.Entity策略配置
        // 生成实体时生成字段的注解，包括@TableId注解等
        // 数据库表和字段映射到实体的命名策略，为下划线转驼峰
        // 全局主键类型为None
        // 实体名称格式化为XXXEntity
        fastAutoGenerator.strategyConfig((scanner, strategyConfigBuilder) -> {
            strategyConfigBuilder.entityBuilder().enableLombok()
                    // 继承的类
                    .superClass(BaseEntity.class)
                    //不实现 Serializable 接口，不生产 SerialVersionUID
                    .disableSerialVersionUID()
                    // 乐观锁实体类名称
                    .versionPropertyName(Optional.ofNullable(scanner.apply("请输入字段中的乐观锁名称？")).filter(StringUtils::isNotBlank).orElse("version"))
                    .logicDeletePropertyName(Optional.ofNullable(scanner.apply("请输入数据库中的逻辑删除字段入实体名称")).filter(StringUtils::isNotBlank).orElse("deleted"))
                    //数据库表映射到实体的命名策略：下划线转驼峰命
                    .naming(NamingStrategy.underline_to_camel)
                    //数据库表字段映射到实体的命名策略：下划线转驼峰命hh
                    .columnNaming(NamingStrategy.underline_to_camel).idType(IdType.AUTO)
                    //开启生成实体时生成字段注解
                    .enableTableFieldAnnotation()
                    .formatFileName("%s")
                    //.convertFileName(fromFileName -> fromFileName + "DO")
                    //添加表字段填充，"create_time"字段自动填充为插入时间，"modify_time"字段自动填充为插入修改时间
                    .addTableFills(
                            new Column("create_time", FieldFill.INSERT),
                            new Column("update_time", FieldFill.INSERT_UPDATE)
                    ).enableFileOverride();
        });

        // 6.6 扩展类型生成
        fastAutoGenerator.injectionConfig((scanner, consumer) -> {
            String hasGenerate = scanner.apply("是否生成DO/DTO/VO");
            if (hasGenerate.equals("是")) {
                consumer.customFile(new CustomFile.Builder().fileName("DO.java").templatePath("/templates/entityDO.java.ftl").packageName("pojo.do").enableFileOverride().build())
                        .customFile(new CustomFile.Builder().fileName("DTO.java").templatePath("/templates/entityDTO.java.ftl").packageName("pojo.dto").enableFileOverride().build())
                        .customFile(new CustomFile.Builder().fileName("VO.java").templatePath("/templates/entityVO.java.ftl").packageName("pojo.vo").enableFileOverride().build())
                        .build();
            }
        });
        // 7.生成代码
        fastAutoGenerator.execute();
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