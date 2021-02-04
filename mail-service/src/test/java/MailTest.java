import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lhq.MailApplication;
import org.lhq.service.MailService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @program: admin-service
 * @description: 邮件功能测试
 * @author: Wang defa
 * @create: 2021-02-04 16:13
 */

@SpringBootTest(classes = MailApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class MailTest {
    @Resource
    private MailService mailService;
    @Test
    public void sendEmail(){
        mailService.sendWithHtml("lhq_hcl@outlook.com","欢迎来到LSP之家","欢迎来到LSP之家",null);
    }
}
