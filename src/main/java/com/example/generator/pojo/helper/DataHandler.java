package com.example.generator.pojo.helper;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.generator.pojo.container.ApiPageResponse;
import com.example.generator.pojo.container.ApiPageResponseChild;
import com.example.generator.pojo.container.ApiResponse;
import com.example.generator.pojo.enums.BasicRespCode;
import com.example.generator.pojo.exception.BasicException;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 */
public class DataHandler implements Serializable {

    private final static long serialVersionUID = 1L;

    /**
     * 处理分页数据
     *
     * @param pageData ApiPageResponse<? extends R>
     * @param <T>
     * @return
     */
    @SuppressWarnings("all")
    public static <R, T> ApiPageResponse<?> pageListDataHandle(IPage<T> pageData, Class<R> clazz) {
        ApiPageResponse<R> response = new ApiPageResponse<>();
        ApiPageResponseChild<R> child = new ApiPageResponseChild<>();
        if (CollectionUtils.isEmpty(pageData.getRecords())) {
            child.setList(Collections.emptyList());
        } else {
            child.setList(JSON.parseArray(JSON.toJSONString(pageData.getRecords()), clazz));
        }
        // 当前页
        child.setCurrentPage(pageData.getCurrent());
        // 页面大小
        child.setPageSize(pageData.getSize());
        // 总页数
        child.setTotalPage(pageData.getPages());
        // 总条数
        child.setTotalCount(pageData.getTotal());
        response.setData(child);
        response.setCode(BasicRespCode.SUCCESS.getCode());
        return response;
    }

    /**
     * 处理返回的list数据
     *
     * @param listData list 数据
     * @param clazz    转换的类型
     * @param <R>      返回值
     * @param <T>      输入值
     * @return
     */
    public static <R, T> List<R> listDataHandle(List<T> listData, Class<R> clazz) {
        if (CollectionUtils.isEmpty(listData)) {
            return Collections.emptyList();
        }
        return JSON.parseArray(JSON.toJSONString(listData), clazz);
    }

    /**
     * 单个对象处理
     *
     * @param data  数据
     * @param clazz 转换的类型
     * @param <R>   返回值
     * @param <T>   输入值
     * @return
     */
    public static <R, T> R dataHandle(T data, Class<R> clazz) {
        if (Objects.isNull(data)) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(data), clazz);
    }

    /**
     * 处理返回结果集
     *
     * @param apiResponse 请求的参数
     * @param <T>         泛型
     * @return 返回的结果
     */
    public static <T> T returnDataHandle(ApiResponse<T> apiResponse) {
        if (!BasicRespCode.SUCCESS.getCode().equals(apiResponse.getCode())) {
            throw new BasicException(BasicRespCode.FAIL.getCode(), "远程调用失败:".concat(apiResponse.getMsg()));
        }
        return apiResponse.getData();
    }
}
