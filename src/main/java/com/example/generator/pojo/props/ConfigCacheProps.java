package com.example.generator.pojo.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * ConfigCacheProps描述:缓存配置
 * <p>
 * 包名称：com.example.generator.pojo.props
 * 类名称：ConfigCacheProps
 * 全路径：com.example.generator.pojo.props.ConfigCacheProps
 * 类描述：缓存配置
 *
 * @author Administrator-YHL
 * @date 2025年09月29日 11:32
 */
@Data
@ConfigurationProperties("pro.cache")
public class ConfigCacheProps {
    /**
     * {@link #redisScanBatchSize} 默认值
     */
    private static final Integer REDIS_SCAN_BATCH_SIZE_DEFAULT = 30;

    /**
     * redis scan 一次返回数量
     */
    private Integer redisScanBatchSize = REDIS_SCAN_BATCH_SIZE_DEFAULT;
}