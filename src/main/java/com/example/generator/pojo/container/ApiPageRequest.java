package com.example.generator.pojo.container;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * ApiPageRequest描述
 *
 * @author Administrator-YHL
 * @date 2023-10-18
 **/
@Data
@Schema(title = "ApiPageRequest", description = "分页请求参数")
public class ApiPageRequest<T> extends ApiRequest<T> {
    private static final long serialVersionUID = 1L;
    /**
     * 页码
     */
    @Schema(description = "页码")
    private Long currentPage = 1L;
    /**
     * 页面记录数
     */
    @Schema(description = "页面记录数")
    private Long pageSize = 20L;
}
