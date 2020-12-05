package org.lhq.service;

import org.lhq.dao.DirectoryDao;
import org.lhq.gp.product.entity.Directory;

import java.util.List;

public interface DirectorySerivce {
	DirectoryDao getDirectoryDao();
	/**
	 * 查询某一目录的上级目录
	 * @param id
	 * @param userId
	 * @param list
	 * @return
	 */
	List<Object> getListPartDirectoryById(Long id,Long userId,List list);
	/**
	 * 查询某一目录
	 */
	List<Object> getListDircByPid(Long id,Long userId);

	Directory getDirById(Long id);
	Directory getDirByPid(Long id,Long userId);
	Integer saveDir(Directory directory);
	Integer updateById (Directory directory);
}
