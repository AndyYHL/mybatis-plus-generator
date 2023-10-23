package com.example.generator.web.api.common;

import com.example.generator.pojo.constant.apicode.CommonApiCode;
import com.example.generator.pojo.constant.base.ApiConstant;
import com.example.generator.pojo.container.ApiPageRequest;
import com.example.generator.pojo.container.ApiPageResponse;
import com.example.generator.pojo.container.ApiRequest;
import com.example.generator.pojo.container.ApiResponse;
import com.example.generator.pojo.criteria.UserCriteria;
import com.example.generator.pojo.param.UserParam;
import com.example.generator.pojo.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * ICommonApi描述
 *
 * @author Administrator-YHL
 * @date 2023-10-18
 **/
/*@FeignClient(name = ServiceName.SHARE_SERVICE,
        contextId = ServiceName.SHARE_SERVICE + ServiceName.DELIMITER + "ICommonApi")*/
@Tag(name = "操作接口", description = "操作描述")
public interface ICommonApi {
    /**
     * 新增或者更新
     *
     * @param req 新增或者更新
     * @return 新增或者更新
     */
    @Operation(summary = "新增或者更新", description = "新增或者更新")
    @PostMapping(value = {ApiConstant.API_PREFIX + CommonApiCode.BASE_URL + CommonApiCode.SAVE_OR_UPDATE})
    ApiResponse<Boolean> saveOrUpdate(@RequestBody @Valid ApiRequest<UserParam> req);

    /**
     * 查询列表
     *
     * @param req 查询列表
     * @return 查询列表
     */
    @Operation(summary = "查询列表", description = "查询列表")
    @PostMapping(value = {ApiConstant.API_OPERATION_PREFIX + CommonApiCode.BASE_URL + CommonApiCode.SELECT_LIST})
    ApiResponse<List<UserVO>> selectList(@RequestBody @Valid ApiRequest<UserCriteria> req);

    /**
     * 查询分页列表
     *
     * @param req 查询分页列表
     * @return 查询分页列表
     */
    @Operation(summary = "查询分页列表", description = "查询分页列表")
    @PostMapping(value = {ApiConstant.API_OPERATION_PREFIX + CommonApiCode.BASE_URL + CommonApiCode.SELECT_PAGE_LIST})
    ApiPageResponse<UserVO> selectPageList(@RequestBody @Valid ApiPageRequest<UserCriteria> req);

    /**
     * 查询数据详情
     *
     * @param req 查询数据详情
     * @return 查询数据详情
     */
    @Operation(summary = "查询数据详情", description = "查询数据详情")
    @PostMapping(value = {ApiConstant.API_PLATFORM_PREFIX + CommonApiCode.BASE_URL + CommonApiCode.SELECT_DATA_DETAIL})
    ApiResponse<UserVO> selectDataDetail(@RequestBody @Valid ApiRequest<UserCriteria> req);

    /**
     * 修改状态
     *
     * @param req 修改状态
     * @return 是否成功
     */
    @Operation(summary = "修改状态（单个）", description = "修改状态（单个）")
    @PostMapping(value = {ApiConstant.API_PLATFORM_PREFIX + CommonApiCode.BASE_URL + CommonApiCode.UPDATE_STATUS})
    ApiResponse<Boolean> updateStatus(@RequestBody @Valid ApiRequest<UserParam> req);
}
