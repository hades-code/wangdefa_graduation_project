package org.lhq.service;

import org.lhq.dao.UserFileDao;
import org.lhq.entity.UserFile;
import org.lhq.exception.ProjectException;

import java.util.List;

public interface UserFileService {
	UserFileDao getUserFileDao();

	List<Object> getListFileByPid(Long pid, Long userId);
	List<UserFile> getUserFileFileByUserId(Long userId);

	void deleteById(Long id);

	void save(UserFile userFile);

	void move(Long sourceFileId, Long targetId);

	void copy(Long sourceFileId, Long targetDirId);

	Boolean rename(String newName,Long fileId) throws ProjectException;
}
