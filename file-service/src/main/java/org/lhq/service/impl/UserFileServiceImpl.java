package org.lhq.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.lhq.common.ActionType;
import org.lhq.dao.UserFileDao;
import org.lhq.entity.UserFile;
import org.lhq.service.UserFileService;
import org.lhq.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author hades
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "userFileCache")
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
	public List<Object> getListFileByPid(Long pid, Long userId) {
		List<Object> files = new ArrayList<>();
		List<UserFile> userFiles = userFileDao.selectList(new QueryWrapper<UserFile>().lambda()
				.select(UserFile::getId, UserFile::getUserId, UserFile::getFileName, UserFile::getFileType, UserFile::getFileSize, UserFile::getModifyTime)
				.eq(UserFile::getDirectoryId, pid)
				.eq(UserFile::getUserId, userId)
				.ne(UserFile::getFileStatus, ActionType.DELETE.code)
				.ne(UserFile::getFileStatus, ActionType.ILLEGAL.code));
		for (UserFile userFile : userFiles) {
			HashMap<String, Object> fileMap = new HashMap<>(16);
			fileMap.put("id", userFile.getId());
			fileMap.put("name", userFile.getFileName());
			fileMap.put("type", userFile.getFileType());
			fileMap.put("size", userFile.getFileSize());
			fileMap.put("modifyTime", DateUtil.format(userFile.getModifyTime(), "yyyy-MM-dd HH:mm"));
			files.add(fileMap);
		}
		return files;
	}

	@Override
	public List<UserFile> getUserFileFileByUserId(Long userId) {
		return userFileDao.selectList(new LambdaQueryWrapper<UserFile>().eq(UserFile::getUserId, userId));
	}

	@Override
	public void deleteById(Long id) {
		UserFile userFile = this.userFileDao.selectById(id);
		userService.updateStorage(userFile.getUserId(), userFile.getFileSize(), ActionType.DELETE.code);
		userFile.setFileStatus(ActionType.DELETE.code);
		userFile.setModifyTime(LocalDateTime.now());
		UpdateWrapper<UserFile> updateWrapper = new UpdateWrapper<>();
		updateWrapper.lambda()
				.eq(UserFile::getId, userFile.getId())
				.set(UserFile::getFileStatus, ActionType.DELETE.code)
				.set(UserFile::getModifyTime, new Date());
		this.userFileDao.update(null, updateWrapper);
	}

	@Override
	public void save(UserFile userFile) {
		int insert = userFileDao.insert(userFile);
	}

	@Override
	public void move(Long sourceFileId, Long targetId) {
		UserFile userFile = this.dealFileOfDir(sourceFileId, targetId);
		userFile.setDirectoryId(targetId);
		userFile.setModifyTime(LocalDateTime.now());
		this.userFileDao.updateById(userFile);
	}

	@Override
	public void copy(Long sourceFileId, Long targetDirId) {
		UserFile userFile = this.dealFileOfDir(sourceFileId, targetDirId);
		//修改文件信息
		if (userFile.getDirectoryId().equals(targetDirId)) {
			userFile.setFileName(userFile.getFileName() + DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd_HH_mm"));
		}
		userFile.setId(null);
		userFile.setDirectoryId(targetDirId);
		userFile.setCreateTime(LocalDateTime.now());
		userFile.setModifyTime(LocalDateTime.now());
		//修改用户信息
		this.userService.updateStorage(userFile.getUserId(), userFile.getFileSize(), ActionType.COPY.code);
		//保存文件
		this.userFileDao.insert(userFile);

	}

	/**
	 * 检查md5 和文件大小是否一致
	 *
	 * @param userFile
	 * @param userFile2
	 * @return
	 */
	private Boolean isSameFile(UserFile userFile, UserFile userFile2) {
		String md5 = userFile.getMd5();
		String md51 = userFile2.getMd5();
		Double fileSize1 = userFile.getFileSize();
		Double fileSize = userFile2.getFileSize();
		if (fileSize == null || fileSize1 == null) {
			return false;
		}
		return StrUtil.equals(md51, md5) && fileSize.equals(fileSize1);
	}

	/**
	 * 检查文件名和后缀名是否一致
	 *
	 * @param userFile
	 * @param userFile2
	 * @return
	 */
	private Boolean isSameFileName(UserFile userFile, UserFile userFile2) {
		return StrUtil.equals(userFile.getFileName(), userFile2.getFileName()) && StrUtil.equals(userFile.getFileType(), userFile2.getFileType());
	}

	/**
	 * 处理复制到本目录下的文件和原来的文件时候有冲突
	 *
	 * @param sourceId
	 * @param tragetId
	 * @return
	 */
	private UserFile dealFileOfDir(Long sourceId, Long tragetId) {
		Date date = new Date();
		UserFile userFile = this.userFileDao.selectById(sourceId);
		List<UserFile> fileList = this.userFileDao.selectList(new QueryWrapper<UserFile>().lambda()
				.select(UserFile::getId, UserFile::getFileName, UserFile::getMd5, UserFile::getFileSize, UserFile::getFileType)
				.eq(UserFile::getDirectoryId, tragetId)
				.ne(UserFile::getFileStatus, ActionType.DELETE.code)
				.ne(UserFile::getFileStatus, ActionType.ILLEGAL.code));
		for (UserFile file : fileList) {
			if (isSameFile(userFile, file) && isSameFileName(userFile, file)) {
				userFile.setFileName(userFile.getFileName() + DateUtil.format(date, "yyyy-MM-dd_HH-mm"));
			}
		}
		return userFile;
	}
}
