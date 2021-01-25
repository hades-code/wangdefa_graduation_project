package org.lhq.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.lhq.annotation.JsonParam;
import org.lhq.entity.vo.Item;
import org.lhq.entity.Directory;
import org.lhq.entity.UserFile;
import org.lhq.exception.ProjectException;
import org.lhq.service.IShareService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hades
 */
@RestController
@Slf4j
@Api(tags = "文件分享接口")
@RequestMapping("share")
public class ShareController {
    @Resource
    IShareService shareService;

    @GetMapping("/{shareLink}")
    public List<Item> shareLink(@PathVariable String shareLink, String shareCode) throws ProjectException {
        Map share = shareService.getShare(shareLink, shareCode);
        List<Directory> dirs = MapUtil.get(share, "dirs", List.class);
        List<UserFile> files = MapUtil.get(share, "files", List.class);
        ArrayList<Item> items = new ArrayList<>();
        if (dirs != null && !dirs.isEmpty()) {
            for (Directory dir : dirs) {
                Item item = new Item()
                        .setId(dir.getId())
                        .setName(dir.getDirectoryName())
                        .setType("dir")
                        .setModifyTime(DateUtil.format(dir.getModifyTime(), "yyyy-MM-dd HH:mm"));
                items.add(item);
            }
        }
        if (files != null && !files.isEmpty()) {
            for (UserFile file : files) {
                Item item = new Item()
                        .setId(file.getId())
                        .setName(file.getFileName())
                        .setType(file.getFileType())
                        .setModifyTime(DateUtil.format(file.getModifyTime(), "yyyy-MM-dd HH:mm"));
                items.add(item);
            }
        }
        return items;
    }

    @GetMapping("needShareCode/{shareLink}")
    public Boolean needShareCode(@PathVariable("shareLink") String shareLink) {
        return this.shareService.needShareCode(shareLink);
    }

    @PostMapping("shareFile")
    public Object shareDirAndFile(ArrayList<Item> item,
                                  Long userId,
                                  boolean shareLock,
                                  String shareCode,
                                  Integer expirationTime) {
        return shareService.shareDirAndFile(item, userId, shareLock, shareCode, expirationTime);
    }
	@ApiOperation(value = "确认提取码是否正确")
    @PostMapping("confirmShareCode")
    public Boolean confirmShareCode(String shareLink, String shareCode) throws ProjectException {
        return this.shareService.confirmShareCode(shareLink, shareCode);
    }
    @ApiOperation(value = "获取分享目录树")
    @GetMapping("getShareDirTree")
    public List getShareDirTree(){
        return null;
    }
}
