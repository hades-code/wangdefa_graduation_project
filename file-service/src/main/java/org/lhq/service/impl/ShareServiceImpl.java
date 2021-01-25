package org.lhq.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.lhq.dao.ShareDao;
import org.lhq.dao.ShareFileDao;
import org.lhq.entity.Directory;
import org.lhq.entity.Share;
import org.lhq.entity.ShareFile;
import org.lhq.entity.UserFile;
import org.lhq.entity.vo.Item;
import org.lhq.entity.vo.ShareVO;
import org.lhq.exception.ProjectException;
import org.lhq.service.DirectoryService;
import org.lhq.service.IShareService;
import org.lhq.service.UserFileService;
import org.lhq.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hades
 */
@Service
@Slf4j
public class ShareServiceImpl implements IShareService {
	@Resource
	private ShareDao shareDao;
	@Resource
	private DirectoryService directoryService;
	@Resource
	private UserFileService userFileService;
	@Resource
	private ShareFileDao shareFileDao;

	@Override
	public ShareDao getShareDao() {
		return this.shareDao;
	}

	@Override
	@Transactional
	public Map<String, Object> shareDirAndFile(List<Item> items, Long userId, Boolean shareLock, String shareCode, Integer expirationTime) {
		HashMap<String, Object> result = new HashMap<>(16);
		LocalDateTime date = LocalDateTime.now();
		Share share = new Share().setShareLink(IdUtil.fastSimpleUUID());
		share.setCreateTime(date);
		share.setUserId(userId);
		share.setShareLock(shareLock);
		if (shareLock) {
			share.setShareCode(shareCode);
		}
		if (expirationTime != null && expirationTime > 0) {
			LocalDateTime expDate = LocalDateTimeUtil.offset(date, expirationTime, ChronoUnit.DAYS);
			share.setExpirationTime(expDate);
			result.put("liveTime", expirationTime + "天");
		}
		shareDao.insert(share);
		for (Item item : items) {
			ShareFile shareFile = new ShareFile();
			if (StrUtil.equals(item.getType(), "dir")) {
				shareFile.setFileOrDir(false);
			}
			shareFile.setFileOrDir(true);
			shareFile.setFileId(item.getId());
			shareFile.setShareId(share.getShareLink());
			shareFileDao.insert(shareFile);
		}
		result.put("shareLink", share.getShareLink());
		result.put("shareCode", share.getShareCode());
		return result;
	}

	@Override
	public Boolean needShareCode(String shareLink) {
		Share share = this.shareDao.selectOne(new LambdaQueryWrapper<Share>()
				.select(Share::getShareLock)
				.eq(Share::getShareLink, shareLink));
		if (share!=null && share.getShareLock()!=null){
			return share.getShareLock();
		}else {
			return false;
		}
	}

	@Override
	public Map<String, Object> getShare(String shareLink, String shareCode) throws ProjectException {
		Date date = new Date();
		HashMap<String, Object> result = new HashMap<>();
		Share share = new Share().setShareLink(shareLink);
		Share getShare = this.shareDao.selectOne(new QueryWrapper<>(share));
		if (getShare == null) {
			log.error("分享不存在或者已被取消");
			throw new ProjectException("分享不存在或者已被取消");
		}
		Date createTime = DateUtils.asDate(getShare.getCreateTime());
		Date expirationTime = DateUtils.asDate(getShare.getExpirationTime());
		if (createTime != null && expirationTime != null && !DateUtil.isIn(date, createTime, expirationTime)) {
			log.error("分享文件已经过期");
			throw new ProjectException("分享文件已经过期");
		}
		if (getShare.getShareLock() && !StrUtil.equals(getShare.getShareCode(), shareCode)) {
			log.error("提取码错误");
			throw new ProjectException("提取码错误");
		}

		ShareFile shareFile = new ShareFile();
		shareFile.setShareId(getShare.getShareLink());
		List<ShareFile> shareFiles = shareFileDao.selectList(new QueryWrapper<ShareFile>().lambda()
				.eq(ShareFile::getShareId, getShare.getShareLink()));
		List<Long> fileIdList = shareFiles.stream()
				.filter(ShareFile::getFileOrDir)
				.map(ShareFile::getFileId)
				.collect(Collectors.toList());
		if (!fileIdList.isEmpty()) {
			List<UserFile> fileList = userFileService.getUserFileDao().selectBatchIds(fileIdList);
			result.put("files", fileList);
		}
		List<Long> dirIdList = shareFiles.stream()
				.filter(sF -> !sF.getFileOrDir())
				.map(ShareFile::getFileId)
				.collect(Collectors.toList());
		if (!dirIdList.isEmpty()) {
			List<Directory> directoryList = directoryService.getDirectoryDao().selectBatchIds(dirIdList);
			result.put("dirs", directoryList);
		}
		return result;
	}
	@Override
	public ShareVO getShareVO(Long id){
		Share share = this.shareDao.selectById(id);
		String shareLink = share.getShareLink();
		List<ShareFile> shareFiles = this.shareFileDao.selectList(new QueryWrapper<ShareFile>().lambda().eq(ShareFile::getShareId, shareLink));
		ShareVO shareVO = new ShareVO();
		HashSet<UserFile> userFiles = new HashSet<>();
		HashSet<Directory> directories = new HashSet<>();
		shareFiles.forEach(shareFile -> {
			if (shareFile.getFileOrDir()){
				UserFile userFile = this.userFileService.getUserFileDao().selectById(shareFile.getFileId());
				userFiles.add(userFile);
			}else {
				Directory directory = this.directoryService.getDirectoryDao().selectById(shareFile.getFileId());
				directories.add(directory);
			}
		});
		shareVO.setShare(share);
		shareVO.setUserFiles(userFiles);
		shareVO.setDirectories(directories);
		return shareVO;
	}

	@Override
	public Boolean confirmShareCode(String shareLink, String shareCode) throws ProjectException {
		Share share = this.shareDao.selectOne(new LambdaQueryWrapper<Share>().eq(Share::getShareLink, shareLink));
		if(share == null){
			throw new ProjectException("此分享不存在或者被取消");
		}
		return StrUtil.equals(shareCode, share.getShareCode());
	}

	@Override
	public List getShareDirTree(String shareLink) {
		Share share = this.shareDao.selectOne(new LambdaQueryWrapper<Share>().eq(Share::getShareLink, shareLink));
		List<ShareFile> shareFileList = this.shareFileDao.selectList(new LambdaQueryWrapper<ShareFile>().eq(ShareFile::getShareId, shareLink));
		List<Long> dirList = shareFileList.stream().filter(shareFile -> !shareFile.getFileOrDir()).map(ShareFile::getFileId).collect(Collectors.toList());
		return null;
	}

}
