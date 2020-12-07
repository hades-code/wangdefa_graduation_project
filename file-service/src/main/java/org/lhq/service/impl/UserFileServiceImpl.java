package org.lhq.service.impl;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.lhq.dao.UserFileDao;
import org.lhq.gp.product.common.ActionType;
import org.lhq.gp.product.entity.UserFile;
import org.lhq.service.UserFileService;
import org.lhq.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author hades
 */
@Service
@Slf4j
public class UserFileServiceImpl implements UserFileService {
	@Resource
	UserFileDao userFileDao;
	@Resource
	UserService userService;

	@Override
	public UserFileDao getUserFileDao() {
		return this.userFileDao;
	}

	@Override
	public List<Object> getListFileByPid(Long pid,Long userId){
		List<Object> files = new ArrayList<>();
		List<UserFile> listUserFile = userFileDao.getListUserFileByPid(pid, userId);
		for (UserFile userFile : listUserFile) {
			HashMap<String, Object> fileMap = new HashMap<>(16);
			fileMap.put("id",userFile.getId());
			fileMap.put("name", userFile.getFileName());
			fileMap.put("type", userFile.getFileType());
			fileMap.put("modifyTime", DateUtil.format(userFile.getModifyTime(),"yyyy-MM-dd HH:mm"));
			files.add(fileMap);
		}
		return files;
	}

	@Override
	public void deleteById(Long id) {
		UserFile userFile = this.userFileDao.selectById(id);
		userService.updateStorage(userFile.getUserId(),userFile.getFileSize(), ActionType.DELETE.code);
		userFile.setFileStatus(ActionType.DELETE.code);
		userFile.setModifyTime(new Date());
		this.userFileDao.updateById(userFile);
	}
	@Override
	public void move(Long sourceFileId,Long targetId){
		UserFile userFile = this.userFileDao.selectById(sourceFileId);
		userFile.setDirectoryId(targetId);
		userFile.setModifyTime(new Date());
		this.userFileDao.updateById(userFile);
	}

	@Override
	public void copy(Long sourceFileId, Long targetDirId) {
		Date date = new Date();
		UserFile userFile = this.userFileDao.selectById(sourceFileId);
		UserFile newUserFile = userFile;
		//修改文件信息
		if (newUserFile.getDirectoryId().equals(targetDirId)){
			newUserFile.setFileName(newUserFile.getFileName() + DateUtil.format(date,"yyyy-MM-dd HH:mm:ss"));
		}
		newUserFile.setId(null);
		newUserFile.setDirectoryId(targetDirId);
		newUserFile.setCreateTime(date);
		newUserFile.setModifyTime(date);
		//修改用户信息
		this.userService.updateStorage(userFile.getUserId(),userFile.getFileSize(),ActionType.COPY.code);
		//保存文件
		this.userFileDao.insert(newUserFile);

	}
}
