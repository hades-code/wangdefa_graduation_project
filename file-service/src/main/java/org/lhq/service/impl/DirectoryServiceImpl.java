package org.lhq.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.lhq.dao.DirectoryDao;
import org.lhq.gp.product.common.Item;
import org.lhq.gp.product.entity.Directory;
import org.lhq.gp.product.entity.UserFile;
import org.lhq.service.DirectorySerivce;
import org.lhq.service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author hades
 */
@Service
@Slf4j
public class DirectoryServiceImpl implements DirectorySerivce {

	@Resource
	DirectoryDao directoryDao;
	@Resource
	UserFileService userFileService;

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
		if (id ==null || id<=0){
			log.error("没有上一级目录");
			return null;
		}
		Directory one = directoryDao.getListParDirById(id, userId);
		log.info("找到的目录为:{}",one);
		HashMap<String, Object> map = new HashMap<>(16);
		map.put("id",one.getId());
		if (one.getParentId() == 0){
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
	 * 查询某一目录，和他下面的文件
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

	@Override
	public Boolean moveDir(Long sourceId, Long targetId) {
		Date date = new Date();
		//获取源文件夹
		Directory sourceDir = this.directoryDao.selectById(sourceId);
		sourceDir.setParentId(targetId);
		sourceDir.setModifyTime(date);
		//获取源目文件夹的子文件夹
		List<Directory> subDirs = this.directoryDao.getListDirByPid(sourceId);
		//获取源目录下的文件
		List<UserFile> sourceFiles = this.userFileService.getUserFileDao().getListUserFileByPid(sourceId);
		for (UserFile sourceFile : sourceFiles) {
			sourceFile.setModifyTime(date);
			this.userFileService.getUserFileDao().updateById(sourceFile);
		}
		//遍历
		for (Directory subDir : subDirs) {
			moveDir(subDir.getId(),sourceDir.getId());
		}
		//保存
		this.directoryDao.updateById(sourceDir);

		return null;
	}

	@Override
	public Boolean deleteDirAndFile(List<Item> items){
		Map common = this.common(items);
		//获取文件夹
		List<Directory> dirs = MapUtil.get(common, "dirs", List.class);
		//获取文件
		List<UserFile> files = MapUtil.get(common, "files", List.class);
		for (Directory dir : dirs) {
			log.info("删除的文件夹为:{}",dir.getDirectoryName());
			directoryDao.deleteById(dir.getId());
		}
		for (UserFile file : files) {
			log.info("删除的文件为:{}",file.getFileName());
			userFileService.deleteById(file.getId());
		}
		return true;
	}
	@Override
	public Boolean copyDirAndFile(List<Item> items,Long targetId){
		Map common = this.common(items);
		//获取文件夹
		List<Directory> dirs = MapUtil.get(common, "dirs", List.class);
		//获取文件
		List<UserFile> files = MapUtil.get(common, "files", List.class);

		Long checkId = targetId;
		while (true) {
			Directory directory = directoryDao.selectById(checkId);
			for (Directory dir : dirs) {
				if (dir.getId().equals(targetId)){
					log.error("不能移动到相同文件「{}」夹下",dir.getDirectoryName());
				}
				if (dir.getId().equals(targetId)){
					log.error("不能移动到子文件夹");
				}
			}
			if (directory.getId().equals(0L)){
				break;
			}
			checkId = directory.getId();
		}
		for (Directory dir : dirs) {
			moveDir(dir.getId(),targetId);
		}
		for (UserFile file : files) {
			userFileService.move(file.getId(),targetId);
		}
		return true;
	}

	@Override
	public List findDirByName(String name, Long userId) {
		Directory dirByName = this.directoryDao.getDirByName(name, userId);
		//TO-DO
		return null;
	}

	private Map common(List<Item> list) {
		if (list == null){
			return new HashMap();
		}
		List<Directory> dirs = new ArrayList<>();
		List<UserFile> files  = new ArrayList<>();
		HashMap<String, Object> result = new HashMap<>();
		for (Item item : list) {
			if (StrUtil.equals(item.getType(),"dir")){
				Directory directory = getDirById(item.getId());
				if (directory == null){
					log.error("不存在此目录");
				}
				dirs.add(directory);
			}else {
				UserFile userFile = userFileService.getUserFileDao().selectById(item.getId());
				if (userFile == null){
					log.error("不存在此文件");
				}
				files.add(userFile);
			}
		}
		result.put("dirs",dirs);
		result.put("files",files);
		return result;
	}

}
