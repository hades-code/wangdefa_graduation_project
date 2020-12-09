package org.lhq.util;



import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * jwt工具,使用对称加密算法
 * @author Wallace
 */


@Component
public class JwtUtil{
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * token 头
     */
    public static final String AUTH_HEADER_KEY = "Authorization";
    /**
     * token 前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * token 签名id
     */
    private static String clientId = "org.lhq";
    /**
     * 签名密钥
     */
    private static final String BASE64_SECRET = "org.lhq";
    /**
     * 签名人
     */
    private static final String NAME = "wang_de_fa";
    /**
     * token过期时间
     */
    private static final long EXPIRES_SECOND = TimeUnit.MILLISECONDS.convert(8L,TimeUnit.HOURS);




    /**
     * @param jsonWebToken 接收到的token
     * @return
     */
    public static Claims parseJwt(String jsonWebToken) {

        if (jsonWebToken.startsWith(TOKEN_PREFIX)){
            String token = jsonWebToken.substring(7);
            try {
                return Jwts.parser()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(BASE64_SECRET))
                        .parseClaimsJws(token).getBody();
            } catch (ExpiredJwtException eje) {
                log.error("===== Token过期 =====", eje);
                throw eje;

            } catch (Exception e) {
                log.error("===== token解析异常 =====", e);
                throw e;

            }
        }else {
            throw new UnsupportedJwtException("不支持的token");
        }

    }


    /**
     * @param userId
     * @param username
     * @param role
     * @return
     */
    public static String createJwt(Long userId, String username, String role) {
        try {
            //使用的加密算法,对称加密,加密解密一套密钥
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);

            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(BASE64_SECRET);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

            //添加构成JWT参数
            JwtBuilder builder = Jwts.builder().setHeaderParam("type", "JWT")
                    //设置放入token的用户信息
                    .claim("role", role)
                    .claim("userId", userId)
                    .setSubject(username)
                    //签名主体,即代表签名拥有者
                    .setIssuer(clientId)
                    //代表签名的签发客户端
                    .setIssuedAt(new Date())
                    //签发日期
                    .setAudience(NAME)
                    //代表这个JWT的接收对象
                    .signWith(signatureAlgorithm, signingKey);
            long TTLMillis = EXPIRES_SECOND;
            if (TTLMillis >= 0) {
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp)
                        //JWT过期时间
                        .setNotBefore(now);
                //JWT生效时间
            }
            return builder.compact();

        } catch (Exception e) {
            log.error("签名失败", e);
        }
        return null;
    }
}
