package org.lhq.feign;

import lombok.extern.slf4j.Slf4j;
import org.lhq.entity.UserFile;
import org.lhq.service.UserFileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: admin-service
 * @description: 对UserFile外提供服务
 * @author: Wang defa
 * @create: 2021-01-05 19:36
 */

@RestController
@RequestMapping("userFile-feign")
@Slf4j
public class UserFileFeign {
    @Resource
    private UserFileService userFileService;
    @GetMapping("{id}")
    public UserFile getUserFileById(@PathVariable("id") Long id){
        return userFileService.getUserFileDao().selectById(id);
    }
    @DeleteMapping("{id}")
    public void deleteUserFileById(@PathVariable("id") Long id){
        userFileService.getUserFileDao().deleteById(id);
    }
    @PostMapping("update")
    public void updateUserFile(@RequestBody UserFile userFile){
        userFileService.getUserFileDao().updateById(userFile);
    }
    @PostMapping("save")
    public void saveUserFile(@RequestBody UserFile userFile){
        userFileService.save(userFile);
    }
}
