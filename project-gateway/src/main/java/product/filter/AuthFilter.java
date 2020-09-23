package product.filter;


import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.lhq.gp.product.entity.User;
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

import io.jsonwebtoken.Claims;
import product.util.JwtUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Wallace
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);


  @Value("${jwt.secret.key}")
  private String secretKey;

  @Value("${auth.skip.urls}")
  private String[] skipAuthUrls;

  @Value("${jwt.blacklist.key.format}")
  private String jwtBlacklistKeyFormat;


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
    boolean flag = token == null || token.isEmpty() || isBlackToken(token);
    if (flag) {
      ServerHttpResponse originalResponse = exchange.getResponse();
      originalResponse.setStatusCode(HttpStatus.OK);
      originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
      byte[] response =
          "{\"code\": \"401\",\"msg\": \"401 未认证.\"}".getBytes(StandardCharsets.UTF_8);
      DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
      return originalResponse.writeWith(Flux.just(buffer));
    }
    // 取出token包含的身份
    User user = verifyJWT(token);
    if (user.getId() == null) {
      ServerHttpResponse originalResponse = exchange.getResponse();
      originalResponse.setStatusCode(HttpStatus.OK);
      originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
      byte[] response =
          "{\"code\": \"10002\",\"msg\": \"invalid token.\"}".getBytes(StandardCharsets.UTF_8);
      DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
      return originalResponse.writeWith(Flux.just(buffer));
    }
    // 将现在的request，添加当前身份
    ServerHttpRequest mutableReq =
        exchange.getRequest().mutate().header("Authorization-UserName", "userName").build();
    ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
    return chain.filter(mutableExchange);
  }

  /**
   * JWT验证
   *
   * @param token
   * @return userName
   */
  private User verifyJWT(String token) {

    Claims claims = JwtUtil.parseJwt(token);
    Long userId = claims.get("userId",Long.class);
    String role = claims.get("role",String.class);
    String username = claims.getSubject();
    User user = new User().setId(userId).setUsername(username);
    return user;
  }

  /**
   * 判断token是否在黑名单内
   *
   * @param token
   * @return
   */
  private boolean isBlackToken(String token) {
    assert token != null;
    return true;
  }
}
