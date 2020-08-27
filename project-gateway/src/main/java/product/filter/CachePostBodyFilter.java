package product.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @program: wangdefa_graduation_project
 * @description: 获取请求体过滤器
 * @author: Wang defa
 * @create: 2020-08-27 11:41
 */

@Component
public class CachePostBodyFilter implements GlobalFilter, Ordered {
    private static Logger logger = LoggerFactory.getLogger(CachePostBodyFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String methodValue = request.getMethodValue();
        logger.info("请求方法为:{}",methodValue);
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
