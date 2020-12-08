package org.lhq.service.impl;

import org.lhq.dao.VirtualAddressDao;
import org.lhq.service.IVirtualAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VirtualAddressServiceImpl implements IVirtualAddressService {
	@Resource
	private VirtualAddressDao virtualAddressDao;
	@Override
	public VirtualAddressDao getVirtualAddressDao(){
		return this.virtualAddressDao;
	}
}
