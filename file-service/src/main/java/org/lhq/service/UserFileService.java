package org.lhq.service;

import java.util.List;

public interface UserFileService {
	List<Object> getListFileByPid(Long pid, Long userId);
}
