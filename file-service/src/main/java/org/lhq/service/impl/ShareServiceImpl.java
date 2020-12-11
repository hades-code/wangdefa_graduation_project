package org.lhq.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.lhq.exception.ProjectException;
import org.lhq.dao.ShareDao;
import org.lhq.dao.ShareFileDao;
import org.lhq.common.Item;
import org.lhq.entity.*;
import org.lhq.service.DirectorySerivce;
import org.lhq.service.IShareService;
import org.lhq.service.UserFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

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
	@Resource
	private ShareFileDao shareFileDao;
	@Override
	public ShareDao getShareDao(){
		return this.shareDao;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Map<String,Object> shareDirAndFile(@RequestBody List<Item> items, Long userId,Boolean shareLock,Date expirationTime) {
		HashMap<Object, Object> result = new HashMap<>();
		Date date = new Date();
		Share share = new Share().setShareLink(IdUtil.fastSimpleUUID());
		share.setCreateTime(date);
		share.setExpirationTime(expirationTime);
		share.setShareLock(shareLock);
		shareDao.insert(share);
		for (Item item :items){
			ShareFile shareFile = new ShareFile();
			if (StrUtil.equals(item.getType(),"dir")){
				shareFile.setFileOrDir(false);
			}
			shareFile.setFileId(item.getId());
			shareFile.setShareId(share.getShareLink());
			shareFileDao.insert(shareFile);
		}
		result.put("shareLink",share.getShareLink());
		result.put("shareCode",share.getShareCode());
		return null;
 	}

	@Override
	public Map<String,Object> getShare(String shareLink, String shareCode) throws ProjectException {
		Date date = new Date();
		HashMap<String, Object> result = new HashMap<>();
		Share share = new Share().setShareLink(shareLink);
		Share getShare = this.shareDao.selectOne(new QueryWrapper<>(share));
		if (getShare == null){
			log.error("分享不存在或者已被取消");
			throw new ProjectException("分享不存在或者已被取消");
		}
		if(!DateUtil.isIn(date,getShare.getCreateTime(),getShare.getExpirationTime())){
			log.error("分享文件已经过期");
			throw new ProjectException("分享文件已经过期");
		}
		if(getShare.getShareLock() && !StrUtil.equals(getShare.getShareCode(),shareCode)){
			log.error("提取码错误");
			throw new ProjectException("提取码错误");
		}

		ShareFile shareFile = new ShareFile();
		shareFile.setShareId(getShare.getShareLink());
		List<ShareFile> shareFiles = shareFileDao.selectList(new QueryWrapper<>(shareFile));
		List<Long> fileIdList = shareFiles.stream()
				.filter(ShareFile::getFileOrDir)
				.map(ShareFile::getFileId)
				.collect(Collectors.toList());
		if (!fileIdList.isEmpty()){
			List<UserFile> fileList = userFileService.getUserFileDao().selectBatchIds(fileIdList);
			result.put("files",fileList);
		}
		List<Long> dirIdList = shareFiles.stream()
				.filter(sF -> !sF.getFileOrDir())
				.map(ShareFile::getFileId)
				.collect(Collectors.toList());
		if (!dirIdList.isEmpty()){
			List<Directory> directoryList = directorySerivce.getDirectoryDao().selectBatchIds(dirIdList);
			result.put("dirs",directoryList);
		}
		return result;
	}

}
