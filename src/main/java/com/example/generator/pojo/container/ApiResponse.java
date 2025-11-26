package com.example.generator.pojo.container;

import cn.hutool.core.util.IdUtil;
import com.example.generator.pojo.enums.BasicRespCode;
import com.example.generator.pojo.exception.BasicException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xxxx
 */
@Data
@Schema(title = "ApiResponse", description = "页面返回参数")
public class ApiResponse<T> extends ApiResponseCommon {

    private static final long serialVersionUID = 1L;
    /**
     * 返回数据
     **/
    @Schema(description = "返回数据")
    private T data = null;

    public ApiResponse() {
        super();
        if (StringUtils.isBlank(super.getTrace())) {
            super.setTrace("TRACE_".concat(IdUtil.fastUUID()).concat("-").concat(String.valueOf(System.currentTimeMillis())));
        }
    }

    public ApiResponse(String trace, String code, String msg, T data) {
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
    public static <T> ApiResponse<T> failTrace(String trace, String msg) {
        return new ApiResponse<T>(trace, BasicRespCode.FAIL.getCode(), msg, null);
    }

    public static <T> ApiResponse<T> failTrace(String trace, String code, String msg) {
        return new ApiResponse<T>(trace, code, msg, null);
    }

    public static <T> ApiResponse<T> failTrace(String trace, BasicException e) {
        return new ApiResponse<T>(trace, e.getErrorCode(), e.getErrorMsg(), null);
    }

    /**
     * 无追踪号
     */
    public static <T> ApiResponse<T> fail(String msg) {
        return new ApiResponse<T>(null, BasicRespCode.FAIL.getCode(), msg, null);
    }

    public static <T> ApiResponse<T> fail(String code, String msg) {
        return new ApiResponse<T>(null, code, msg, null);
    }

    public static <T> ApiResponse<T> fail(BasicException e) {
        return new ApiResponse<T>(null, e.getErrorCode(), e.getErrorMsg(), null);
    }

    /**
     * 有追踪号
     */
    public static <T> ApiResponse<T> successTrace(String trace) {
        return new ApiResponse<T>(trace, BasicRespCode.SUCCESS.getCode(), BasicRespCode.SUCCESS.getDesc(), null);
    }

    public static <T> ApiResponse<T> successTrace(String trace, T data) {
        return new ApiResponse<T>(trace, BasicRespCode.SUCCESS.getCode(), BasicRespCode.SUCCESS.getDesc(), data);
    }

    /**
     * 无追踪号
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<T>(null, BasicRespCode.SUCCESS.getCode(), BasicRespCode.SUCCESS.getDesc(), null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(null, BasicRespCode.SUCCESS.getCode(), BasicRespCode.SUCCESS.getDesc(), data);
    }

    public static boolean isSuccess(ApiResponse<?> resp) {
        return resp != null && BasicRespCode.SUCCESS.getCode().equals(resp.getCode());
    }

    public static boolean isFail(ApiResponse<?> resp) {
        return !isSuccess(resp);
    }
}
