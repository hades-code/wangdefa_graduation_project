package org.lhq.service;

import org.lhq.entity.vo.Item;
import org.lhq.dao.ShareDao;
import org.lhq.entity.vo.ShareVO;
import org.lhq.exception.ProjectException;

import java.util.List;
import java.util.Map;

public interface IShareService {
	ShareDao getShareDao();

	/**
	 * 分享文件和目录
	 * @param items
	 * @param userId
	 * @param shareLock
	 * @param shareCode
	 * @param expirationTime
	 * @return
	 */
	Object shareDirAndFile(List<Item> items, Long userId, Boolean shareLock, String shareCode, Integer expirationTime);

	/**
	 * 返回是否需要提取码
	 * @param shareCode
	 * @return
	 */
	Boolean needShareCode(String shareCode);

	/**
	 * 获取分享文件
	 * @param shareLink
	 * @param shareCode
	 * @return
	 * @throws ProjectException
	 */
	Map getShare(String shareLink, String shareCode) throws ProjectException;

	/**
	 * 管理界面获取分享结构
	 * @param id
	 * @return
	 */
	ShareVO getShareVO(Long id);

	/**
	 * 确认分享码是否正确
	 * @param shareLink
	 * @param shareCode
	 * @return
	 * @throws ProjectException
	 */
	Boolean confirmShareCode(String shareLink,String shareCode) throws ProjectException;
}
