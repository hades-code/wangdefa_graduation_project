package org.lhq.controller;

import cn.hutool.core.util.StrUtil;
import org.lhq.service.DirectorySerivce;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
*@program: wangdefa_graduation_project
*@description: 目录Controller
*@author: Wang defa
*@create: 2020-12-04 09:51
*/

@RestController
@RequestMapping("directory")
public class DirectoryController {
    @Resource
    DirectorySerivce directorySerivce;

    @PostMapping("mkdir")
    public void mkdir(String dirName,Long parentId,Long id){
        if (StrUtil.isEmpty(dirName)){
            return;
        }

    }
}
