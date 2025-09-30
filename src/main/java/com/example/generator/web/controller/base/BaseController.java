package com.example.generator.web.controller.base;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.generator.pojo.container.ApiPageRequest;
import com.example.generator.pojo.container.ApiPageResponse;
import com.example.generator.pojo.container.ApiRequest;
import com.example.generator.pojo.container.ApiResponse;
import com.example.generator.pojo.domain.base.BaseEntity;
import com.example.generator.pojo.enums.BasicRespCode;
import com.example.generator.pojo.exception.BasicException;
import com.example.generator.web.api.common.IBaseApi;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * <p>
 * BaseController描述:通用Base
 * <p>
 * 包名称：com.example.generator.web.controller.base
 * 类名称：BaseController
 * 全路径：com.example.generator.web.controller.base.BaseController
 * 类描述：通用Base
 *
 * @author Administrator-YHL
 * @date 2025年09月30日 14:59
 */
@SuppressWarnings("all")
public abstract class BaseController<VO, REQ, DO extends BaseEntity> implements IBaseApi<VO, REQ> {
    public abstract IService<DO> getService();

    // 泛型Class
    private final Class<VO> clazzVO;
    private final Class<REQ> clazzREQ;
    private final Class<DO> clazzDO;

    protected BaseController() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] genericTypes = parameterizedType.getActualTypeArguments();
        this.clazzVO = (Class) genericTypes[0];
        this.clazzREQ = (Class) genericTypes[1];
        this.clazzDO = (Class) genericTypes[2];
    }

    @Override
    public ApiResponse<VO> saveOrUpdate(ApiRequest<REQ> req) {
        DO doClass = JSON.parseObject(JSON.toJSONString(req.getParam()), clazzDO);
        boolean result = this.getService().saveOrUpdate(doClass);
        if (!result) {
            return ApiResponse.fail("保存失败！");
        }
        VO dtoClass = JSON.parseObject(JSON.toJSONString(doClass), clazzVO);
        return ApiResponse.success(dtoClass);
    }

    @Override
    public ApiResponse<List<VO>> selectList(ApiRequest<REQ> req) {
        REQ reqParam = req.getParam();
        QueryWrapper<DO> queryWrapper = this.getQueryWrapper(reqParam);
        queryWrapper.lambda().orderByDesc(BaseEntity::getId);
        List<DO> doClassList = this.getService().list(queryWrapper);
        return ApiResponse.success(JSON.parseArray(JSON.toJSONString(doClassList), clazzVO));
    }

    @Override
    public ApiPageResponse<VO> selectPageList(ApiPageRequest<REQ> req) {
        REQ reqParam = req.getParam();
        QueryWrapper<DO> queryWrapper = this.getQueryWrapper(reqParam);
        queryWrapper.lambda().orderByDesc(BaseEntity::getId);
        IPage<DO> doClassList = this.getService().page(new Page<>(req.getCurrentPage(), req.getPageSize()), queryWrapper);
        return ApiPageResponse.success(JSON.parseArray(JSON.toJSONString(doClassList), clazzVO));
    }

    @Override
    public ApiResponse<VO> selectDataDetail(ApiRequest<REQ> req) {
        REQ reqParam = req.getParam();
        QueryWrapper<DO> queryWrapper = this.getQueryWrapper(reqParam);
        DO doClass = this.getService().getOne(queryWrapper);
        return ApiResponse.success(JSON.parseObject(JSON.toJSONString(doClass), clazzVO));
    }

    /**
     * 组合查询条件
     *
     * @param reqParam
     * @return
     */
    protected QueryWrapper<DO> getQueryWrapper(REQ reqParam) {
        QueryWrapper<DO> queryWrapper = new QueryWrapper<>();
        // 获取所有字段
        Field[] fields = reqParam.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                // 设置为可访问
                field.setAccessible(true);
                String value = field.get(reqParam).toString();
                queryWrapper.eq(StringUtils.isNotBlank(value), field.getName(), value);
            } catch (IllegalAccessException e) {
                throw new BasicException(BasicRespCode.FAIL.getCode(), "无法访问字段：" + field.getName() + "[" + e.getMessage() + "]");
            }
        }
        return queryWrapper;
    }
}