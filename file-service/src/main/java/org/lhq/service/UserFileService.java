package org.lhq.service;

import org.lhq.dao.UserFileDao;

import java.util.List;

public interface UserFileService {
	UserFileDao getUserFileDao();
	List<Object> getListFileByPid(Long pid, Long userId);
	void deleteById(Long id);
	void move(Long sourceFileId,Long targetId);
}
