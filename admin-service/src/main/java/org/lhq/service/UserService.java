package org.lhq.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.lhq.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Wallace
 */
@FeignClient(name = "user-service")
@RequestMapping("user-feign")
public interface UserService {
    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    @PostMapping("getUser")
    User getUserById(@RequestParam("userId") Long userId);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PostMapping("update")
    Boolean updateUserInfoById(@RequestBody User user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("save")
    Boolean saveUser(@RequestBody User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    @PostMapping("delete")
    Boolean deleteUserById(@RequestParam("id") Long id);

    /**
     * 分页查找
     * @param user
     * @param size
     * @param pageNum
     * @return
     */
    @PostMapping("page")
    Page<User> getUserPage(@RequestBody User user,
                           @RequestParam Long size,
                           @RequestParam Long pageNum);
}
