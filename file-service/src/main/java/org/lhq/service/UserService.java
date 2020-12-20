package org.lhq.service;

import org.lhq.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author hades
 */
@FeignClient(name = "user-service")
@RequestMapping("user-feign")
public interface UserService {
		/**
		 * 更改用户已用存储空间
		 */
		@PostMapping("/updateStorage")
		void updateStorage(@RequestParam("userId") Long userId,
						   @RequestParam("size") Double size,
						   @RequestParam("type") String type);

		/**
		 * 根据用户id查找用户
		 * @param userId
		 * @return
		 */
		@PostMapping("getUser")
		User getUserById(@RequestParam("userId") Long userId);

		/**
		 *
		 * @param user
		 * @return
		 */
		@PostMapping("update")
		Boolean updateUserInfoById(@RequestBody User user);
}
