package org.lhq.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author hades
 */
@FeignClient(name = "userService",url = "www.baidu.com")
public interface UserService {

}
