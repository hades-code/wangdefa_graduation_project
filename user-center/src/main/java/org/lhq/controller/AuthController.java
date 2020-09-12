package org.lhq.controller;

import org.lhq.gp.product.entity.User;
import org.lhq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Resource
    UserService userService;

    @PostMapping("login")
    public User login(@RequestBody User user){
        LOGGER.info("登录行动:{}",user);
        User loginUser = userService.login(user.getUsername(), user.getUsername());
        if (loginUser.getUsername() == null||loginUser.getUsername().equals("") ){
            LOGGER.info("用户名或密码错误");
        }else {
            //TO-DO:
        }
        return null;
    }
}
