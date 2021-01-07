package org.lhq.controller;

import org.lhq.entity.vo.ShareVO;
import org.lhq.service.ShareService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: admin-service
 * @description: 管理分享文件
 * @author: Wang defa
 * @create: 2021-01-07 16:46
 */

@RestController
@RequestMapping("admin/share")
public class AdminShareController {
    @Resource
    ShareService shareService;
    @GetMapping("{id}")
    public ShareVO getShare(@PathVariable("id") Long id){
        return shareService.getShare(id);
    }
}
