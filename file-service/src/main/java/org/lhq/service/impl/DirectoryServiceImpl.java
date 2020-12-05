package org.lhq.service.impl;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.lhq.dao.DirectoryDao;
import org.lhq.gp.product.entity.Directory;
import org.lhq.service.DirectorySerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

	@Override
	public DirectoryDao getDirectoryDao() {
		return this.directoryDao;
	}

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
			map.put("name","根目录");
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
	 * @param pid
	 * @param userId
	 * @return
	 */
	@Override
	public List<Object> getListDircByPid(Long pid, Long userId) {
		ArrayList<Object> dirc = new ArrayList<>();
		List<Directory> directoryList = this.directoryDao.getListDirByPid(pid, userId);
		for (Directory directory : directoryList) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("id",directory.getId());
			map.put("dirName",directory.getDirectoryName());
			map.put("modifyTime", DateUtil.format(directory.getModifyTime(),"yyyy-MM-dd HH:mm"));
			dirc.add(map);
		}
		return dirc;
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
	@Override
	public Integer updateById (Directory directory){
		return this.directoryDao.updateById(directory);
	}

}
