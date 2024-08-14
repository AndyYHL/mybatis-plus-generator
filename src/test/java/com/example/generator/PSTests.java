package com.example.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import org.apache.ibatis.annotations.Mapper;
import org.junit.platform.commons.util.StringUtils;

import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        // 1.数据库配置
        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig.Builder(JDBC_URL, JDBC_USER_NAME, JDBC_PASSWORD)
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler())
                .typeConvertHandler(((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.NUMERIC) {
                        return DbColumnType.BIG_DECIMAL;
                    }
                    return typeRegistry.getColumnType(metaInfo);
                }));

        FastAutoGenerator fastAutoGenerator = FastAutoGenerator.create(dataSourceConfigBuilder)
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
                        .pathInfo(Collections.singletonMap(OutputFile.xml, finalPath + "\\src\\main\\resources\\mapper"))
                )
                .strategyConfig((scanner, builder) -> builder
                        .addInclude(getTables(scanner.apply("请输入要生成的表名，多个英文逗号分隔？所有输入 all")))
                        .entityBuilder()
                );
        // 启动
        basics(fastAutoGenerator);
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
                    .superClass(Object.class)
                    // 开启注解
                    .enableLombok()
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
                                .superClass(Object.class)
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
}
