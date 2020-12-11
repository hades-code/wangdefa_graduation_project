package org.lhq.service;

import org.lhq.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hades
 */
@FeignClient(name = "user-service")
@RequestMapping("user-feign")
public interface UserService {
		@PostMapping("/updateStorage")
		void updateStorage(@RequestParam("userId") Long userId,
						   @RequestParam("size") Double size,
						   @RequestParam("type") String type);
		@PostMapping("/{id}")
		User getUserInfo(@PathVariable Long userId);
		@PostMapping("update")
		Integer updateUserInfoById(User user);
}
