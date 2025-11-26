package com.example.generator.web.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * EscapeLikeSqlInterceptor描述:自定义mybatis-plus拦截器, 用于转义模糊查询参数中的特殊字符
 * <p>
 * 包名称：com.example.generator.web.interceptor
 * 类名称：EscapeLikeSqlInterceptor
 * 全路径：com.example.generator.web.interceptor.EscapeLikeSqlInterceptor
 * 类描述：自定义mybatis-plus拦截器, 用于转义模糊查询参数中的特殊字符
 *
 * @author Administrator-YHL
 * @date 2024年04月09日 16:55
 */
@Slf4j
@SuppressWarnings("all")
public class EscapeLikeSqlInterceptor implements InnerInterceptor {
    /**
     * 点
     */
    public static final String DOT = ".";

    /**
     * 按?分割字符串表达式
     */
    public static final String PLACEHOLDER_REGEX = "\\?";

    /**
     * 按.分割字符串表达式
     */
    public static final String DOT_REGEX = "\\.";

    /**
     * like操作通配符
     */
    public static final char LIKE_WILDCARD_CHARACTER = '%';

    /**
     * like操作通常会存在的占位符形式
     */
    public static final String PLACEHOLDER = " ?";

    /**
     * 条件构造器生成sql特有的参数名前缀
     */
    public static final String WRAPPER_PARAMETER_PROPERTY = "ew.paramNameValuePairs.";

    /**
     * like语句在sql中的字符串
     */
    private final String LIKE_SQL = " like ";

    private static final String SQL_SPECIAL_CHARACTER = "_%*@|&()[]\"'\\";

    /**
     * 不应用此拦截器的参数名,方法参数中有此名称的参数则不应用此拦截器
     */
    private final String IGNORE = "EscapeLikeSqlIgnore";

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        if (parameter instanceof Map) {
            Map<?, ?> parameterMap = (Map<?, ?>) parameter;
            if (parameterMap.containsKey(IGNORE)) {
                return;
            }
        }

