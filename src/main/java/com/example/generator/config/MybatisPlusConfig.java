package com.example.generator.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.example.generator.web.interceptor.EscapeLikeSqlInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * MybatisPlusConfig描述
 *
 * @author Administrator-YHL
 * @date 2023-10-20
 **/
@Slf4j
@Configuration
public class MybatisPlusConfig implements MetaObjectHandler {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.POSTGRE_SQL);
        //合理化 当查询不到数据的时候，自定回到第一页
        //关闭合理化
        paginationInnerInterceptor.setOverflow(false);
        // 分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        // 开启乐观锁
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 防止全表更新
        // interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        interceptor.addInnerInterceptor(new EscapeLikeSqlInterceptor());
        return interceptor;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
            this.setFieldValByName("createUserId", "-", metaObject);
        } catch (Exception e) {
            log.warn("CreateAndUpdateMetaObjectHandler.insertFill:{}", e.getMessage());
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
            this.setFieldValByName("updateUserId", "-", metaObject);
        } catch (Exception e) {
            log.warn("CreateAndUpdateMetaObjectHandler.updateFill:{}", e.getMessage());
        }
    }
}
