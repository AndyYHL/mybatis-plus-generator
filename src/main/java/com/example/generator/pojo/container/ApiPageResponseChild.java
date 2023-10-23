package com.example.generator.pojo.container;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * PageResponse描述
 *
 * @author Administrator-YHL
 * @date 2023-10-20
 **/
@Data
@Schema(title = "ApiPageResponseChild", description = "分页数据")
public class ApiPageResponseChild<T> {
    /**
     * 页码
     */
    @Schema(description = "页码")
    private Long currentPage;
    /**
     * 页面记录数
     */
    @Schema(description = "页面记录数")
    private Long pageSize;
    /**
     * 总页数
     */
    @Schema(description = "总页数")
    private Long totalPage;
    /**
     * 总记录数
     */
    @Schema(description = "总记录数")
    private Long totalCount;
    /**
     * 分页数据列表
     */
    @Schema(description = "分页数据列表")
    private List<T> list;

    public ApiPageResponseChild() {

    }

    /**
     * 处理返回list
     *
     * @param list
     */
    public ApiPageResponseChild(List<T> list) {
        this.currentPage = 1L;
        this.pageSize = (long) list.size();
        this.totalPage = 1L;
        this.totalCount = (long) list.size();
        this.list = list;
    }

    /**
     * 处理分页返回list
     *
     * @param page  分页信息
     * @param clazz 返回类型
     */
    public ApiPageResponseChild(IPage<?> page, Class<T> clazz) {
        this.currentPage = page.getCurrent();
        this.pageSize = page.getSize();
        this.totalPage = page.getPages();
        this.totalCount = page.getTotal();
        if (CollectionUtils.isEmpty(page.getRecords())) {
            this.list = Collections.emptyList();
        } else {
            this.list = JSON.parseArray(JSON.toJSONString(page.getRecords()), clazz);
        }
    }
}
