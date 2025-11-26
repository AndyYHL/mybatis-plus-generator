package com.example.generator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;

/**
 * <p>
 * ConfigurationBean描述:图片转换器
 * <p>
 * 包名称：com.example.generator.config
 * 类名称：ConfigurationBean
 * 全路径：com.example.generator.config.ConfigurationBean
 * 类描述：图片转换器
 *
 * @author Administrator-YHL
 * @date 2023年11月17日 09:13
 */
@Configuration
public class ConfigurationBean {
    @Bean
    public BufferedImageHttpMessageConverter bufferedImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
}
