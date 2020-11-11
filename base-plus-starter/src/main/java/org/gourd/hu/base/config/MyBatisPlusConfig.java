package org.gourd.hu.base.config;

import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @author gourd.hu
 */
public class MyBatisPlusConfig {

    /**
     * mybatis-plus乐观锁插件
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


    /**
     * Sql 注入器
     * @return
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new DefaultSqlInjector();
    }

}