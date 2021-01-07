package org.lhq.service;

import org.lhq.entity.vo.ShareVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 分享服务
 * @author Wallace
 */
@FeignClient(name = "file-service")
@RequestMapping("share-feign")
public interface ShareService {
    @GetMapping("{id}")
    ShareVO getShare(@PathVariable("id") Long id);
}
