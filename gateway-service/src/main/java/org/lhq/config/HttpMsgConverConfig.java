package org.lhq.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: wangdefa_graduation_project
 * @description: 消息转换器设置
 * @author: Wang defa
 * @create: 2020-09-13 15:58
 */

@Configuration
public class HttpMsgConverConfig {
    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));

    }
}