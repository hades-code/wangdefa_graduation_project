package org.lhq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.lhq.dao.UserDao;
import org.lhq.entity.User;
import org.lhq.exception.ProjectException;
import org.lhq.service.UserService;
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
    public User register(User user) throws ProjectException {
        Integer integer = this.userDao.selectCount(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()));
        if (integer > 0){
            throw new ProjectException("该用户名已被注册");
        }
        integer = this.userDao.selectCount(new LambdaQueryWrapper<User>().eq(User::getEmail, user.getEmail()));
        if (integer > 0){
            throw new ProjectException("该邮箱已经被注册");
        }else {
            this.userDao.insert(user);
        }
        return user;
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
