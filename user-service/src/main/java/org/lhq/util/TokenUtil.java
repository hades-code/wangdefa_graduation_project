package org.lhq.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.proc.BadJWTException;
import org.lhq.entity.dto.PayloadDto;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

@Component
public class TokenUtil {
    /**
     * token 前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    public static PayloadDto getPayLoadDto(Long userId,String username, ArrayList<String> role){
        Date now = new Date();
        DateTime exp = DateUtil.offsetMinute(now, 60);
        return PayloadDto.builder()
                .sub("auth")
                .iat(now.getTime())
                .exp(exp.getTime())
                .jti("lhq")
                .userId(userId)
                .username(username)
                .authorities(role)
                .build();
    }
    public static String generateTokenByHMAC(String payloadStr, String secret) throws JOSEException {
        //创建JWS头，设置签名算法和类型
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).
                type(JOSEObjectType.JWT)
                .build();
        //将负载信息封装到Payload中
        Payload payload = new Payload(payloadStr);
        //创建JWS对象
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        //创建HMAC签名器
        JWSSigner jwsSigner = new MACSigner(secret);
        //签名
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    public static PayloadDto verifyTokenByHMAC(String token, String secret) throws BadJWTException, JOSEException, ParseException {
        //从token中解析JWS对象
        JWSObject jwsObject = JWSObject.parse(token);
        //创建HMAC验证器
        JWSVerifier jwsVerifier = new MACVerifier(secret);
        if (!jwsObject.verify(jwsVerifier)) {
            throw new BadJWTException("token签名不合法！");
        }
        String payload = jwsObject.getPayload().toString();
        PayloadDto payloadDto = JSONUtil.toBean(payload, PayloadDto.class);
        if (payloadDto.getExp() < new Date().getTime()) {
            throw new BadJWTException("token已过期！");
        }
        return payloadDto;
    }


}
