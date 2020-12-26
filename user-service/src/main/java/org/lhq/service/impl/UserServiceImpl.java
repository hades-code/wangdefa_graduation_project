package org.lhq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.lhq.dao.UserDao;
import org.lhq.entity.User;
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
@Service
@Slf4j
@CacheConfig(cacheNames = "userCache")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserDao userDao;


    @Override
    @Cacheable(key = "#root.methodName + #root.args[0]",condition = "#id != null",unless="#result == null")
    public User getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    @CachePut(key = "#result",condition = "#result != null")
    public boolean saveOrUpdate(User entity) {
        return super.saveOrUpdate(entity);
    }

    @Override
    @CacheEvict(key = "#id")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
	@Cacheable(key = "#root.methodName + #root.args[0]",condition = "#user != null",unless="#result == null")
    public User login(String username,String password) {
		User loginUser = this.userDao.selectOne(new QueryWrapper<User>().lambda()
				.eq(User::getUsername,username));
		return Optional.ofNullable(loginUser).orElse(new User().setPassword("").setUsername(""));
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

	@Override
	public void updateStorage(Long userId, Double size, String type) {
		User user = userDao.selectById(userId);
		if (user == null) {
			return;
		}
		if (user.getUsedStorageSize() - size < 0){
			user.setUsedStorageSize(0.0);
		}else {
			user.setUsedStorageSize(user.getUsedStorageSize() + size);
		}
		userDao.updateById(user);
	}
}
