package org.lhq.controller;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lhq.gp.product.common.CustomizeResponseEntity;
import org.lhq.gp.product.common.ResultCode;
import org.lhq.gp.product.entity.Directory;
import org.lhq.service.DirectorySerivce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("dir")
@Slf4j
public class DirectoryController {
    @Resource
    DirectorySerivce directorySerivce;

    @PostMapping("mkdir")
    public ResponseEntity<Object> mkdir(String dirName, Long parentId, Long userId){
        if (StrUtil.isEmpty(dirName)){
            return new ResponseEntity<Object>("文件夹名称为空",HttpStatus.FORBIDDEN);
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
        return new ResponseEntity<Object>("创建成功",HttpStatus.OK);
    }
    @PostMapping("rename")
    public ResponseEntity<Object> updateDirName(String dirName,Long id){
        if (StrUtil.isEmpty(dirName) || id <= 0){
            return new ResponseEntity<Object>("目录重命名失败",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Directory directory = directorySerivce.getDirById(id);
        if (directory == null){
            return new ResponseEntity<>("文件夹不存在",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        directory.setDirectoryName(dirName);
        directory.setModifyTime(new Date());
        directorySerivce.updateById(directory);
        return new ResponseEntity<Object>("目录重命名成功",HttpStatus.OK);
    }
}