        if (!needEscape(boundSql.getSql())) {
            return;
        }
        escapeSql(boundSql);
    }

    @Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        if (parameter instanceof Map) {
            Map<?, ?> parameterMap = (Map<?, ?>) parameter;
            if (parameterMap.containsKey(IGNORE)) {
                return;
            }
        }

        BoundSql boundSql = ms.getBoundSql(parameter);
        if (!needEscape(boundSql.getSql())) {
            return;
        }
        escapeSql(boundSql);
    }

    /**
     * sql是否需要转义
     *
     * @param sql
     * @return
     */
    private boolean needEscape(String sql) {
        return containLike(sql) && containPlaceholder(sql);
    }

    /**
     * sql是否包含like语句
     *
     * @param sql
     * @return
     */
    private boolean containLike(String sql) {
        return StrUtil.containsIgnoreCase(sql, LIKE_SQL);
    }

    /**
     * sql是否包含占位符
     *
     * @param sql
     * @return
     */
    private boolean containPlaceholder(String sql) {
        return StrUtil.contains(sql, PLACEHOLDER);
    }

    /**
     * 参数名是否是条件构造器生成
     *
     * @param property
     * @return
     */
    private boolean containWrapper(String property) {
        return StrUtil.contains(property, WRAPPER_PARAMETER_PROPERTY);
    }

    /**
     * 参数名是否是对象的嵌套表达式
     *
     * @param property
     * @return
     */
    private boolean cascadeParameter(String property) {
        return StrUtil.contains(property, DOT);
    }

    /**
     * 转义sql语句中的特殊字符
     *
     * @param boundSql
     */
    @SuppressWarnings("unchecked")
    private void escapeSql(BoundSql boundSql) {
        String[] split = boundSql.getSql().split(PLACEHOLDER_REGEX);
        Object parameter = boundSql.getParameterObject();
        Set<String> processedProperty = new HashSet<>();
        for (int i = 0; i < split.length; i++) {
            // like 通常在末尾
            if (StrUtil.lastIndexOfIgnoreCase(split[i], LIKE_SQL) > -1) {
                if (parameter instanceof Map) {
                    // 拿到此位置的"?"对应的参数名
                    String property = boundSql.getParameterMappings().get(i).getProperty();
                    // 防止重复转义
                    if (processedProperty.contains(property)) {
                        continue;
                    }
                    Map<Object, Object> parameterMap = (Map<Object, Object>) parameter;
                    if (containWrapper(property)) {
                        // 条件构造器构造sql方式
                        handlerWrapperEscape(property, parameterMap);
                    } else {
                        // 自主写sql方式
                        handlerOriginalSqlEscape(property, parameterMap);
                    }
                    processedProperty.add(property);
                } else if (parameter instanceof String) {
                    // 单条件&&不通过条件构造器&&直接写sql&&mapper不写@Param注解,会导致parameter直接为参数值而不是map
//                    BeanUtil.setFieldValue(boundSql, "parameterObject", SqlUtil.addSalashes((String) parameter));
                    // 强行反射设置属性,暂不清楚为什么更改parameterObject无效
                    BeanUtil.setFieldValue(boundSql.getParameterObject(), "value", addSalashes(((String) parameter)).toCharArray());
                }
            }
        }
    }

    /**
     * 处理通过条件构造器构造sql的转义
     *
     * @param property        参数名
     * @param parameterObject 此条sql的参数map
     */
    private void handlerWrapperEscape(String property, Map<?, ?> parameterObject) {
        String[] keys = property.split(DOT_REGEX);
        Object ew = parameterObject.get(keys[0]);
        if (ew instanceof AbstractWrapper) {
            Map<String, Object> paramNameValuePairs = ((AbstractWrapper<?, ?, ?>) ew).getParamNameValuePairs();
            // 拿到参数值
            Object paramValue = paramNameValuePairs.get(keys[2]);
            if (paramValue instanceof String) {
                // 去除首尾%并转义后再拼上%
                paramNameValuePairs.put(keys[2], String.format("%%%s%%", addSalashes((String) paramValue, LIKE_WILDCARD_CHARACTER)));
            }
        }
    }

    /**
     * 处理自己写sql的转义
     *
     * @param property        参数名
     * @param parameterObject 此条sql的参数map
     */
    private void handlerOriginalSqlEscape(String property, Map<Object, Object> parameterObject) {
        if (cascadeParameter(property)) {
            // 级联形式的参数,比如参数是对象中的某个字段的值:filter.name
            String[] keys = property.split(DOT_REGEX, 2);
            Object parameterBean = parameterObject.get(keys[0]);
            Object parameterValue = BeanUtil.getProperty(parameterBean, keys[1]);
            if (parameterValue instanceof String) {
                BeanUtil.setProperty(parameterBean, keys[1], addSalashes((CharSequence) parameterValue));
            }
        } else {
            // 普通参数名
            parameterObject.computeIfPresent(property, (key, value) -> {
                if (value instanceof String) {
                    return addSalashes((CharSequence) value);
                }
                return value;
            });
        }
    }

    private static String addSalashes(CharSequence content, Filter<Character> filter) {
        if (StrUtil.isEmpty(content)) {
            return StrUtil.str(content);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (filter.accept(c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static String addSalashes(CharSequence content, CharSequence applyCharacters) {
        if (StrUtil.isEmpty(content)) {
            return StrUtil.str(content);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (StrUtil.contains(applyCharacters, c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static String addSalashes(CharSequence content) {
        if (StrUtil.isEmpty(content)) {
            return StrUtil.str(content);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (StrUtil.contains(SQL_SPECIAL_CHARACTER, c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static String addSalashes(CharSequence content, char trimFix) {
        if (content.charAt(0) == trimFix) {
            content = content.subSequence(1, content.length());
        }
        if (content.charAt(content.length() - 1) == trimFix) {
            content = content.subSequence(0, content.length() - 1);
        }
        return addSalashes(content);
    }
}
