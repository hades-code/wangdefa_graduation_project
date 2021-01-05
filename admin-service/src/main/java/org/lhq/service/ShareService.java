package org.lhq.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 分享服务
 * @author Wallace
 */
@FeignClient(name = "file-service")
@RequestMapping("userFile-feign")
public interface ShareService {
}
