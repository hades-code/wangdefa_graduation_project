package org.lhq.service.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.lhq.dao.UserFileDao;
import org.lhq.gp.product.entity.UserFile;
import org.lhq.service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
	@Override
	public List<Object> getListFileByPid(Long pid,Long userId){
		List<Object> files = new ArrayList<>();
		List<UserFile> listUserFile = userFileDao.getListUserFileByPid(pid, userId);
		for (UserFile userFile : listUserFile) {
			HashMap<String, Object> fileMap = new HashMap<>(16);
			fileMap.put("id",userFile.getId());
			fileMap.put("name", userFile.getFileName());
			fileMap.put("type", userFile.getFileType());
			fileMap.put("lastmodifytime", DateUtil.format(userFile.getModifyTime(),"yyyy-MM-dd HH:mm"));
			files.add(fileMap);
		}
		return files;
	}
}
