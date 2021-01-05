package org.lhq.service;

import org.lhq.common.Item;
import org.lhq.dao.ShareDao;
import org.lhq.exception.ProjectException;

import java.util.List;
import java.util.Map;

public interface IShareService {
	ShareDao getShareDao();

	Object shareDirAndFile(List<Item> items, Long userId, Boolean shareLock, String shareCode, Integer expirationTime);

	Map getShare(String shareLink, String shareCode) throws ProjectException;
}
