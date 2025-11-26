package com.example.generator.web.handle;

import com.alibaba.fastjson2.JSON;
import com.example.generator.pojo.container.ApiResponse;
import com.example.generator.pojo.enums.BasicRespCode;
import com.example.generator.pojo.exception.BasicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Administrator
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> doException(Exception e) {
        log.error("错误信息:[{}],错误内容:[{}]", e.getMessage(), JSON.toJSONString(e.getStackTrace()));
        return ApiResponse.fail(BasicRespCode.FAIL.getCode());
    }

    @ExceptionHandler(BasicException.class)
    public ApiResponse<?> doBasicException(BasicException e) {
        log.error("错误信息:[{}],错误内容:[{}]", e.getMessage(), JSON.toJSONString(e.getStackTrace()));
        return ApiResponse.fail(e.getErrorCode(), e.getErrorMsg());
    }

}
