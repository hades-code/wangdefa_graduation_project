package org.lhq.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.lhq.dao.DirectoryDao;
import org.lhq.gp.product.entity.Directory;
import org.lhq.service.DirectorySerivce;
import org.lhq.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author hades
 */
@Service
@Slf4j
public class DirectoryServiceImpl implements DirectorySerivce {

	@Resource
	DirectoryDao directoryDao;

	/**
	 *查询某一目录的上级目录
	 * @param id
	 * @param userId
	 * @param list
	 * @return
	 */
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

	/**
	 * 查询某一目录，合他下面的文件
	 * @param id
	 * @param userId
	 * @return
	 */
	@Override
	public List<Object> getListDircById(Long id, Long userId) {
		ArrayList<Object> dirc = new ArrayList<>();
		Directory listParDirById = this.directoryDao.getListParDirById(id, userId);

		return null;
	}

	@Override
	public Directory getDirById(Long id){
		Directory directory = this.directoryDao.selectById(id);
		return directory;
	}
	@Override
	public Directory getDirByPid(Long id, Long userId){
		Directory dir = this.directoryDao.getDirByPid(id, userId);
		return dir;
	}
	@Override
	public Integer saveDir(Directory directory){
		int insert = this.directoryDao.insert(directory);
		return insert;
	}
	public Integer updateById (Directory directory){
		return this.directoryDao.updateById(directory);
	}

}
