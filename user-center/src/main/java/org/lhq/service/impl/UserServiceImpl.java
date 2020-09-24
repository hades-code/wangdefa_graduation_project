package org.lhq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lhq.dao.UserDao;

import org.lhq.gp.product.entity.User;
import org.lhq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author wangdefa
 * @since 2020-09-15 20:28:42
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User login(User user) {
        return null;
    }

    @Override
    public User register(User user) {
        return null;
    }
}
