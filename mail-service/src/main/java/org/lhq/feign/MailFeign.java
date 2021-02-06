package org.lhq.feign;

import lombok.extern.slf4j.Slf4j;
import org.lhq.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: admin-service
 * @description: 邮箱feign
 * @author: Wang defa
 * @create: 2021-01-27 15:42
 */

@RestController
@Slf4j
@RequestMapping("mail-feign")
public class MailFeign {
    @Resource
    private MailService mailService;
    @PostMapping("resetPassword")
    public Boolean resetPassword(String targetMail,String title,String content){
        return mailService.sendWithHtml(targetMail, title, content, null);
    }
    @PostMapping("activation")
    public Boolean activation(String targetMail,String title,String content){
        return mailService.sendWithHtml(targetMail,title,content,null);
    }
    @PostMapping("verificationCode")
    public Boolean mailVerificationCode(String targetMail,String title,String content){
        return mailService.sendWithHtml(targetMail,title,content,null);
    }
}
