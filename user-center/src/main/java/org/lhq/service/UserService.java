package org.lhq.service;

import org.lhq.gp.product.entity.User;
import org.lhq.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    public User login(String username,String password){
        return new User().setUsername("wdf").setPassword("123");
    }
}
