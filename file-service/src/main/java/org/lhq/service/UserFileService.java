package org.lhq.service;

import org.lhq.dao.UserFileDao;
import org.lhq.entity.UserFile;

import java.util.List;

public interface UserFileService {
	UserFileDao getUserFileDao();

	List<Object> getListFileByPid(Long pid, Long userId);

	void deleteById(Long id);

	void save(UserFile userFile);

	void move(Long sourceFileId, Long targetId);

	void copy(Long sourceFileId, Long targetDirId);
}
