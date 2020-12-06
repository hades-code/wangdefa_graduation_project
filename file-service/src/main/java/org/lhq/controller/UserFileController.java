package org.lhq.controller;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lhq.gp.product.entity.UserFile;
import org.lhq.service.UserFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author hades
 */
@RestController
@RequestMapping("file")
@Slf4j
public class UserFileController {

	@Resource
	UserFileService userFileService;

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
}
