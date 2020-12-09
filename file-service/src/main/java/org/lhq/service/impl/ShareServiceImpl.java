package org.lhq.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lhq.dao.ShareDao;
import org.lhq.dao.VirtualAddressDao;
import org.lhq.common.Item;
import org.lhq.entity.Directory;
import org.lhq.entity.Share;
import org.lhq.entity.UserFile;
import org.lhq.entity.VirtualAddress;
import org.lhq.service.DirectorySerivce;
import org.lhq.service.IShareService;
import org.lhq.service.UserFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author hades
 */
@Service
@Slf4j
public class ShareServiceImpl implements IShareService {
	@Resource
	private ShareDao shareDao;
	@Resource
	private DirectorySerivce directorySerivce;
	@Resource
	private UserFileService userFileService;
	private VirtualAddressDao virtualAddressDao;
	@Override
	public ShareDao getShareDao(){
		return this.shareDao;
	}

	@Override
	public Object shareDirAndFile(@RequestBody List<Item> items, Long userId) {
		Date date = new Date();
		Map common = this.common(items);
		//获取文件夹
		List<Directory> dirs = MapUtil.get(common, "dirs", List.class);
		//获取文件
		List<UserFile> files = MapUtil.get(common, "files", List.class);
		for (Directory dir : dirs) {
			VirtualAddress virtualAddress = new VirtualAddress();
			virtualAddress.setCreateTime(date);
			virtualAddress.setDirOrFileId(dir.getId());
			virtualAddress.setUserId(userId);
			virtualAddress.setBooleanFile(false);
			virtualAddress.setName(dir.getDirectoryName());
			int insert = virtualAddressDao.insert(virtualAddress);
			log.info(String.valueOf(insert));
		}
		for (UserFile file : files) {
			VirtualAddress virtualAddress = new VirtualAddress();
			virtualAddress.setBooleanFile(true);
			virtualAddress.setName(file.getFileName());
			virtualAddress.setDirOrFileId(file.getId());
			virtualAddress.setCreateTime(date);
			virtualAddress.setUserId(userId);
			int insert = virtualAddressDao.insert(virtualAddress);
			log.info(String.valueOf(insert));
		}
		Share share = new Share();
		share.setCreateTime(date);
		share.setUserId(userId);
		share.setMultiFile(true);
		share.setNeedCode(true);
		share.setShareCode("1234");
		share.setShareLink(IdUtil.fastSimpleUUID());
		shareDao.insert(share);

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
				Directory directory = this.directorySerivce.getDirById(item.getId());
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
