package org.lhq.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.lhq.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @program: wangdefa_graduation_project
 * @description: 邮件发送实现类
 * @author: Wang defa
 * @create: 2020-12-11 17:58
 */

@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private JavaMailSender javaMailSender;
    /**
     * 发送简单文本的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @return
     */
    @Override
    public boolean send(String to, String subject, String content) {
        log.info("## 准备发送邮件.............");
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //邮件发送来源
        simpleMailMessage.setFrom(mailProperties.getUsername());
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);

        try {
            // 发送
            javaMailSender.send(simpleMailMessage);
            log.info("## Send the mail success ...");
        } catch (Exception e) {
            log.error("Send mail error: ", e);
            return false;
        }
        return true;

    }

    /**
     * 发送 html 的邮件
     *
     * @param to
     * @param subject
     * @param html
     * @return
     */
    @Override
    public boolean sendWithHtml(String to, String subject, String html) {
        return false;
    }

    /**
     * 发送带有图片的 html 的邮件
     *
     * @param to
     * @param subject
     * @param html
     * @param cids
     * @param filePaths
     * @return
     */
    @Override
    public boolean sendWithImageHtml(String to, String subject, String html, String[] cids, String[] filePaths) {
        return false;
    }

    /**
     * 发送带有附件的邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param filePaths
     * @return
     */
    @Override
    public boolean sendWithWithEnclosure(String to, String subject, String content, String[] filePaths) {
        return false;
    }
}
