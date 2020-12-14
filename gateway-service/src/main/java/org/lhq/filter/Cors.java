package org.lhq.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@Slf4j
public class Cors {
	private static final String MAX_AGE = "18000L";

	@Bean
	public WebFilter corsFilter() {
		return (ServerWebExchange ctx, WebFilterChain chain) -> {
			ServerHttpRequest request = ctx.getRequest();
			if (CorsUtils.isCorsRequest(request)) {
				HttpHeaders requestHeaders = request.getHeaders();
				ServerHttpResponse response = ctx.getResponse();
				HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
				HttpHeaders headers = response.getHeaders();
				List<String> stringList = requestHeaders.getAccessControlRequestHeaders();
				for (String accessControlRequestHeaders : stringList) {
					headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,accessControlRequestHeaders);
			}
				headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
				headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
				headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"GET,POST,OPTIONS,PUT,DELETE");
				headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
				headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
				if (request.getMethod() == HttpMethod.OPTIONS) {
					response.setStatusCode(HttpStatus.OK);
					return Mono.empty();
				}
			}

			return chain.filter(ctx);
		};
	}

	@Bean
	public ServerCodecConfigurer serverCodecConfigurer() {
		return new DefaultServerCodecConfigurer();
	}

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter() {
			@Override
			public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
				return chain.filter(exchange);
			}
		};
	}
}
