package org.lhq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author hades
 */
@Configuration
public class JsonParamResolverConfiguration extends WebMvcConfigurationSupport {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new JsonParamArgumentResolver());
		super.addArgumentResolvers(resolvers);
	}
/*	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(responseBodyConverter());
		super.configureMessageConverters(converters);
	}
	@Bean
	public HttpMessageConverter responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
		return converter;
	}*/

	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringHttpMessageConverter =(StringHttpMessageConverter)converters.get(1);
		stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
	}
}
