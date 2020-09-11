import org.junit.Test;
import org.junit.runner.RunWith;
import org.lhq.gp.product.entity.User;
import org.lhq.mapper.UserMapper;
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
    private UserMapper userMapper;
    @Test
    public void test(){
        userMapper.save(new User().setUsername("dasdddsd"));
    }
}
