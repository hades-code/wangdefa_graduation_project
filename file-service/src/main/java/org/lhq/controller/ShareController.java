package org.lhq.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.lhq.annotation.JsonParam;
import org.lhq.common.Item;
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

	@PostMapping("shareFile")
	public Object shareDirAndFile(@JsonParam(value = "item", type = Item.class) List<Item> item,
								  @JsonParam(value = "userId", type = Long.class) Long userId,
								  @JsonParam(value = "shareLock", type = Boolean.class) boolean shareLock,
								  @JsonParam(value = "shareCode", type = String.class, required = false) String shareCode,
								  @JsonParam(value = "expirationTime", type = Integer.class, required = false) Integer expirationTime) {
		return shareService.shareDirAndFile(item, userId, shareLock, shareCode, expirationTime);
	}
}
