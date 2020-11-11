package org.gourd.hu.base.config;

import org.gourd.hu.base.handler.GlobalExceptionHandler;
import org.gourd.hu.base.holder.SpringContextHolder;
import org.gourd.hu.base.request.filter.RequestDetailFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: gourd.hu
 * @Date: 2018/9/19 15:40
 */
@Import({SpringContextHolder.class, GlobalExceptionHandler.class, RequestDetailFilter.class})
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/websocket.html").setViewName("websocket");
        registry.addViewController("/mail.html").setViewName("mail");
        registry.addViewController("/kaptcha.html").setViewName("kaptcha");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 配置自己的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    /**
     * 允许跨域请求
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig());
        return new CorsFilter(source);
    }

    /**
     * 跨域配置
     * @return
     */
    private CorsConfiguration corsConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 请求常用的三种配置，*代表允许所有，当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }

}