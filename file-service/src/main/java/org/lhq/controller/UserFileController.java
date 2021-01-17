package org.lhq.controller;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.lhq.entity.Directory;
import org.lhq.entity.UserFile;
import org.lhq.exception.ProjectException;
import org.lhq.service.DirectoryService;
import org.lhq.service.UserFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hades
 */
@Api(tags = "用户文件接口")
@RestController
@RequestMapping("file")
@Slf4j
public class UserFileController {

	@Resource
	UserFileService userFileService;
	@Resource
    DirectoryService directorySerivce;

	/**
	 * 重命名文件
	 *
	 * @param id
	 * @param name
	 * @return
	 */
	@PostMapping("rename")
	public Boolean rename(@RequestParam("id") Long id, @RequestParam("name") String name) throws ProjectException {
		if (StrUtil.isEmpty(name)) {
			throw new ProjectException("重命名失败,文件名为空");
		}
		return userFileService.rename(name,id);
	}

	/**
	 * 删除文件
	 *
	 * @param ids
	 * @return
	 */
	@PostMapping("/delete")
	public ResponseEntity delete(List<Long> ids) {
		if (ids.isEmpty()) {
			log.error("删除失败");
		}
		for (Long id : ids) {
			UserFile userFile = userFileService.getUserFileDao().selectById(id);
			if (userFile == null) {
				log.error("文件不存在，删除失败");
			} else {
				userFileService.getUserFileDao().deleteById(userFile.getId());
			}
		}
		return new ResponseEntity("删除完成", HttpStatus.OK);
	}

	@PostMapping(value = "copy")
	public String copyFile(Long sourceId, Long targetId) throws ProjectException {
		if (sourceId < 0 || targetId < 0) {
			throw new ProjectException("文件复制失败");
		}
		Directory directory = directorySerivce.getDirectoryDao().selectById(targetId);
		UserFile userFile = userFileService.getUserFileDao().selectById(sourceId);
		if (directory == null || userFile == null) {
			log.error("文件或目录不存在");
			throw new ProjectException("文件或者目录不存在");
		}
		this.userFileService.copy(sourceId, targetId);
		return "复制成功";
	}

	@PostMapping("/move")
	public ResponseEntity moveFile(Long sourceId, Long targetId) throws ProjectException {
		if (sourceId < 0 || targetId < 0) {
			throw new ProjectException("移动失败");
		}
		// 判断文件、目标目录是否存在
		Directory directory = directorySerivce.getDirectoryDao().selectById(targetId);
		UserFile userFile = userFileService.getUserFileDao().selectById(sourceId);
		if (directory == null || userFile == null) {
			throw new ProjectException("目录或者文件不存在");
		}
		this.userFileService.move(sourceId, targetId);
		return new ResponseEntity("移动成功", HttpStatus.OK);
	}

	@GetMapping("getOne")
	public ResponseEntity getOne(Long id) throws ProjectException {
		if (id < 0) {
			throw new ProjectException("查找失败,Id错误");
		}
		UserFile userFile = userFileService.getUserFileDao().selectById(id);
		if (userFile == null) {
			throw new ProjectException("查找失败,文件不存在");
		}
		return ResponseEntity.ok(userFile);
	}

	@PostMapping("edit")
	public ResponseEntity<Object> edit(UserFile userFile) throws ProjectException {
		if (userFile == null || userFile.getId() == null) {
			throw new ProjectException("文件为空，修改失败");
		}
		UserFile getFile = userFileService.getUserFileDao().selectById(userFile.getId());
		if (StrUtil.isNotEmpty(userFile.getFileName())) {
			getFile.setFileName(userFile.getFileName());
		}
		this.userFileService.getUserFileDao().updateById(getFile);
		return ResponseEntity.ok("修改成功");
	}
}
