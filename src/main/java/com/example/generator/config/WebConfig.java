package com.example.generator.config;

import com.example.generator.web.interceptor.RequestHeaderContextInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig描述 绑定拦截器
 *
 * @author Administrator-YHL
 * @date 2023-10-25
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor requestHeaderContextInterceptor() {
        return new RequestHeaderContextInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestHeaderContextInterceptor()).addPathPatterns("/api/**");
        /*registry.addInterceptor(requestHeaderContextInterceptor())
                .excludePathPatterns("/","/account/login","/webjars/**","/swagger-resources/**","/swagger-ui/**","/v3/**")
                .addPathPatterns("/**");*/
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }
}
