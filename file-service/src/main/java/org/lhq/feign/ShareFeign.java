package org.lhq.feign;

import lombok.extern.slf4j.Slf4j;
import org.lhq.entity.vo.ShareVO;
import org.lhq.service.IShareService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: admin-service
 * @description: 分享feign调用
 * @author: Wang defa
 * @create: 2021-01-07 10:08
 */

@RestController
@Slf4j
@RequestMapping("share-feign")
public class ShareFeign {
    @Resource
    private IShareService shareService;
    @GetMapping("{id}")
    public ShareVO getShare(@PathVariable("id") Long id){
        return shareService.getShareVO(id);
    }
}
