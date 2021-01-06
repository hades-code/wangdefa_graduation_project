package org.lhq.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
	@PostMapping("page")
	public IPage<User>  pageUser(@RequestBody User user,
                                 @RequestParam(required = false) Long pageNum,
                                 @RequestParam(required = false) Long size){
        pageNum = pageNum ==null?1:pageNum;
        size = size == null?5:size;
        IPage<User> userPage = new Page<User>().setSize(size).setCurrent(pageNum);
        return this.userService.page(userPage, new QueryWrapper<>(user));
    }
}
