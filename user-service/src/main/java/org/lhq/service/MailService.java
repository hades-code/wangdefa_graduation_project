package org.lhq.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "user-service")
@RequestMapping("mail-feign")
public interface MailService {
}
