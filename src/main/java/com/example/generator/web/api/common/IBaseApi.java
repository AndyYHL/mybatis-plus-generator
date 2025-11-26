package com.example.generator.web.api.common;

import com.example.generator.pojo.constant.apicode.CommonApiCode;
import com.example.generator.pojo.container.ApiPageRequest;
import com.example.generator.pojo.container.ApiPageResponse;
import com.example.generator.pojo.container.ApiRequest;
import com.example.generator.pojo.container.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * IBaseApi描述:公共接口
 * <p>
 * 包名称：com.example.generator.web.api.common
 * 类名称：IBaseApi
 * 全路径：com.example.generator.web.api.common.IBaseApi
 * 类描述：公共接口
 *
 * @author Administrator-YHL
 * @date 2025年09月30日 14:50
 */
public interface IBaseApi<VO, REQ> {
    /**
     * 新增或者更新
     *
     * @param req 新增或者更新
     * @return 新增或者更新
     */
    @Operation(summary = "新增或者更新", description = "新增或者更新")
    @PostMapping(value = {CommonApiCode.SAVE_OR_UPDATE})
    ApiResponse<VO> saveOrUpdate(@RequestBody @Valid ApiRequest<REQ> req);

    /**
     * 查询列表
     *
     * @param req 查询列表
     * @return 查询列表
     */
    @Operation(summary = "查询列表", description = "查询列表")
    @PostMapping(value = {CommonApiCode.SELECT_LIST})
    ApiResponse<List<VO>> selectList(@RequestBody @Valid ApiRequest<REQ> req);

    /**
     * 查询分页列表
     *
     * @param req 查询分页列表
     * @return 查询分页列表
     */
    @Operation(summary = "查询分页列表", description = "查询分页列表")
    @PostMapping(value = {CommonApiCode.SELECT_PAGE_LIST})
    ApiPageResponse<VO> selectPageList(@RequestBody @Valid ApiPageRequest<REQ> req);

    /**
     * 查询数据详情
     *
     * @param req 查询数据详情
     * @return 查询数据详情
     */
    @Operation(summary = "查询数据详情", description = "查询数据详情")
    @PostMapping(value = {CommonApiCode.SELECT_DATA_DETAIL})
    ApiResponse<VO> selectDataDetail(@RequestBody @Valid ApiRequest<REQ> req);

    /**
     * 查询数据详情
     *
     * @param businessId 查询数据详情
     * @return 查询数据详情
     */
    @Operation(summary = "根据业务主键查询数据详情", description = "根据业务主键查询数据详情")
    @GetMapping(value = {CommonApiCode.SELECT_DATA_DETAIL + "/{businessId}"})
    ApiResponse<VO> selectDataDetail(@PathVariable("businessId") String businessId);

    /**
     * 删除数据
     *
     * @param req 删除数据
     * @return 删除数据
     */
    @Operation(summary = "删除数据", description = "删除数据")
    @DeleteMapping(value = {CommonApiCode.DELETE})
    ApiResponse<Boolean> delete(@RequestBody @Valid ApiRequest<REQ> req);

    /**
     * 根据业务主键删除
     *
     * @param businessId 删除数据
     * @return 删除数据
     */
    @Operation(summary = "根据业务主键删除", description = "根据业务主键删除")
    @DeleteMapping(value = {CommonApiCode.DELETE + "/{businessId}"})
    ApiResponse<Boolean> delete(@PathVariable("businessId") String businessId);
}