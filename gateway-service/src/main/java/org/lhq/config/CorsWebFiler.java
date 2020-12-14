package org.lhq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsWebFiler {
	@Bean
	public CorsWebFilter getCorsFilter(){
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true); // 允许cookies跨域
		config.setMaxAge(1800L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
		config.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
		config.addAllowedHeader("*");// #允许访问的头信息,*表示全部
		config.addAllowedMethod("OPTIONS");// 允许提交请求的方法类型，*表示全部允许
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);//拦截所有请求
		return new CorsWebFilter(source);
	}

}
