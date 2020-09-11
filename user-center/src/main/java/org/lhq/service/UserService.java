package org.lhq.service;

import org.lhq.gp.product.entity.User;
import org.springframework.stereotype.Service;

/**
 * @program: wangdefa_graduation_project
 * @description: 用户服务
 * @author: Wang defa
 * @create: 2020-08-24 23:28
 */

@Service
public class UserService {
    public User login(String username,String password){
        return new User().setUsername("wdf").setPassword("123");
    }
}
