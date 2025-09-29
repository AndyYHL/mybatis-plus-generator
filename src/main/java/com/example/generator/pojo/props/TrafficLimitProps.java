package com.example.generator.pojo.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 * TrafficLimitProps描述:获取令牌桶数据
 * <p>
 * 包名称：com.example.generator.pojo.props
 * 类名称：TrafficLimitProps
 * 全路径：com.example.generator.pojo.props.TrafficLimitProps
 * 类描述：获取令牌桶数据
 *
 * @author Administrator-YHL
 * @date 2025年09月29日 10:41
 */
@Data
@Component
public class TrafficLimitProps {
    /**
     * 令牌桶的 key
     */
    @Value("${traffic.limit.key:app}")
    private String key;
    /**
     * 默认恢复速率
     */
    @Value("${traffic.limit.rate:10}")
    private Integer rate;
    /**
     * 默认桶容量
     */
    @Value("${traffic.limit.capacity:100}")
    private Integer capacity;
}