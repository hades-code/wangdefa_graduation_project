package org.lhq.service;

import org.lhq.entity.UserFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: admin-service
 * @description: 管理员文件管理
 * @author: Wang defa
 * @create: 2021-01-05 11:37
 */

@FeignClient(name = "file-service")
@RequestMapping("file-feign")
public interface FileService {
    @PostMapping("save")
    Boolean saveUserFile(@RequestBody UserFile userFile);
    @PostMapping("delete")
    Boolean deleteUserFileById(@RequestParam("id") Long id);
    @PostMapping("get")
    UserFile getUserFileById(@RequestParam("id") Long id);
    @PostMapping("update")
    Boolean updateUserById(UserFile userFile);
}
