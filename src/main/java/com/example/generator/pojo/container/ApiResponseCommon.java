package com.example.generator.pojo.container;

import com.example.generator.pojo.enums.BasicRespCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * ApiResponseCommon描述
 *
 * @author Administrator-YHL
 * @date 2023-10-18
 **/
@Data
@Schema(title = "ApiResponseCommon", description = "页面返回公共参数")
public class ApiResponseCommon {
    /**
     * 返回码
     **/
    @Schema(description = "返回码")
    private String code = BasicRespCode.SUCCESS.getCode();
    /**
     * 返回信息
     **/
    @Schema(description = "返回信息")
    private String msg = null;
    /**
     * 追踪号
     **/
    @Schema(description = "追踪号")
    private String trace;
}
