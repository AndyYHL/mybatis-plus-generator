package com.example.generator.pay.service;

import com.example.generator.pay.config.RouteConfig;
import com.example.generator.pay.factory.AbstractPayChannelFactory;
import com.example.generator.pay.selector.PaymentRouteSelector;
import com.example.generator.pay.strategy.PayStrategy;
import com.example.generator.pay.strategy.PayStrategyFactory;
import com.example.generator.pojo.enums.PayChannel;
import com.example.generator.pojo.exception.BasicException;
import com.example.generator.pojo.request.NotifyRequest;
import com.example.generator.pojo.request.PayRequest;
import com.example.generator.pojo.request.RefundRequest;
import com.example.generator.pojo.response.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * AggregateFactoryPay描述:工厂模式 聚合支付服务 - 支持费率动态路由
 * <p>
 * 包名称：com.example.generator.pay.service
 * 类名称：AggregateFactoryPay
 * 全路径：com.example.generator.pay.service.AggregateFactoryPay
 * 类描述：工厂模式
 *
 * @author 31756-YHL
 * @date 2025年11月26日 11:08
 */
@Slf4j
@Service
public class AggregateFactoryPay {
    @Autowired
    private PaymentRouteSelector routeSelector;

    @Autowired
    private PayStrategyFactory payStrategyFactory;

    @Autowired
    private List<AbstractPayChannelFactory> channelFactories;

    /**
     * 智能路由支付 - 自动选择最优费率渠道
     */
    public PayResponse smartPay(PayRequest request) {
        // 1. 根据费率选择最优渠道
        RouteConfig route = routeSelector.selectOptimalRoute(
                request.getAmount(),
                getAvailableChannels()
        );
        try {
            // 2. 设置选中的支付渠道
            request.setPayChannel(PayChannel.findByCodeEnum(route.getChannelCode()));
        } catch (Throwable e) {
            throw new BasicException("没有获取到合适的通道");
        }
        // 3. 记录路由信息
        log.info("智能路由结果: 订单={}, 选择渠道={}, 手续费={}",
                request.getOrderNo(), route.getChannelCode(), route.getActualFee());
        // 4. 执行支付
        PayStrategy strategy = payStrategyFactory.getStrategy(request.getPayChannel());
        PayResponse response = strategy.pay(request);

        // 5. 附加费率信息到响应
        response.setActualFee(route.getActualFee());
        response.setRouteReason(route.getRouteReason());

        return response;
    }

    /**
     * 传统支付 - 指定渠道支付
     */
    public PayResponse pay(PayRequest request) {
        PayStrategy strategy = payStrategyFactory.getStrategy(request.getPayChannel());
        return strategy.pay(request);
    }

    /**
     * 查询订单
     */
    public QueryResponse queryOrder(PayRequest request) {
        PayStrategy strategy = payStrategyFactory.getStrategy(request.getPayChannel());
        return strategy.queryOrder(request);
    }

    /**
     * 关闭订单
     */
    public CloseResponse close(PayRequest request) {
        PayStrategy strategy = payStrategyFactory.getStrategy(request.getPayChannel());
        return strategy.close(request);
    }

    /**
     * 退款
     */
    public RefundResponse refund(PayChannel payChannel, RefundRequest request) {
        PayStrategy strategy = payStrategyFactory.getStrategy(payChannel);
        return strategy.refund(request);
    }

    /**
     * 退款查询
     */
    public RefundResponse queryRefund(PayChannel payChannel, RefundRequest request) {
        PayStrategy strategy = payStrategyFactory.getStrategy(payChannel);
        return strategy.queryRefund(request);
    }

    /**
     * 处理回调
     */
    public NotifyResponse handleNotify(NotifyRequest request) {
        PayStrategy strategy = payStrategyFactory.getStrategy(request.getPayChannel());
        return strategy.handleNotify(request);
    }

    /**
     * 获取可用渠道列表
     */
    private List<PayChannel> getAvailableChannels() {
        return channelFactories.stream()
                .filter(AbstractPayChannelFactory::healthCheck)
                .map(AbstractPayChannelFactory::getPayChannel)
                .collect(Collectors.toList());
    }

    /**
     * -- 费率配置表
     * CREATE TABLE `pay_rate_config` (
     *   [id](file://D:\ideaPro\mybatis-plus-generator\src\main\java\com\example\generator\pojo\domain\base\BaseEntity.java#L28-L29) BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
     *   `channel_code` VARCHAR(32) NOT NULL COMMENT '渠道编码',
     *   `channel_name` VARCHAR(64) NOT NULL COMMENT '渠道名称',
     *   `rate_type` VARCHAR(16) NOT NULL COMMENT '费率类型: FIXED/PERCENT/LADDER',
     *   `rate_value` DECIMAL(10,4) COMMENT '费率值',
     *   `min_fee` DECIMAL(10,2) COMMENT '最低手续费',
     *   `max_fee` DECIMAL(10,2) COMMENT '最高手续费',
     *   `ladder_config` TEXT COMMENT '阶梯费率配置(JSON)',
     *   `priority` INT DEFAULT 99 COMMENT '优先级',
     *   `weight` INT DEFAULT 100 COMMENT '权重(负载均衡)',
     *   `enabled` TINYINT(1) DEFAULT 1 COMMENT '是否启用',
     *   `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
     *   `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     *   PRIMARY KEY ([id](file://D:\ideaPro\mybatis-plus-generator\src\main\java\com\example\generator\pojo\domain\base\BaseEntity.java#L28-L29)),
     *   UNIQUE KEY `uk_channel` (`channel_code`)
     * ) ENGINE=InnoDB COMMENT='支付费率配置表';
     *
     * -- 路由日志表
     * CREATE TABLE `pay_route_log` (
     *   [id](file://D:\ideaPro\mybatis-plus-generator\src\main\java\com\example\generator\pojo\domain\base\BaseEntity.java#L28-L29) BIGINT NOT NULL AUTO_INCREMENT,
     *   `order_no` VARCHAR(64) NOT NULL COMMENT '订单号',
     *   [amount](file://D:\ideaPro\mybatis-plus-generator\src\main\java\com\example\generator\pojo\request\PayRequest.java#L49-L49) DECIMAL(10,2) NOT NULL COMMENT '支付金额',
     *   `selected_channel` VARCHAR(32) COMMENT '选中渠道',
     *   `actual_fee` DECIMAL(10,4) COMMENT '实际手续费',
     *   `route_reason` VARCHAR(256) COMMENT '路由原因',
     *   `candidate_channels` TEXT COMMENT '候选渠道(JSON)',
     *   `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
     *   PRIMARY KEY ([id](file://D:\ideaPro\mybatis-plus-generator\src\main\java\com\example\generator\pojo\domain\base\BaseEntity.java#L28-L29)),
     *   KEY `idx_order_no` (`order_no`)
     * ) ENGINE=InnoDB COMMENT='支付路由日志表';
     */
}