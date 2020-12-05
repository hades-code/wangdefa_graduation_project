package org.lhq.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.lhq.gp.product.entity.Directory;
import org.lhq.service.DirectorySerivce;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

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
    public void mkdir(String dirName,Long parentId,Long userId){
        if (StrUtil.isEmpty(dirName)){
            return;
        }
        Directory dir = directorySerivce.getDirById(parentId);
        if (dir == null){
            Directory directory = directorySerivce.getDirByPid(0L, userId);
            if (directory != null){
                parentId = directory.getId();
            }
        }
        Directory newDir = new Directory();
        newDir.setDirectoryName(dirName);
        newDir.setParentId(parentId);
        newDir.setUserId(userId);
        newDir.setCreateTime(new Date());
        newDir.setModifyTime(new Date());
        directorySerivce.saveDir(newDir);
    }
}
