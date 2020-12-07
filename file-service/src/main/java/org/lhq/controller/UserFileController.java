package org.lhq.controller;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lhq.gp.product.entity.Directory;
import org.lhq.gp.product.entity.UserFile;
import org.lhq.service.DirectorySerivce;
import org.lhq.service.UserFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author hades
 */
@RestController
@RequestMapping("file")
@Slf4j
public class UserFileController {

	@Resource
	UserFileService userFileService;
	@Resource
	DirectorySerivce directorySerivce;

	/**
	 * 重命名文件
	 * @param id
	 * @param name
	 * @return
	 */
	@PostMapping("rename")
	public ResponseEntity rename(Long id,String name){
		if (StrUtil.isEmpty(name)){
			log.error("重命名失败，文件名为空");
		}
		UserFile userFile = userFileService.getUserFileDao().selectById(id);
		if (userFile == null){
			log.error("文件不存在");
		}
		userFile.setFileName(name);
		userFile.setModifyTime(new Date());
		userFileService.getUserFileDao().updateById(userFile);
		return new ResponseEntity("修改成功", HttpStatus.OK);
	}

	/**
	 * 删除文件
	 * @param ids
	 * @return
	 */
	@PostMapping("/delete")
	public ResponseEntity delete(List<Long> ids){
		if (ids.isEmpty()){
			log.error("删除失败");
		}
		for (Long id : ids) {
			UserFile userFile = userFileService.getUserFileDao().selectById(id);
			if (userFile == null){
				log.error("文件不存在，删除失败");
			}else {
				userFileService.getUserFileDao().deleteById(userFile.getId());
			}
		}
		return new ResponseEntity("删除完成",HttpStatus.OK);
	}
	@PostMapping("copy")
	public ResponseEntity copyFile(Long sourceId,Long targetId){
		if (sourceId < 0 || targetId < 0){
			log.error("复制失败");
		}
		Directory directory = directorySerivce.getDirectoryDao().selectById(targetId);
		UserFile userFile = userFileService.getUserFileDao().selectById(sourceId);
		if (directory == null || userFile == null){
			log.error("文件或目录不存在");
		}
		this.userFileService.copy(sourceId,targetId);
		return new ResponseEntity("复制成功",HttpStatus.OK);
	}
	@PostMapping("/move")
	public ResponseEntity moveFile(Long sourceId,Long targetId){
		if (sourceId < 0 || targetId <0){
			log.error("移动失败");
		}
		// 判断文件、目标目录是否存在
		Directory directory = directorySerivce.getDirectoryDao().selectById(targetId);
		UserFile userFile = userFileService.getUserFileDao().selectById(sourceId);
		if (directory == null || userFile == null){
			log.error("文件或文件夹不存在");
		}
		this.userFileService.move(sourceId,targetId);
		return new ResponseEntity("移动成功",HttpStatus.OK);
	}
	@GetMapping("getOne")
	public ResponseEntity getOne(Long id){
		if (id < 0 ){
			log.error("查找失败!ID错误");
		}
		UserFile userFile = userFileService.getUserFileDao().selectById(id);
		if (userFile == null){
			log.error("问价不存在");
		}
		return ResponseEntity.ok(userFile);
	}
	@PostMapping("edit")
	public ResponseEntity<Object> edit(UserFile userFile){
		if (userFile == null || userFile.getId() == null){
			log.error("修改失败");
		}
		UserFile getFile = userFileService.getUserFileDao().selectById(userFile.getId());
		if (StrUtil.isNotEmpty(userFile.getFileName())){
			getFile.setFileName(userFile.getFileName());
		}
		this.userFileService.getUserFileDao().updateById(getFile);
		return ResponseEntity.ok("修改成功");
	}
}
