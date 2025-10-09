package com.example.generator.pojo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * BusinessId描述:业务主键
 * <p>
 * 包名称：com.example.generator.pojo.annotation
 * 类名称：BusinessId
 * 全路径：com.example.generator.pojo.annotation.BusinessId
 * 类描述：业务主键
 *
 * @author Administrator-YHL
 * @date 2025年10月09日 09:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface BusinessId {
    /**
     * 业务主键 默认为空
     *
     * @return
     */
    String value() default "";
}