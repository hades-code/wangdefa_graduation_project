package org.lhq.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.lhq.dao.UserFileDao;
import org.lhq.common.ActionType;
import org.lhq.entity.UserFile;
import org.lhq.service.UserFileService;
import org.lhq.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
		List<UserFile> userFiles = userFileDao.selectList(new QueryWrapper<UserFile>().lambda()
				.eq(UserFile::getDirectoryId,pid)
				.eq(UserFile::getUserId,userId)
				.ne(UserFile::getFileStatus,ActionType.DELETE.code)
				.ne(UserFile::getFileStatus,ActionType.ILLEGAL.code));
		for (UserFile userFile : userFiles) {
			HashMap<String, Object> fileMap = new HashMap<>(16);
			fileMap.put("id",userFile.getId());
			fileMap.put("name", userFile.getFileName());
			fileMap.put("type", userFile.getFileType());
			fileMap.put("size",userFile.getFileSize());
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
