package org.lhq.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.lhq.dao.DirectoryDao;
import org.lhq.common.Item;
import org.lhq.entity.Directory;
import org.lhq.entity.UserFile;
import org.lhq.service.DirectorySerivce;
import org.lhq.service.UserFileService;
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
		Directory directory = directoryDao.selectOne(new QueryWrapper<Directory>().lambda()
				.eq(Directory::getId, id)
				.eq(Directory::getUserId, userId));
		log.info("找到的目录为:{}",directory);
		HashMap<String, Object> map = new HashMap<>(16);
		map.put("id",directory.getId());
		if (directory.getParentId() == 0){
			map.put("name","根目录");
			list.add(map);
		}else {
			map.put("name",directory.getDirectoryName());
			list.add(map);
			getListPartDirectoryById(directory.getParentId(),userId,list);
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
		List<Directory> directoryList =this.directoryDao.selectList(new QueryWrapper<Directory>().lambda()
				.eq(Directory::getParentId,pid)
				.eq(Directory::getUserId,userId));
		for (Directory directory : directoryList) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("id",directory.getId());
			map.put("name",directory.getDirectoryName());
			map.put("type","dir");
			map.put("modifyTime", DateUtil.format(directory.getModifyTime(),"yyyy-MM-dd HH:mm"));
			dirc.add(map);
		}
		return dirc;
	}

	@Override
	public Boolean mkdir(String dirName, Long pid, Long userId) {
		if (pid == null){
			Directory directory = getDirByPid(0L, userId);
			pid = directory.getId();
		}
		List<Directory> directoryList =this.directoryDao.selectList(new QueryWrapper<Directory>().lambda()
				.eq(Directory::getDirectoryName,dirName)
				.eq(Directory::getParentId,pid)
				.eq(Directory::getUserId,userId));
		if (directoryList == null || directoryList.size() <= 0){
			Directory newDir = new Directory();
			newDir.setDirectoryName(dirName);
			newDir.setParentId(pid);
			newDir.setUserId(userId);
			newDir.setCreateTime(new Date());
			newDir.setModifyTime(new Date());
			directoryDao.insert(newDir);
			return true;
		}
		return false;
	}

	@Override
	public Directory getDirById(Long id){
		Directory directory = this.directoryDao.selectById(id);
		return directory;
	}
	@Override
	public Directory getDirByPid(Long id, Long userId){
		Directory dir = this.directoryDao.selectOne(new QueryWrapper<Directory>().lambda()
				.eq(Directory::getParentId,id)
				.eq(Directory::getUserId,userId));
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
	public Boolean copyDirAndFile(List<Item> list, Long targetId) {
		Long sourceId = 1L;
		Date date = new Date();
		//获取源文件夹
		Directory sourceDir = this.directoryDao.selectById(sourceId);
		sourceDir.setParentId(targetId);
		sourceDir.setModifyTime(date);
		//获取源目文件夹的子文件夹
		List<Directory> subDirs = this.directoryDao.selectList(new QueryWrapper<Directory>().lambda()
				.eq(Directory::getParentId,sourceId));
		//获取源目录下的文件
		List<UserFile> sourceFiles = this.userFileService.getUserFileDao().selectList(new QueryWrapper<UserFile>().lambda()
				.eq(UserFile::getDirectoryId,sourceId));
		for (UserFile sourceFile : sourceFiles) {
			sourceFile.setModifyTime(date);
			this.userFileService.getUserFileDao().updateById(sourceFile);
		}
		//遍历
		for (Directory subDir : subDirs) {
			copyDirAndFile(null,sourceDir.getId());
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
	public Boolean moveDirAndFile(List<Item> items,Long targetId){
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
			moveDirAndFile(null,targetId);
		}
		for (UserFile file : files) {
			userFileService.move(file.getId(),targetId);
		}
		return true;
	}

	@Override
	public List findDirByName(String name, Long userId) {
		Directory dirByName = this.directoryDao.selectOne(new QueryWrapper<Directory>().lambda()
				.eq(Directory::getDirectoryName,name)
				.eq(Directory::getUserId,userId));
		//TO-DO
		return null;
	}

	/**
	 *
	 * @param id
	 * @param list
	 * @return
	 */
	@Override
	public List listDir(Long id, List list){
		List<Directory> directoryList = this.directoryDao.selectList(new QueryWrapper<Directory>().lambda()
				.eq(Directory::getParentId, id));
		for (Directory directory : directoryList) {
			Map<String, Object> map = new HashMap<>();
			map.put("id",directory.getId());
			map.put("name",directory.getDirectoryName());
			List<Object> dirList = new ArrayList<>();
			listDir(directory.getId(),dirList);
			map.put("children",dirList);
			list.add(map);
		}
		return list;
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
					continue;
				}
				dirs.add(directory);
			}else {
				UserFile userFile = userFileService.getUserFileDao().selectById(item.getId());
				if (userFile == null){
					log.error("不存在此文件");
					continue;
				}
				files.add(userFile);
			}
		}
		result.put("dirs",dirs);
		result.put("files",files);
		return result;
	}

}
