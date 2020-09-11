package org.lhq.service;

import org.lhq.gp.product.entity.User;
import org.lhq.mapper.UserMapper;
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
     * @CacheEvict 缓存清理
     * @param user
     */
    @CacheEvict(value = "userCache",key = "'userId:'+#user.id",beforeInvocation = true)
    public void deleteUser(User user){
        userMapper.delete(user);
    }
    public User login(String username,String password){
        return new User().setUsername("wdf").setPassword("123");
    }
}
