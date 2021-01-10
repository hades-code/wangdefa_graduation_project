import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lhq.UserServiceApplication;
import org.lhq.entity.User;
import org.lhq.exception.ProjectException;
import org.lhq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * @program: admin-service
 * @description: 用户服务测试
 * @author: Wang defa
 * @create: 2021-01-09 15:00
 */

@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Test
    public void addUser() throws ProjectException {
        User user = new User();
        for (int i = 0; i < 100; i++) {
            user.setId(null);
            user.setUsername("wdf_"+ RandomUtil.randomString(5));
            user.setEmail(RandomUtil.randomString(5)+"@wdf.com");
            user.setPassword("123456");
            userService.register(user);
        }
    }
}
