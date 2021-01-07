package org.lhq.controller;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.lhq.annotation.JsonParam;
import org.lhq.entity.vo.Item;
import org.lhq.entity.Directory;
import org.lhq.exception.ProjectException;
import org.lhq.service.DirectorySerivce;
import org.lhq.service.UserFileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @program: wangdefa_graduation_project
 * @description: 目录Controller
 * @author: Wang defa
 * @create: 2020-12-04 09:51
 */

@RestController
@RequestMapping("dir")
@Slf4j
@Api(tags = "目录接口")
public class DirectoryController {
	@Resource
	DirectorySerivce directorySerivce;
	@Resource
	UserFileService userFileService;

	@ApiImplicitParam(name = "dirName", value = "文件夹名称", required = true)
	@ApiOperation(value = "新建目录")
	@PostMapping(value = "mkdir")
	public String mkdir(@RequestParam(value = "dirName", required = false) String dirName,
						@RequestParam(value = "pid", required = false) Long parentId,
						@RequestParam(value = "userId", required = false) Long userId) throws ProjectException {
		if (StrUtil.isEmpty(dirName)) {
			throw new ProjectException("文件夹名为空");
		}
		Boolean mkdir = directorySerivce.mkdir(dirName, parentId, userId);
		return mkdir ? "创建成功" : "创建失败";
	}

	@PostMapping("rename")
	public String updateDirName(String name, Long id) throws ProjectException {
		if (StrUtil.isEmpty(name) || id <= 0) {
			throw new ProjectException("文件名为空");
		}
		Directory directory = directorySerivce.getDirById(id);
		if (directory == null) {
			throw new ProjectException("文件夹不存在");
		}
		UpdateWrapper<Directory> updateWrapper = new UpdateWrapper<>();
		updateWrapper.lambda()
				.eq(Directory::getId, id)
				.set(Directory::getDirectoryName, name)
				.set(Directory::getModifyTime, new Date());
		directorySerivce.getDirectoryDao().update(null, updateWrapper);
		return "目录重命名成功";
	}

	/**
	 * 获取该文件夹下的文件和文件夹
	 *
	 * @param pid
	 * @param userId
	 * @return
	 */
	@GetMapping("/getDir")
	public Map getDir(@RequestParam("pid") Long pid, @RequestParam("userId") Long userId) throws ProjectException {
		//如果传上来的pid为空泽获取根目录
		if (pid == null || pid <= 0) {
			Directory dir = directorySerivce.getDirByPid(0L, userId);
			if (dir != null) {
				pid = dir.getId();
			}
		}
		Directory directory = directorySerivce.getDirById(pid);
		if (directory == null) {
			throw new ProjectException("目录不存在");
		}
		HashMap<String, Object> result = new HashMap<>(16);
		//获取目录
		List<Object> directories = directorySerivce.getListDircByPid(pid, userId);
		List<Object> userFiles = userFileService.getListFileByPid(pid, userId);
		List<Object> parentDirs = new ArrayList<>();
		parentDirs = directorySerivce.getListPartDirectoryById(pid, userId, parentDirs);
		result.put("id", pid);
		result.put("dirs", directories);
		result.put("file", userFiles);
		result.put("path", ListUtil.reverse(parentDirs));
		return result;
	}

	/**
	 * 获取目录和子目录
	 *
	 * @param userId
	 * @return
	 */
	@GetMapping("listDir")
	public List getListDir(Long userId) {
		Directory directory = this.directorySerivce.getDirByPid(0L, userId);
		List list = new ArrayList<>();
		list = directorySerivce.listDir(directory.getId(), list);
		Map<String, Object> result = new HashMap<>(16);
		result.put("id", directory.getId());
		result.put("name", "全部文件");
		result.put("children", list);
		List<Object> allList = new ArrayList<>();
		allList.add(result);
		return allList;
	}

	/**
	 * 删除文件
	 *
	 * @param list
	 * @return
	 */
	@PostMapping("deleteDir")
	public String deleteDir(@RequestBody List<Item> list) {
		Boolean result = directorySerivce.deleteDirAndFile(list);
		return "删除" + (result ? "成功" : "失败");
	}

	@PostMapping("copy")
	public String copy(@JsonParam(value = "targetId", type = Long.class) Long targetId, @JsonParam(value = "sourceListId", type = Item.class) List<Item> list) {
		directorySerivce.copyDirAndFile(list, targetId);
		return "复制成功";
	}

	@PostMapping("move")
	public String move(@JsonParam(value = "sourceListId", type = Item.class) List<Item> list, @JsonParam(value = "targetId", type = Long.class) Long targetId) {
		this.directorySerivce.moveDirAndFile(list, targetId);
		return "移动成功";
	}


}
