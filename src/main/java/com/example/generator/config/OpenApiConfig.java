package com.example.generator.config;

import com.example.generator.pojo.constant.base.ApiConstant;
import com.example.generator.pojo.enums.BasicRespCode;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenApiConfig描述
 *
 * @author Administrator-YHL
 * @date 2023-10-18
 **/
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(name = "springdoc.api-docs.enabled", havingValue = "true", matchIfMissing = true)
public class OpenApiConfig {
    /**
     * 单纯设置请求头
     * .components(new Components().addSecuritySchemes(loginToken, new SecurityScheme()
     * .type(SecurityScheme.Type.APIKEY)
     * .in(SecurityScheme.In.HEADER)
     * .name(loginToken)))
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(OpenAPI.class)
    public OpenAPI customOpenApi() {
        final String loginToken = "BearerAuth";
        return new OpenAPI()// 设置认证的请求头,在 Components 中注册一个安全方案
                .components(getComponents())
                //在 OpenAPI 文档中添加一个安全需求,声明 API 调用时需要满足的安全方案
                .addSecurityItem(new SecurityRequirement().addList(loginToken))
                .info(getApiInfo())
                .servers(getServers());
    }

    public SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)//类型：通过 API Key 进行认证
                .name("BearerAuth")//设置 API Key 的名称为 "Authorization"
                .in(SecurityScheme.In.HEADER)//指定 API Key 的传递位置为 HTTP 请求头
                .scheme("Bearer")
                .bearerFormat("JWT");//设置安全方案的具体协议为 "Bearer"
    }

    private Components getComponents() {
        Components components = new Components();
        components.addSecuritySchemes("bearer", securityScheme());

        for (BasicRespCode respCode : BasicRespCode.values()) {
            String statusCode = respCode.getCode();
            components.addResponses(statusCode, new ApiResponse().description(respCode.getDesc()));
        }
        return components;
    }

    private Info getApiInfo() {
        Info info = new Info();
        info.setTitle("聚合支付-前端使用");
        info.setDescription("集合支付");
        info.setContact(new Contact()
                .name("程序员")
                .email("jhddsz@jhddsz.com"));
        info.setLicense(new License()
                .name("许可协议")
                .url("https://mgr.jhddpay.com"));
        info.setVersion("v1.0.0");
        return info;
    }

    private List<Server> getServers() {
        Server serverTest = new Server();
        serverTest.setUrl("https://test-mgr.jhddpay.com/");
        serverTest.setDescription("测试环境");

        Server serverMgr = new Server();
        serverMgr.setUrl("https://mgr.jhddpay.com/");
        serverMgr.setDescription("生产环境");
        return List.of(serverTest, serverMgr);
    }

    @Bean
    public GroupedOpenApi systemApi() {
        return GroupedOpenApi.builder().group("系统接口")
                .pathsToMatch(ApiConstant.API_PREFIX.concat("**"))
                .build();
    }

    @Bean
    public GroupedOpenApi operationApi() {
        return GroupedOpenApi.builder().group("运营接口请求")
                .pathsToMatch(ApiConstant.API_OPERATION_PREFIX.concat("**"))
                .build();
    }

    @Bean
    public GroupedOpenApi platformApi() {
        return GroupedOpenApi.builder().group("平台请求地址")
                .pathsToMatch(ApiConstant.API_PLATFORM_PREFIX.concat("**"))
                .build();
    }
}
