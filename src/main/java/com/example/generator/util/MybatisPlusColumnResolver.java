package com.example.generator.util;

import com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.springframework.stereotype.Component;

/**
 * <p>
 * MybatisPlusColumnResolver描述:Mybatis-Plus结合lambda表达式获取entity的数据库字段名
 * <p>
 * 包名称：com.example.generator.util
 * 类名称：MybatisPlusColumnResolver
 * 全路径：com.example.generator.util.MybatisPlusColumnResolver
 * 类描述：Mybatis-Plus结合lambda表达式获取entity的数据库字段名
 *
 * @author Administrator-YHL
 * @date 2023年11月24日 10:21
 */
@Component
public class MybatisPlusColumnResolver {
    public <T> ColumnResolver<T> create() {
        return new ColumnResolver<>();
    }

    /**
     * @author
     * @date
     */
    public static class ColumnResolver<T> extends AbstractLambdaWrapper<T, ColumnResolver<T>> {

        @Override
        protected ColumnResolver<T> instance() {
            return null;
        }

        @Override
        public String columnToString(SFunction<T, ?> column) {
            return super.columnToString(column);
        }

        @Override
        public String columnToString(SFunction<T, ?> column, boolean onlyColumn) {
            return super.columnToString(column, onlyColumn);
        }
    }
}
