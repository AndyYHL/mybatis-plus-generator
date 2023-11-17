package com.example.generator.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeException;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.example.generator.pojo.container.ApiRequest;
import com.example.generator.pojo.container.ApiResponse;
import com.example.generator.pojo.param.UserParam;
import com.example.generator.web.api.channel.ILoginApi;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * LoginController描述
 *
 * @author Administrator-YHL
 * @date 2023-11-8
 **/
@Slf4j
@RestController
public class LoginController implements ILoginApi {

    @Autowired
    private QrConfig config;

    @Override
    public ApiResponse<String> doLogin(ApiRequest<UserParam> req) {
        UserParam param = req.getParam();
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if ("admin".equals(param.getUserName()) && "admin".equals(param.getUserId())) {
            StpUtil.login(10001);
            return ApiResponse.success("登录成功");
        }
        return ApiResponse.fail("登录失败");
    }

    @Override
    public ApiResponse<String> isLogin(ApiRequest<UserParam> req) {
        return ApiResponse.success("当前会话是否登录：".concat(String.valueOf(StpUtil.isLogin())));
    }

    @Override
    public ApiResponse<String> getClassQr(String content, HttpServletResponse response) {
        try {
            // 生成字节数组输出流
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            QrCodeUtil.generate(content, config, "png", byteOut);
            // 生成字节数组输入流
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            // 从流中读取图片,返回图片文件
            BufferedImage img = ImgUtil.read(byteIn);
            // 将图片对象转换为Base64形式
            String base64 = ImgUtil.toBase64(img, "png");
            return ApiResponse.success(base64);
        } catch (QrCodeException e) {
            return ApiResponse.fail("失败");
        }
    }

    @Override
    public BufferedImage getClassQr1(String content) {
        return QrCodeUtil.generate(content, config);
    }

    @Override
    public BufferedImage getClassQrZuHe(String content, Integer x, Integer y) {
        // 读取图片
        BufferedImage image = null;
        try {
            image = ImageIO.read(new FileInputStream("C:\\Users\\Administrator\\Desktop\\doubleScreen\\images\\nodesign.png"));
            Image image1 = ImgUtil.pressText(
                    image,
                    "版权所有:" + content + "x:" + image.getWidth() + "y:" + image.getHeight(), Color.WHITE, //文字
                    new Font("黑体", Font.BOLD, 20), //字体
                    x, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                    y, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                    0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
            );
            image = ImgUtil.toBufferedImage(image1);
            String url = "https://www.baidu.com/s?wd=" + content;
            BufferedImage qr = QrCodeUtil.generate(url, config);
            Graphics2D g = image.createGraphics();
            g.drawImage(qr, 50, 50, null);
            g.dispose();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
