package org.lhq.controller;

import org.lhq.gp.product.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: wangdefa_graduation_project
 * @description: UserController
 * @author: Wang defa
 * @create: 2020-09-08 17:55
 */

@RestController
@RequestMapping("user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @PostMapping("login")
    public void login(User user){
        LOGGER.info("登录行动{}",user);
    }
}
