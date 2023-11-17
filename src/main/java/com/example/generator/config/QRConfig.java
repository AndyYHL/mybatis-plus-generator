package com.example.generator.config;

import cn.hutool.extra.qrcode.QrConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

/**
 * <p>
 * QRConfig描述:二维码
 * <p>
 * 包名称：com.example.generator.config
 * 类名称：QRConfig
 * 全路径：com.example.generator.config.QRConfig
 * 类描述：二维码
 *
 * @author Administrator-YHL
 * @date 2023年11月16日 17:43
 */
@Configuration
public class QRConfig {

    //采用JavaConfig的方式显示注入hutool中 生成二维码
    @Bean
    public QrConfig qrConfig() {
        //初始宽度和高度
        QrConfig qrConfig = new QrConfig(300, 300);
        //设置边距，即二维码和边框的距离
        qrConfig.setMargin(2);
        //设置前景色
        qrConfig.setForeColor(Color.BLACK);
        //设置背景色
        qrConfig.setBackColor(Color.WHITE);
        return qrConfig;
    }
}
