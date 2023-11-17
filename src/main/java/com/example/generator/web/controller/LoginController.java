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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
            image = ImageIO.read(new FileInputStream("C:\\Users\\Administrator\\Desktop\\base\\25175591_115124404035_2.png"));
            String txt = "版权所有:" + content + "x:" + image.getWidth() + "y:" + image.getHeight();
            int px = (image.getWidth() / 2 - BigDecimal.valueOf(txt.length() * 7.22).setScale(0, RoundingMode.UP).intValue() - x);
            int py = (image.getHeight() / 2 - 20 + y);
            log.info("x:{},y:{},长度:{}", px, py, txt.length());
            Image image1 = ImgUtil.pressText(
                    image,
                    txt, Color.WHITE, //文字
                    new Font("黑体", Font.BOLD, 20), //字体
                    px, //x坐标修正值。 默认在中间，偏移量相对于中间偏移
                    py, //y坐标修正值。 默认在中间，偏移量相对于中间偏移
                    0.8f//透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
            );
            image = ImgUtil.toBufferedImage(image1);
            String url = "https://www.baidu.com/s?wd=" + content;
            BufferedImage qr = QrCodeUtil.generate(url, config);
            Graphics2D g = image.createGraphics();
            g.drawImage(qr, 230, 525, null);
            g.dispose();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadFileInputStreamResource() {
        BufferedImage bufferedImage = this.getClassQrZuHe("11111", 0, 0);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
        // 创建InputStreamResource对象，封装文件输入流
        InputStreamResource resource = new InputStreamResource(inputStream);
        // 设置响应头，指定文件名
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment().filename("file.png").build());
        // 返回InputStreamResource对象和响应头
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @Override
    public ResponseEntity<ByteArrayResource> downloadFileByteArrayResource() {
        BufferedImage bufferedImage = this.getClassQrZuHe("11111", 0, 0);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 设置响应头，指定文件名
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("file9.png").build());

        ByteArrayResource resource = new ByteArrayResource(os.toByteArray());
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
