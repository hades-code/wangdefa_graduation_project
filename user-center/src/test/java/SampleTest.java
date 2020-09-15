import org.junit.Test;
import org.lhq.dao.UserDao;

import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @program: wangdefa_graduation_project
 * @description: 简单测试
 * @author: Wang defa
 * @create: 2020-09-11 16:36
 */

@SpringBootTest
public class SampleTest {
    @Resource
    private UserDao userDao;
    @Test
    public void test(){
        userDao.selectById(1);
    }
}
