package com.example.generator.pay.service;

import com.example.generator.pay.config.RateConfig;
import com.example.generator.pojo.enums.PayChannel;
import com.example.generator.pojo.enums.RateTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * RateConfigService描述:查询所有的通道费率
 * <p>
 * 包名称：com.example.generator.pay.service
 * 类名称：RateConfigService
 * 全路径：com.example.generator.pay.service.RateConfigService
 * 类描述：查询所有的通道费率
 *
 * @author 31756-YHL
 * @date 2025年11月26日 11:20
 */
@Slf4j
@Service
public class RateConfigService {
    public List<RateConfig> getEnabledRateConfigs(List<PayChannel> availableChannels) {
        // 模拟从数据库中查询所有的通道费率
        return List.of(
                new RateConfig(PayChannel.WECHAT_PAY.getCode(), PayChannel.WECHAT_PAY.getName(), RateTypeEnum.PERCENT, new BigDecimal("0.01"), new BigDecimal("0.01"), new BigDecimal("0.1"), 1, true, null),
                new RateConfig(PayChannel.ALI_PAY.getCode(), PayChannel.ALI_PAY.getName(), RateTypeEnum.PERCENT, new BigDecimal("0.02"), new BigDecimal("0.01"), new BigDecimal("0.2"), 2, true, null),
                new RateConfig(PayChannel.UNION_PAY.getCode(), PayChannel.UNION_PAY.getName(), RateTypeEnum.LADDER, new BigDecimal("0.03"), new BigDecimal("0.01"), new BigDecimal("0.4"), 3,
                        true, "[{\"min\":0,\"max\":1000,\"rate\":0.65}," +
                        "{\"min\":1000,\"max\":10000,\"rate\":0.58}," +
                        "{\"min\":10000,\"max\":999999999,\"rate\":0.5}]")
        );
    }
}