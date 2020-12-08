package org.lhq.service.impl;

import org.lhq.dao.ShareDao;
import org.lhq.gp.product.entity.Share;
import org.lhq.service.IShareService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hades
 */
@Service
public class ShareServiceImpl implements IShareService {
	@Resource
	private ShareDao shareDao;
	@Override
	public ShareDao getShareDao(){
		return this.shareDao;
	}
}
