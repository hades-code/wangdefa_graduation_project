package org.lhq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lhq.gp.product.entity.User;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2020-09-15 20:28:37
 */
public interface UserService extends IService<User> {
    User login(User user);
    User register(User user);
}
