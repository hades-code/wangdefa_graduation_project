package org.lhq.filter;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.lhq.entity.dto.PayloadDto;
import org.lhq.serivce.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;


/**
 * @author Wallace
 */

@Component
public class AuthFilter implements GlobalFilter, Ordered {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);


  @Value("${jwt.secret.key:123456}")
  private String secretKey;

  @Value("${auth.skip.urls:auth/login}")
  private String[] skipAuthUrls;

  /*@Value("${jwt.blacklist.key.format}")
  private String jwtBlacklistKeyFormat;*/

  private AuthService authService;


  @Override
  public int getOrder() {
    return -100;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String url = exchange.getRequest().getURI().getPath();

    LOGGER.info("请求路URL为:{}", url);
    // 不需要验证的路径，跳过
    if (Arrays.asList(skipAuthUrls).contains(url)) {
      return chain.filter(exchange);
    }
    // 从请求头中取出token
    String token = exchange.getRequest().getHeaders().getFirst("Authorization");
    // 未携带token或token在黑名单内
    if (StrUtil.isBlank(token)) {
      ServerHttpResponse originalResponse = exchange.getResponse();
      originalResponse.setStatusCode(HttpStatus.OK);
      originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
      byte[] response =
          "{\"code\": \"401\",\"msg\": \"401 未认证.\"}".getBytes(StandardCharsets.UTF_8);
      DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
      return originalResponse.writeWith(Flux.just(buffer));
    }
    PayloadDto payloadDto = authService.verifyToken(token);
    if (DateUtil.current() - payloadDto.getExp()<= TimeUnit.MICROSECONDS.convert(10,TimeUnit.MINUTES)){
          payloadDto.setIat(DateUtil.current());
      String newToken = authService.refreshToken(payloadDto);
    }
    // 将现在的request，添加当前身份
    ServerHttpRequest mutableReq =
        exchange.getRequest().mutate().header("Authorization-UserName", "userName").build();
    ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
    return chain.filter(mutableExchange);
  }


}

