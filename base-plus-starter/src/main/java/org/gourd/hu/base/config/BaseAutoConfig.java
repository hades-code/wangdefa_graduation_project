package org.gourd.hu.base.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author gourd.hu
 * @date 2018-11-20
 */
@Configuration
@EnableAutoConfiguration
@Import({AsyncPoolConfig.class, MyBatisPlusConfig.class, Swagger2Config.class, WebMvcConfig.class})
public class BaseAutoConfig {

    /**
     * 不依赖cloud模块需要注册此bean
     * @return
     */
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }



}