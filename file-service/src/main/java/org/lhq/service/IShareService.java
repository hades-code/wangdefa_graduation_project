package org.lhq.service;

import org.lhq.dao.ShareDao;
import org.lhq.common.Item;

import java.util.List;

public interface IShareService {
	ShareDao getShareDao();
	Object shareDirAndFile(List<Item> items, Long userId);
}
