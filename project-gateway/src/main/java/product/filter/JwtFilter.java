package product.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


/**
 * @program: wangdefa_graduation_project
 * @description: Jwt
 * @author: Wang defa
 * @create: 2020-08-27 16:01
 */

@Component
public class JwtFilter implements GlobalFilter, Ordered {
    private static Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        logger.info("请求路径为:{}",path);
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(authorization)){
            return authError(exchange.getResponse(),"请登录");
        }else {
           // Claims claims = JwtUtil.parseJwt(authorization);
        }
        //return authError(exchange.getResponse(),"请登录");
        return chain.filter(exchange);
    }

    public Mono<Void> authError(ServerHttpResponse response,String mess){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String returnStr = null;
        try {
            returnStr = objectMapper.writeValueAsString("returnData");
        } catch (JsonProcessingException e) {
            logger.error("Json转换错误: {}",e);
        }
        DataBuffer buffer = response.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));

    }

    @Override
    public int getOrder() {
        return -99;
    }
}
