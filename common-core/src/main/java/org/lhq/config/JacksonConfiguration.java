package org.lhq.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hades
 * 解决js前后端Long类型位数不一致的问题，在序列化的时候吧Long 序列化成String
 */
@Configuration
public class JacksonConfiguration {
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
		return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
				.serializerByType(Long.class, ToStringSerializer.instance)
				.serializerByType(Long.TYPE,ToStringSerializer.instance);
	}
}
