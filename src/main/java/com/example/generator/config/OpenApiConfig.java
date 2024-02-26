package com.example.generator.config;

import com.example.generator.pojo.constant.base.ApiConstant;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public OpenAPI springOpenApi() {
        final String loginToken = "BearerAuth";
        return new OpenAPI().info(new Info().title("Simple Boot API")
                        .description("SpringDoc")
                        .version("v1.0.0")).externalDocs(new ExternalDocumentation()
                        .description("knife4j:接口文档")
                        .url("http://127.0.0.1:8080/doc.html")
                )
                .components(new Components().addSecuritySchemes(loginToken, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name(loginToken)))
                .addSecurityItem(new SecurityRequirement().addList(loginToken));
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
