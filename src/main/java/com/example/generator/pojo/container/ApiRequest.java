package com.example.generator.pojo.container;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xxxx
 */
@Data
@Schema(title = "ApiRequest", description = "页面请求参数")
public class ApiRequest<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "追踪号")
    private String trace;

    @Valid
    @Schema(description = "请求参数不能为空！")
    @NotNull(message = "请求参数不能为空！")
    private T param;

    public ApiRequest() {
        super();
    }

    public ApiRequest(T param) {
        this.param = param;
    }

    public static <T> ApiRequest<T> create(T param) {
        return new ApiRequest<>(param);
    }

}
