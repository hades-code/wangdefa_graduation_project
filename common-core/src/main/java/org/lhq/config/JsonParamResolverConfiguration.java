package org.lhq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
	/**
	 * 添加自定义参数解析器
	 * @param resolvers
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new JsonParamArgumentResolver());
		super.addArgumentResolvers(resolvers);
	}

	/**
	 * 吧Long序列化成String
	 * @param converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter jackson2CborHttpMessageConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE,ToStringSerializer.instance);
		objectMapper.registerModule(simpleModule);
		jackson2CborHttpMessageConverter.setObjectMapper(objectMapper);
		converters.add(jackson2CborHttpMessageConverter);
		converters.add(responseBodyConverter());
		super.configureMessageConverters(converters);
	}

	/**
	 * 改变响应编码
	 * @return
	 */
	@Bean
	public HttpMessageConverter responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
		return converter;
	}

/*	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringHttpMessageConverter =(StringHttpMessageConverter)converters.get(0);
		stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
	}*/
}
