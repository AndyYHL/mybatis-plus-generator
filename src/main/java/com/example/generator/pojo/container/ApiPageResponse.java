package com.example.generator.pojo.container;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.generator.pojo.enums.BasicRespCode;
import com.example.generator.pojo.exception.BasicException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * ApiPageResponse描述
 *
 * @author Administrator-YHL
 * @date 2023-10-18
 **/
@Data
@Schema(title = "ApiPageResponse", description = "分页返回参数")
public class ApiPageResponse<T> extends ApiResponseCommon {
    /**
     * 返回数据
     **/
    @Schema(description = "返回数据")
    private ApiPageResponseChild<T> data = null;

    public ApiPageResponse() {
        super();
        if (StringUtils.isBlank(super.getTrace())) {
            super.setTrace("TRACE_".concat(IdUtil.fastUUID()).concat("-").concat(String.valueOf(System.currentTimeMillis())));
        }
    }

    public ApiPageResponse(String trace, String code, String msg, ApiPageResponseChild<T> data) {
        super();
        super.setCode(code);
        super.setMsg(msg);
        if (StringUtils.isBlank(trace)) {
            super.setTrace("TRACE_".concat(IdUtil.fastUUID()).concat("-").concat(String.valueOf(System.currentTimeMillis())));
        } else {
            super.setTrace(trace);
        }
        this.data = data;
    }

    /**
     * 有追踪号
     */
    public static <T> ApiPageResponse<T> failTrace(String trace, String msg) {
        return new ApiPageResponse<T>(trace, BasicRespCode.FAIL.getCode(), msg, null);
    }

    public static <T> ApiPageResponse<T> failTrace(String trace, String code, String msg) {
        return new ApiPageResponse<T>(trace, code, msg, null);
    }

    public static <T> ApiPageResponse<T> failTrace(String trace, BasicException e) {
        return new ApiPageResponse<T>(trace, e.getErrorCode(), e.getErrorMsg(), null);
    }

    /**
     * 无追踪号
     */
    public static <T> ApiPageResponse<T> fail(String msg) {
        return new ApiPageResponse<T>(null, BasicRespCode.FAIL.getCode(), msg, null);
    }

    public static <T> ApiPageResponse<T> fail(String code, String msg) {
        return new ApiPageResponse<T>(null, code, msg, null);
    }

    public static <T> ApiPageResponse<T> fail(BasicException e) {
        return new ApiPageResponse<T>(null, e.getErrorCode(), e.getErrorMsg(), null);
    }

    /**
     * 有追踪号
     */
    public static <T> ApiPageResponse<T> successTrace(String trace) {
        return new ApiPageResponse<T>(trace, BasicRespCode.SUCCESS.getCode(), BasicRespCode.SUCCESS.getDesc(), null);
    }

    public static <T> ApiPageResponse<T> successTrace(String trace, List<T> data) {
        return new ApiPageResponse<T>(trace, BasicRespCode.SUCCESS.getCode(), BasicRespCode.SUCCESS.getDesc(), new ApiPageResponseChild<>(data));
    }

    public static <T> ApiPageResponse<T> successTrace(String trace, IPage<?> page, Class<T> clazz) {
        return new ApiPageResponse<T>(trace, BasicRespCode.SUCCESS.getCode(), BasicRespCode.SUCCESS.getDesc(), new ApiPageResponseChild<>(page, clazz));
    }

    /**
     * 无追踪号
     */
    public static <T> ApiPageResponse<T> success() {
        return new ApiPageResponse<T>(null, BasicRespCode.SUCCESS.getCode(), BasicRespCode.SUCCESS.getDesc(), null);
    }

    public static <T> ApiPageResponse<T> success(List<T> data) {
        return new ApiPageResponse<T>(null, BasicRespCode.SUCCESS.getCode(), BasicRespCode.SUCCESS.getDesc(), new ApiPageResponseChild<>(data));
    }

    public static <T> ApiPageResponse<T> success(IPage<?> page, Class<T> clazz) {
        return new ApiPageResponse<T>(null, BasicRespCode.SUCCESS.getCode(), BasicRespCode.SUCCESS.getDesc(), new ApiPageResponseChild<>(page, clazz));
    }

    public static boolean isSuccess(ApiResponse<?> resp) {
        return resp != null && BasicRespCode.SUCCESS.getCode().equals(resp.getCode());
    }

    public static boolean isFail(ApiResponse<?> resp) {
        return !isSuccess(resp);
    }
}
