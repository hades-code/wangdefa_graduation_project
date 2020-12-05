package org.lhq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lhq.dao.UserDao;
import org.lhq.gp.product.entity.User;
import org.lhq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Optional;

/**
 * (User)表服务实现类
 *
 * @author wangdefa
 * @since 2020-09-15 20:28:42
 */
@Service("userService")
@CacheConfig(cacheNames = "userCache")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserDao userDao;


    @Override
   // @Cacheable(key = "#id",condition = "id != null")
    public User getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    @CachePut(key = "#result.id")
    public boolean saveOrUpdate(User entity) {
        return super.saveOrUpdate(entity);
    }

    @Override
    @CacheEvict(key = "#id")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public User login(User user) {
        User loginUser = userDao.selectByUsername(user.getUsername());
        User optionalUser = Optional.ofNullable(loginUser).orElse(new User().setPassword("").setUsername(""));
        boolean equals = optionalUser.getPassword().equals(user.getPassword());
        LOGGER.info("登录:{}",equals);
        return optionalUser;
    }


    @Override
    public User register(User user) {
        User registerUser = userDao.selectByUsername(user.getUsername());
        if (registerUser != null){
            LOGGER.info("用户名重复");
            return null;
        }else {
            userDao.insert(user);
            LOGGER.info("注册成功");
            return user;
        }
    }
}
