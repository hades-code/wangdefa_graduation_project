package org.lhq.service;

import org.lhq.dao.ShareDao;
import org.lhq.common.Item;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IShareService {
	ShareDao getShareDao();
	Object shareDirAndFile(List<Item> items, Long userId, Boolean shareLock, Date expirationTime);
	Map getShare(String shareLink, String shareCode);
}
