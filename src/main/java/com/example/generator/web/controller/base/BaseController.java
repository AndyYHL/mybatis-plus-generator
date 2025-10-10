package com.example.generator.web.controller.base;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.generator.pojo.annotation.BusinessId;
import com.example.generator.pojo.container.ApiPageRequest;
import com.example.generator.pojo.container.ApiPageResponse;
import com.example.generator.pojo.container.ApiRequest;
import com.example.generator.pojo.container.ApiResponse;
import com.example.generator.pojo.domain.base.BaseEntity;
import com.example.generator.pojo.enums.BasicRespCode;
import com.example.generator.pojo.exception.BasicException;
import com.example.generator.web.api.common.IBaseApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

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
        boolean result;
        Pair<String, Object> businessIdAndValue = this.getBusinessIdAndValue(doClass);
        if (Objects.nonNull(businessIdAndValue) && StringUtils.isNotBlank(businessIdAndValue.getValue().toString())) {
            // 进行更新
            UpdateWrapper<DO> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq(businessIdAndValue.getKey(), businessIdAndValue.getValue());
            result = this.getService().update(doClass, updateWrapper);
            if (!result) {
                return ApiResponse.fail("更新失败！");
            }
        } else {
            this.setBusinessId(doClass);
            result = this.getService().save(doClass);
            if (!result) {
                return ApiResponse.fail("保存失败！");
            }
        }
        VO dtoClass = JSON.parseObject(JSON.toJSONString(doClass), clazzVO);
        return ApiResponse.successTrace(req.getTrace(), dtoClass);
    }

    @Override
    public ApiResponse<List<VO>> selectList(ApiRequest<REQ> req) {
        REQ reqParam = req.getParam();
        QueryWrapper<DO> queryWrapper = this.getQueryWrapper(reqParam);
        queryWrapper.orderByDesc(this.getTableIdColumnName(clazzDO));
        List<DO> doClassList = this.getService().list(queryWrapper);
        return ApiResponse.successTrace(req.getTrace(), JSON.parseArray(JSON.toJSONString(doClassList), clazzVO));
    }

    @Override
    public ApiPageResponse<VO> selectPageList(ApiPageRequest<REQ> req) {
        REQ reqParam = req.getParam();
        QueryWrapper<DO> queryWrapper = this.getQueryWrapper(reqParam);
        queryWrapper.orderByDesc(this.getTableIdColumnName(clazzDO));
        IPage<DO> doClassList = this.getService().page(new Page<>(req.getCurrentPage(), req.getPageSize()), queryWrapper);
        return ApiPageResponse.successTrace(req.getTrace(), doClassList, clazzVO);
    }

    @Override
    public ApiResponse<VO> selectDataDetail(ApiRequest<REQ> req) {
        REQ reqParam = req.getParam();
        QueryWrapper<DO> queryWrapper = this.getQueryWrapper(reqParam);
        DO doClass = this.getService().getOne(queryWrapper);
        return ApiResponse.successTrace(req.getTrace(), JSON.parseObject(JSON.toJSONString(doClass), clazzVO));
    }

    @Override
    public ApiResponse<VO> selectDataDetail(String businessId) {
        QueryWrapper<DO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(this.getBusinessId(clazzDO), businessId);
        DO doClass = this.getService().getOne(queryWrapper);
        return ApiResponse.success(JSON.parseObject(JSON.toJSONString(doClass), clazzVO));
    }

    @Override
    public ApiResponse<Boolean> delete(ApiRequest<REQ> req) {
        REQ reqParam = req.getParam();
        QueryWrapper<DO> queryWrapper = this.getQueryWrapper(reqParam);
        return ApiResponse.successTrace(req.getTrace(), this.getService().remove(queryWrapper));
    }

    @Override
    public ApiResponse<Boolean> delete(String businessId) {
        QueryWrapper<DO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(this.getBusinessId(clazzDO), businessId);
        return ApiResponse.success(this.getService().remove(queryWrapper));
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
                Object obj = field.get(reqParam);
                if (Objects.isNull(obj)) {
                    continue;
                }
                String value = obj.toString();
                queryWrapper.eq(StringUtils.isNotBlank(value), StrUtil.toUnderlineCase(field.getName()), value);
            } catch (IllegalAccessException e) {
                throw new BasicException(BasicRespCode.FAIL.getCode(), "无法访问字段：" + field.getName() + "[" + e.getMessage() + "]");
            }
        }
        return queryWrapper;
    }

    /**
     * 获取类的主键
     *
     * @return
     */
    private String getTableId() {
        String columnName = "", fieldName = "";
        // 通过反射获取@TableId注解
        Field[] fields = BaseEntity.class.getDeclaredFields();
        for (Field field : fields) {
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null) {
                columnName = tableId.value(); // 获取到"id"
                fieldName = field.getName();   // 获取到"id"
                break;
            }
        }
        return columnName;
    }

    /**
     * 获取表的主键列名
     *
     * @param clazz DO实体类
     * @return 主键列名
     */
    private String getTableIdColumnName(Class<?> clazz) {
        // 遍历类及其父类
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                TableId tableId = field.getAnnotation(TableId.class);
                if (tableId != null) {
                    // return tableId.value();
                    return StrUtil.toUnderlineCase(field.getName());
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return "id";
    }

    private String getBusinessId(Class<?> clazz) {
        // 遍历类及其父类
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                BusinessId businessId = field.getAnnotation(BusinessId.class);
                if (businessId != null) {
                    if (StringUtils.isNotBlank(businessId.value())) {
                        return businessId.value();
                    }
                    return StrUtil.toUnderlineCase(field.getName());
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return "id";
    }

    private Pair<String, Object> getBusinessIdAndValue(DO clazz) {
        // 遍历类及其父类
        Field[] fields = clazz.getClass().getDeclaredFields();
        String columnName;
        Object columnValue;
        for (Field field : fields) {
            BusinessId businessId = field.getAnnotation(BusinessId.class);
            if (businessId != null) {
                columnName = businessId.value();
                try {
                    // 设置为可访问
                    field.setAccessible(true);
                    columnValue = field.get(clazz);
                    return Pair.of(StringUtils.isNotBlank(columnName) ? columnName : StrUtil.toUnderlineCase(field.getName()), columnValue);
                } catch (IllegalAccessException e) {
                    throw new BasicException(BasicRespCode.FAIL.getCode(), "无法访问字段：" + field.getName() + "[" + e.getMessage() + "]");
                }
            }
        }
        return null;
    }

    private void setBusinessId(DO clazz) {
        // 遍历类及其父类
        Field[] fields = clazz.getClass().getDeclaredFields();
        for (Field field : fields) {
            BusinessId businessId = field.getAnnotation(BusinessId.class);
            if (businessId != null) {
                try {
                    // 设置为可访问
                    field.setAccessible(true);
                    if (field.getType().isAssignableFrom(String.class)) {
                        field.set(clazz, IdUtil.getSnowflakeNextIdStr());
                    } else if (field.getType().isAssignableFrom(Long.class)) {
                        field.set(clazz, IdUtil.getSnowflakeNextId());
                    } else {
                        field.set(clazz, IdUtil.getSnowflakeNextIdStr());
                    }
                    break;
                } catch (IllegalAccessException e) {
                    throw new BasicException(BasicRespCode.FAIL.getCode(), "无法访问字段：" + field.getName() + "[" + e.getMessage() + "]");
                }
            }
        }
    }
}