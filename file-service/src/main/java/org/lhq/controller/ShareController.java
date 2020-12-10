package org.lhq.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.lhq.common.Item;
import org.lhq.entity.Directory;
import org.lhq.entity.UserFile;
import org.lhq.service.IShareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

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
	public ResponseEntity shareLink(@PathVariable String shareLink,String shareCode){
		Map share = shareService.getShare(shareLink, shareCode);
		List<Directory> dirs = MapUtil.get(share, "dirs", List.class);
		List<UserFile> files = MapUtil.get(share, "files", List.class);
		HashMap<String, Object> result = new HashMap<>();
		ArrayList<Map> dirMap = new ArrayList<>();
		ArrayList<Item> items = new ArrayList<>();
		if ( dirs!=null && !dirs.isEmpty() ){
			for (Directory dir : dirs) {
				HashMap<String, Object> map = new HashMap<>();
				Item item = new Item()
						.setId(dir.getId())
						.setName(dir.getDirectoryName())
						.setType("dir")
						.setModifyTime(DateUtil.format(dir.getModifyTime(), "yyyy-MM-dd HH:mm"));
				items.add(item);
				map.put("id",dir.getId());
				map.put("name",dir.getDirectoryName());
				map.put("type","dir");
				map.put("modifyTime", DateUtil.format(dir.getModifyTime(),"yyyy-MM-dd HH:mm"));
				dirMap.add(map);
			}
		}
		ArrayList<Map> fileMap = new ArrayList<>();
		if ( files != null && !files.isEmpty() ){
			for (UserFile file : files) {
				HashMap<String, Object> map = new HashMap<>();
				Item item = new Item()
						.setId(file.getId())
						.setName(file.getFileName())
						.setType(file.getFileType())
						.setModifyTime(DateUtil.format(file.getModifyTime(), "yyyy-MM-dd HH:mm"));
				items.add(item);
				map.put("id",file.getId());
				map.put("name",file.getFileName());
				map.put("type",file.getFileType());
				map.put("modifyTime",file.getModifyTime());
				fileMap.add(map);
			}
		}
		return ResponseEntity.ok(items);
	}
	@PostMapping("shareFile")
	public ResponseEntity shareDirAndFile(@RequestBody List<Item> item, Long userId, boolean shareLock, Date expirationTime){
		Object file = shareService.shareDirAndFile(item, userId,shareLock,expirationTime);
		return ResponseEntity.ok(file);
	}
}
