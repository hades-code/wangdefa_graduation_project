package org.lhq.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.lhq.entity.User;
import org.lhq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: admin-service
 * @description: 管理员管理用户
 * @author: Wang defa
 * @create: 2021-01-05 11:57
 */

@RestController
@Api(tags = "管理员管理用户")
@RequestMapping("admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;

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
    @PostMapping("query")
    public Page<User> queryList(User user, Long pageNum, Long size){
        return this.userService.getUserPage(user,size,pageNum);
    }
}
