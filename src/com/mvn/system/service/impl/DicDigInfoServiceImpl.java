package com.mvn.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvn.system.model.DicDigInfo;
import com.mvn.system.service.DicDigInfoService;
import com.mvn.system.service.dao.DicDigInfoMapper;
/**
 * 
 * @author Admin
 *
 */
@Service
@Transactional(rollbackFor =  Exception.class)
public class DicDigInfoServiceImpl implements DicDigInfoService {
	protected static Log log = LogFactory.getLog(DicDigInfoServiceImpl.class);
	@Resource
	private DicDigInfoMapper dicDigInfoMapper;
	@Override
	public List<DicDigInfo> findDicdigByTypeAlias(String types) {
		return dicDigInfoMapper.findDicdigByTypeAlias(types);
	}

}
