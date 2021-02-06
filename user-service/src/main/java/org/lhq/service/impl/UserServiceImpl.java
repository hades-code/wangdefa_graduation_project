package org.lhq.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.lhq.dao.UserDao;
import org.lhq.entity.User;
import org.lhq.exception.ProjectException;
import org.lhq.service.MailService;
import org.lhq.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.Duration;
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
    @Resource
    private MailService mailService;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;


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
    public User login(String username,String password) {
		User loginUser = this.userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername,username));
		log.info("查找到登录用户:{}",loginUser);
		return Optional.ofNullable(loginUser).orElse(new User().setPassword("").setUsername(""));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(User user,String verificationCode) throws ProjectException {
        Integer integer = this.userDao.selectCount(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()));
        if (integer > 0){
            throw new ProjectException("该用户名已被注册");
        }
        integer = this.userDao.selectCount(new LambdaQueryWrapper<User>().eq(User::getEmail, user.getEmail()));
        if (integer > 0){
            throw new ProjectException("该邮箱已经被注册");
        }
        String code = StrUtil.toString(redisTemplate.opsForValue().get(user.getEmail()));
        if (!StrUtil.equals(code,verificationCode)){
            throw new ProjectException("验证码错误");
        } else {
            user.setPassword(DigestUtil.md5Hex(user.getPassword()));
            this.userDao.insert(user);
        }
        return user;
    }

    @Override
    public void resetPassword(Long userId, String newPassword) {
        String resetLink = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(String.valueOf(userId),resetLink,Duration.ofMinutes(10));
    }

    @Override
    public void activateAccount(Long userId) {
    }

    @Override
    public void mailVerificationCode(String mail) {
        String verificationCode = RandomUtil.randomString(4);
        redisTemplate.opsForValue().set(mail,verificationCode, Duration.ofMinutes(10));
        mailService.mailVerificationCode(mail,"验证码",verificationCode);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, Long userId) throws ProjectException {
        User user = this.userDao.selectOne(new LambdaQueryWrapper<User>()
                .select(User::getId)
                .select(User::getPassword)
                .eq(User::getId,userId));
        if (StrUtil.equals(user.getPassword(),DigestUtil.md5Hex(oldPassword))){
            this.userDao.updateById(user.setPassword(DigestUtil.md5Hex(newPassword)));
        }else {
            throw new ProjectException("原密码错误");
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
