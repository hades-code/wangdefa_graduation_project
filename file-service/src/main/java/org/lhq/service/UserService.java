package org.lhq.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author hades
 */
@FeignClient(name = "userService",url = "userService")
public interface UserService {
		void updateStorage(Long userId,Double size,String type);
}
