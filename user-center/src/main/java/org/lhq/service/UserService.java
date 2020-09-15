package org.lhq.service;

import org.lhq.gp.product.entity.User;
import org.lhq.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;


/**
 * @program: wangdefa_graduation_project
 * @description: 用户服务
 * @author: Wang defa
 * @create: 2020-08-24 23:28
 */

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    public User addUser(User user){
        User integer = userMapper.save(user);
        return integer;
    }

    /**
     * @CachePut 调用方法，也更新缓存、
     *
     * @param user
     * @return
     */
    @CachePut(value = "userCache",key = "'userId:'+#user.id")
    public User updateUser(User user){
        User user1 = userMapper.saveAndFlush(user);
        return user;
    }

    /**
     * @Cacheable
     *   value/cacheName:指定缓存组件的名字
     *   key:缓存数据使用的key，默认使用方法参数的值
     *   keyGenerator 和key 二选一使用
     *
     * @param id
     * @return
     */
    @Cacheable(value = "userCache",key = "'userId:'+#id")
    public User selectOne(Long id){
        Optional<User> byId = userMapper.findById(id);
        User user = byId.orElse(new User());
        return user;
    }

    /**
     * 通过用户名来查找用户的方法
     * @param user
     * @return
     */
    @Cacheable(value = "userCache",key = "'userId:'+#user.id")
    public User selectByUsername(User user){
        return new User();
    }


    /**
     * @CacheEvict 缓存清理
     * @param user
     */
    @CacheEvict(value = "userCache",key = "'userId:'+#user.id",beforeInvocation = true)
    public void deleteUser(User user){
        userMapper.delete(user);
    }
    public User login(String username,String password){
        Optional<User> byId = userMapper.findById(1L);
        return byId.orElse(new User());
    }

    /**
     * 注册的方法
     * @param user
     * @return
     */
    public User register(User user){
        LOGGER.info("请求注册的User:{}",user);
        User findUser = selectByUsername(user);
        if (findUser.getUsername() == null){
            User add = addUser(user);
            LOGGER.info("用户:{} 注册成功",user.getUsername());
            return add;
        }else {
            LOGGER.info("用户注册失败!");
            return new User();
        }
    }
}
