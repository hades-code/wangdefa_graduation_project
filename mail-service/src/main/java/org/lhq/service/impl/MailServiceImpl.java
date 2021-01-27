package org.lhq.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.lhq.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

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
     * @param to
     * @param subject
     * @param content
     * @return
     */
    @Override
    public boolean send(String to, String subject, String content) {
        log.info("## Ready to send mail ...");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 邮件发送来源
        simpleMailMessage.setFrom(mailProperties.getUsername());
        // 邮件发送目标
        simpleMailMessage.setTo(to);
        // 设置标题
        simpleMailMessage.setSubject(subject);
        // 设置内容
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
     * @param to
     * @param subject
     * @param html
     * @return
     */
    @Override
    public boolean sendWithHtml(String to, String subject, String html) {
        log.info("## Ready to send mail ...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            // 邮件发送来源
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            // 邮件发送目标
            mimeMessageHelper.setTo(to);
            // 设置标题
            mimeMessageHelper.setSubject(subject);
            // 设置内容，并设置内容 html 格式为 true
            mimeMessageHelper.setText(html, true);

            javaMailSender.send(mimeMessage);
            log.info("## Send the mail with html success ...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Send html mail error: ", e);
            return false;
        }

        return true;
    }

    /**
     * 发送带有图片的 html 的邮件
     * @param to
     * @param subject
     * @param html
     * @param cids
     * @param filePaths
     * @return
     */
    @Override
    public boolean sendWithImageHtml(String to, String subject, String html, String[] cids, String[] filePaths) {
        log.info("## Ready to send mail ...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            // 邮件发送来源
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            // 邮件发送目标
            mimeMessageHelper.setTo(to);
            // 设置标题
            mimeMessageHelper.setSubject(subject);
            // 设置内容，并设置内容 html 格式为 true
            mimeMessageHelper.setText(html, true);

            // 设置 html 中内联的图片
            for (int i = 0; i < cids.length; i++) {
                FileSystemResource file = new FileSystemResource(filePaths[i]);
                // addInline() 方法 cid 需要 html 中的 cid (Content ID) 对应，才能设置图片成功，
                // 具体可以参见，下面 4.3.3 单元测试的参数设置
                mimeMessageHelper.addInline(cids[i], file);
            }

            javaMailSender.send(mimeMessage);
            log.info("## Send the mail with image success ...");
        } catch (Exception e) {
            log.error("Send html mail error: ", e);
            return false;
        }

        return true;
    }

    /**
     * 发送带有附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePaths
     * @return
     */
    @Override
    public boolean sendWithWithEnclosure(String to, String subject, String content, String[] filePaths) {
        log.info("## Ready to send mail ...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            // 邮件发送来源
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            // 邮件发送目标
            mimeMessageHelper.setTo(to);
            // 设置标题
            mimeMessageHelper.setSubject(subject);
            // 设置内容
            mimeMessageHelper.setText(content);

            // 添加附件
            for (int i = 0; i < filePaths.length; i++) {
                FileSystemResource file = new FileSystemResource(filePaths[i]);
                String attachementFileName = "附件" + (i + 1);
                mimeMessageHelper.addAttachment(attachementFileName, file);
            }

            javaMailSender.send(mimeMessage);
            log.info("## Send the mail with enclosure success ...");
        } catch (Exception e) {
            log.error("Send html mail error: ", e);
            return false;
        }
        return true;
    }


}
