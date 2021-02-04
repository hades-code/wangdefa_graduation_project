package org.lhq.serivce;

import org.lhq.entity.dto.PayloadDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "user-service")
@RequestMapping("user-feign")
public interface AuthService {
    @PostMapping("verifyToken")
    PayloadDto verifyToken(String token);
    @PostMapping("refresh")
    String refreshToken(PayloadDto payloadDto);
}
