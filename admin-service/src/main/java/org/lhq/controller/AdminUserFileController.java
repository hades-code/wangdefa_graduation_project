package org.lhq.controller;

import org.lhq.entity.UserFile;
import org.lhq.service.FileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: admin-service
 * @description: 管理员管理文件
 * @author: Wang defa
 * @create: 2021-01-05 19:16
 */

@RestController
@RequestMapping("admin/file")
public class AdminUserFileController {
    @Resource
    FileService fileService;
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id){
        fileService.deleteUserFileById(id);
    }
    @GetMapping("{id}")
    public UserFile getUserFileById(@PathVariable("id") Long id){
        return fileService.getUserFileById(id);
    }
    @GetMapping("user/{userId}")
    public List<UserFile> getUserFileListByUserId(@PathVariable("userId") Long userId){
    	return fileService.getUserFileListByUserId(userId);
	}
    @PostMapping("update")
    public void updateUserFile(@RequestBody UserFile userFile){
        fileService.updateUserById(userFile);
    }
}
