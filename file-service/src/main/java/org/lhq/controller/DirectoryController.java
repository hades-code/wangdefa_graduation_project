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
import org.lhq.service.DirectoryService;
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
	DirectoryService directoryService;
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
		Boolean mkdir = directoryService.mkdir(dirName, parentId, userId);
		return mkdir ? "创建成功" : "创建失败";
	}

	@PostMapping("rename")
	public String updateDirName(String name, Long id) throws ProjectException {
		if (StrUtil.isEmpty(name) || id <= 0) {
			throw new ProjectException("文件名为空");
		}
		Directory directory = directoryService.getDirById(id);
		if (directory == null) {
			throw new ProjectException("文件夹不存在");
		}
		UpdateWrapper<Directory> updateWrapper = new UpdateWrapper<>();
		updateWrapper.lambda()
				.eq(Directory::getId, id)
				.set(Directory::getDirectoryName, name)
				.set(Directory::getModifyTime, new Date());
		directoryService.getDirectoryDao().update(null, updateWrapper);
		return "目录重命名成功";
	}

	/**
	 * 获取该文件夹下的文件和文件夹
	 * 并返回文件路径
	 * @param pid
	 * @param userId
	 * @return
	 */
	@PostMapping("/getCurrentDir")
	public Map getDir(@RequestParam(value = "pid",required = false) Long pid, @RequestParam("userId") Long userId) throws ProjectException {
		pid = Optional.ofNullable(pid).orElse(0L);
		Map<String, Object> result = new HashMap<>(16);
		//获取目录
		List<Object> directories = directoryService.getListDircByPid(pid, userId);
		List<Object> userFiles = userFileService.getListFileByPid(pid, userId);
		List<Object> parentDirs = new ArrayList<>();
		parentDirs = directoryService.getListPartDirectoryById(pid, userId, parentDirs);
		HashMap<String, String> map = new HashMap<>(16);
		map.put("name","根目录");
		parentDirs.add(map);
		result.put("id", pid);
		result.put("dirs", directories);
		result.put("file", userFiles);
		result.put("path", ListUtil.reverse(parentDirs));
		return result;
	}

	/**
	 * 获取目录和子目录
	 * 已经弃用，推荐使用DirTree
	 * @param userId
	 * @return
	 */
	@GetMapping("listDir")
	@Deprecated
	public List getListDir(Long userId) {
		Directory directory = this.directoryService.getDirByPid(0L, userId);
		List list = new ArrayList<>();
		list = directoryService.listDir(directory.getId(), list);
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
		Boolean result = directoryService.deleteDirAndFile(list);
		return "删除" + (result ? "成功" : "失败");
	}

	@PostMapping("copy")
	public String copy(@JsonParam(value = "targetId", type = Long.class) Long targetId, @JsonParam(value = "sourceListId", type = Item.class) List<Item> list) {
		directoryService.copyDirAndFile(list, targetId);
		return "复制成功";
	}

	@PostMapping("move")
	public String move(@JsonParam(value = "sourceListId", type = Item.class) List<Item> list, @JsonParam(value = "targetId", type = Long.class) Long targetId) {
		this.directoryService.moveDirAndFile(list, targetId);
		return "移动成功";
	}
	@PostMapping("dirTree")
	public List getDirTree(Long userId){
		return this.directoryService.getDirTree(userId,null);
	}
	@PostMapping("getRecycleBin")
	public List getRecycleBin(Long userId){
		return this.directorySerivce.getRecycleBin(userId);
	}


}
