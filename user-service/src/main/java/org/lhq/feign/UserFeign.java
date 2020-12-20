package org.lhq.feign;

import lombok.extern.slf4j.Slf4j;
import org.lhq.entity.User;
import org.lhq.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: wangdefa_graduation_project
 * @description: feign对外提供暴露出服务
 * @author: Wang defa
 * @create: 2020-12-11 16:22
 */

@RestController
@RequestMapping("user-feign")
@Slf4j
public class UserFeign {
    @Resource
    UserService userService;

    @PostMapping("updateStorage")
    public void updateStorage(@RequestParam("userId") Long userId,
                              @RequestParam("size") Double size,
                              @RequestParam("type") String type){
        this.userService.updateStorage(userId,size,type);
    }
    @PostMapping("getUser")
    public User getUserById(@RequestParam("userId") Long userId){
		User byId = this.userService.getById(userId);
		return byId;
	}
	@PostMapping("update")
	public Boolean updateUser(@RequestBody User user){
		return this.userService.updateById(user);
	}
}
