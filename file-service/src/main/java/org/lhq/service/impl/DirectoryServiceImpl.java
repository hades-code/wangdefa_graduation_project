package org.lhq.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.lhq.dao.DirectoryDao;
import org.lhq.gp.product.entity.Directory;
import org.lhq.service.DirectorySerivce;
import org.lhq.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author hades
 */
@Service
@Slf4j
public class DirectoryServiceImpl implements DirectorySerivce {
	@Resource
	DirectoryDao directoryDao;
	@Resource
	UserService userService;
	@Override
	public List<Object> getListPartDirectoryById(Long id, Long userId, List list) {
		Directory one = directoryDao.getListParDirById(id, userId);
		log.info("找到的目录为:{}",one);
		HashMap<String, Object> map = new HashMap<>(16);
		map.put("id",one.getId());
		if (one.getParentId() == null){
			map.put("name","全部文件");
			list.add(map);
		}else {
			map.put("name",one.getDirectoryName());
			list.add(map);
			getListPartDirectoryById(one.getParentId(),userId,list);
		}
		return list;
	}
}
