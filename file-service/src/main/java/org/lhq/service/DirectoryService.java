package org.lhq.service;

import org.lhq.entity.vo.TreeModel;
import org.lhq.entity.vo.Item;
import org.lhq.dao.DirectoryDao;
import org.lhq.entity.Directory;
import org.lhq.exception.ProjectException;

import java.util.List;

public interface DirectoryService {
	DirectoryDao getDirectoryDao();

	/**
	 * 查询某一目录的上级目录
	 *
	 * @param id
	 * @param userId
	 * @param list
	 * @return
	 */
	List<Object> getListPartDirectoryById(Long id, Long userId, List list);

	/**
	 * 查询某一目录和他下级目录
	 * @param id
	 * @param list
	 * @return
	 */
	List<Directory> getListSubDirectoryById(Long id,List list);

	/**
	 * 查询某一目录
	 */
	List<Object> getListDircByPid(Long id, Long userId);

	/**
	 * 新建文件夹
	 *
	 * @param dirName
	 * @param pid
	 * @param userId
	 * @return
	 */
	Boolean mkdir(String dirName, Long pid, Long userId) throws ProjectException;

	/**
	 * 根据文件夹id获取文件夹
	 *
	 * @param id
	 * @return
	 */
	Directory getDirById(Long id);

	/**
	 * 根据pid和userId 获取文件夹
	 *
	 * @param id
	 * @param userId
	 * @return
	 */
	Directory getDirByPid(Long id, Long userId);

	/**
	 * 保存文件夹......没啥用
	 *
	 * @param directory
	 * @return
	 */
	Integer saveDir(Directory directory);

	/**
	 * 复制文件夹
	 *
	 * @param sourceId
	 * @param targetId
	 */
	void copyDir(Long sourceId, Long targetId);

	/**
	 * 移动文件夹
	 *
	 * @param sourceId
	 * @param targetId
	 */
	void moveDir(Long sourceId, Long targetId);

	/**
	 * 通过id更新文件夹
	 *
	 * @param directory
	 * @return
	 */
	Integer updateById(Directory directory);

	/**
	 * 移动文件夹和文件
	 *
	 * @param items
	 * @param targetId
	 * @return
	 */
	Boolean moveDirAndFile(List<Item> items, Long targetId);

	/**
	 * 删除文件夹和文件
	 *
	 * @param items
	 * @return
	 */
	Boolean deleteDirAndFile(List<Item> items);

	/**
	 * 复制文件夹个文件
	 *
	 * @param items
	 * @param targetId
	 * @return
	 */
	Boolean copyDirAndFile(List<Item> items, Long targetId);
	Boolean copyDirAndFile(List<Item> items, Long targetId,Long userId);

	/**
	 * 树状结构返回目录结构
	 *
	 * @param id
	 * @param list
	 * @return
	 */
	List listDir(Long id, List list);

	/**
	 * 根据名字查找目录
	 * @param name
	 * @param userId
	 * @return
	 */
	List findDirByName(String name, Long userId);
	List<TreeModel> getDirTree(Long userId, List<TreeModel> models);
	List getRecycleBin(Long userId);
}
