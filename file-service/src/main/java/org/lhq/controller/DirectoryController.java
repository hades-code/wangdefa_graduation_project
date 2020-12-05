package org.lhq.controller;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lhq.gp.product.common.CustomizeResponseEntity;
import org.lhq.gp.product.common.ResultCode;
import org.lhq.gp.product.entity.Directory;
import org.lhq.service.DirectorySerivce;
import org.lhq.service.UserFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    @Resource
    UserFileService userFileService;

    @PostMapping("mkdir")
    public ResponseEntity<Object> mkdir(String dirName, Long parentId, Long userId){
        if (StrUtil.isEmpty(dirName)){
            return new ResponseEntity<Object>("文件夹名称为空",HttpStatus.FORBIDDEN);
        }
        Directory dir = directorySerivce.getDirById(parentId);
		Directory directory = directorySerivce.getDirByPid(0L, userId);
		if (dir == null && directory != null){
			parentId = directory.getId();
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
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getDir(Long pid,@PathVariable Long userId){
    	//如果传上来的pid为空泽获取根目录
    	if (pid <= 0){
			Directory dir = directorySerivce.getDirByPid(0L, userId);
			if (dir!=null){
				pid = dir.getId();
			}
		}
		Directory directory = directorySerivce.getDirById(pid);
    	if (directory == null){
    		return new ResponseEntity<>("目录不存在",HttpStatus.NOT_FOUND);
		}
		HashMap<String, Object> result = new HashMap<>(16);
		//获取目录
		List<Object> directories = directorySerivce.getListDircByPid(pid, userId);
		List<Object> userFiles = userFileService.getListFileByPid(pid, userId);
		List<Object> parentDirs = new ArrayList<>();
		parentDirs = directorySerivce.getListPartDirectoryById(pid, userId, parentDirs);
		result.put("id",pid);
		result.put("dirs",directories);
		result.put("file",userFiles);
		result.put("path",parentDirs);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
}
