package org.lhq.controller;

import org.lhq.entity.User;
import org.lhq.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: admin-service
 * @description: 管理员管理用户
 * @author: Wang defa
 * @create: 2021-01-05 11:57
 */

@RestController
@RequestMapping("admin/user")
public class AdminUserController {
    @Resource
    UserService userService;
    @GetMapping("{id}")
    public User getUser(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }
    @PostMapping("update")
    public void updateUser(User user){
        userService.updateUserInfoById(user);
    }
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUserById(id);
    }
    @PostMapping("save")
    public Boolean saveUser(User user){
        return userService.saveUser(user);
    }
}
