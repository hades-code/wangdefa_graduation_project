package org.lhq.service;

import org.lhq.gp.product.entity.User;
import org.lhq.mapper.UserMapper;
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
    public User updateUser(User user){
        User user1 = userMapper.saveAndFlush(user);
        return user;
    }
    @Cacheable(value = "user",key = "#id")
    public User selectOne(Long id){
        Optional<User> byId = userMapper.findById(id);
        User user = byId.orElse(new User());
        return user;
    }
    public void deleteUser(User user){
        userMapper.delete(user);
    }
    public User login(String username,String password){
        return new User().setUsername("wdf").setPassword("123");
    }
}
