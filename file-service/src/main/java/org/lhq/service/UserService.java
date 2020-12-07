package org.lhq.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hades
 */
@FeignClient(name = "userService",url = "userService")
public interface UserService {
		@PostMapping("/updateStorage")
		void updateStorage(@RequestParam("userid") Long userId,
						   @RequestParam("size") Double size,
						   @RequestParam("type") String type);
}
