package org.lhq.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Wallace
 */
@FeignClient(name = "user-service")
@RequestMapping("mail-feign")
public interface MailService {
    @PostMapping("resetPassword")
    Boolean resetPassword(String targetMail,String title,String resetLink);
    @PostMapping("activation")
    Boolean activation(String targetMail,String title,String activationCode);
    @PostMapping("verificationCode")
    Boolean mailVerificationCode(String targetMail,String title,String verificationCode);
}
