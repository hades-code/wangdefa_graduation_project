package org.lhq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author hades
 */
@Configuration
public class CustomMvcConfiguration extends WebMvcConfigurationSupport {
    public static final String DATE_TIME_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    /**
     * 添加自定义参数解析器
     * 
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new JsonParamArgumentResolver());
        super.addArgumentResolvers(resolvers);
    }

    /**
     * 吧Long序列化成String
     * 
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(getMappingJackson2HttpMessageConverter());
        converters.add(responseBodyConverter());
        super.configureMessageConverters(converters);
    }


    /**
     * 改变响应编码
     * 
     * @return
     */
    @Bean
    public HttpMessageConverter responseBodyConverter() {
		return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

	@Bean
	public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		//设置日期格式
		ObjectMapper objectMapper = new ObjectMapper();
		JavaTimeModule javaTimeModule = new JavaTimeModule();
        SimpleModule simpleModule = new SimpleModule();
		javaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STRING)));
		javaTimeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_STRING)));
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		objectMapper.registerModule(javaTimeModule);
		objectMapper.registerModule(simpleModule);
		mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

		return mappingJackson2HttpMessageConverter;
	}

}
