package org.lhq.service;

import java.util.List;

public interface DirectorySerivce {
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
	List<Object> getListDircById(Long id,Long userId);

	/**
	 * 新建一个目录
	 * @param dirName
	 * @param pid
	 * @param userId
	 */
	void mkdir(String dirName,Long pid,Long userId);
}
