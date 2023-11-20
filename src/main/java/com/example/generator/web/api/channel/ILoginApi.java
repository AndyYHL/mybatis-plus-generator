package com.example.generator.web.api.channel;

import com.example.generator.pojo.constant.apicode.LoginApiCode;
import com.example.generator.pojo.constant.base.ApiConstant;
import com.example.generator.pojo.container.ApiRequest;
import com.example.generator.pojo.container.ApiResponse;
import com.example.generator.pojo.param.UserParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * ILoginApi描述
 *
 * @author Administrator-YHL
 * @date 2023-11-8
 **/
@Tag(name = "登录-操作接口", description = "登录-操作描述")
public interface ILoginApi {
    /**
     * 登录
     *
     * @param req 登录
     * @return 登录
     */
    @Operation(summary = "登录", description = "登录")
    @PostMapping(value = {ApiConstant.API_PLATFORM_PREFIX + LoginApiCode.BASE_URL + LoginApiCode.DO_LOGIN})
    ApiResponse<String> doLogin(@RequestBody @Valid ApiRequest<UserParam> req);

    /**
     * 查询登录状态
     *
     * @param req 查询登录状态
     * @return 查询登录状态
     */
    @Operation(summary = "查询登录状态", description = "查询登录状态")
    @PostMapping(value = {ApiConstant.API_PLATFORM_PREFIX + LoginApiCode.BASE_URL + LoginApiCode.IS_LOGIN})
    ApiResponse<String> isLogin(@RequestBody @Valid ApiRequest<UserParam> req);

    /**
     * 返回图片信息 base 64
     *
     * @param content
     * @return
     */
    @PostMapping(value = "/getClassQr/{content}")
    ApiResponse<String> getClassQr(@PathVariable("content") String content, HttpServletResponse response);

    /**
     * 返回图片信息
     *
     * @param content
     * @return
     */
    @GetMapping(value = "/getClassQr1/{content}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    BufferedImage getClassQr1(@PathVariable("content") String content);

    /**
     * 返回图片信息
     *
     * @param content
     * @return
     */
    @GetMapping(value = "/getClassQrZuHe/{content}/{x}/{y}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    BufferedImage getClassQrZuHe(@PathVariable("content") String content, @PathVariable("x") Integer x, @PathVariable("y") Integer y);

    /**
     * 下载文件
     *
     * @return
     */
    @PostMapping(value = "/downloadFileInputStream", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ResponseEntity<InputStreamResource> downloadFileInputStreamResource();

    /**
     * 下载文件
     *
     * @return
     */
    @PostMapping(value = "/downloadFileByteArray", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ResponseEntity<ByteArrayResource> downloadFileByteArrayResource();

    /**
     *
     * @param nameList
     * @return
     */
    @PostMapping(value = "/user/dictList")
    ApiResponse<Object> dictList(@RequestBody ApiRequest<List<String>> nameList);
}
