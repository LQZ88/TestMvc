package com.mvn.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvn.system.model.TreeModel;
import com.mvn.system.service.TreeInfoService;
import com.mvn.system.service.dao.TreeInfoMapper;

@Service @Transactional
public class TreeInfoServiceImpl implements TreeInfoService {
	protected static Log log = LogFactory.getLog(TreeInfoServiceImpl.class);
	@Resource
	private TreeInfoMapper treeInfoMapper;
	
	@Override
	public List<TreeModel> getAllDicInfo() {
		return treeInfoMapper.getAllDicInfo();
	}

	@Override
	public List<TreeModel> getUserDicInfo(String userId) {
		return treeInfoMapper.getUserDicInfo(userId);
	}
	
}
