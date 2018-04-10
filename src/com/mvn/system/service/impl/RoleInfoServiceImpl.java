package com.mvn.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvn.system.model.DicRoleInfo;
import com.mvn.system.model.RoleInfo;
import com.mvn.system.service.RoleInfoService;
import com.mvn.system.service.dao.RoleInfoMapper;

@Service @Transactional
public class RoleInfoServiceImpl implements RoleInfoService {
	protected static Log log = LogFactory.getLog(RoleInfoServiceImpl.class);
	@Resource
	private RoleInfoMapper roleInfoMapper;
	@Override
	public List<RoleInfo> selectRoleInfoByList(RoleInfo model) {
		return roleInfoMapper.selectRoleInfoByList(model);
	}
	@Override
	public int selectRoleInfoByCount(RoleInfo model) {
		return roleInfoMapper.selectRoleInfoByCount(model);
	}
	@Override
	public int insertRoleInfo(RoleInfo model) {
		return roleInfoMapper.insertRoleInfo(model);
	}
	@Override
	public int updateRoleInfo(RoleInfo model) {
		return roleInfoMapper.updateRoleInfo(model);
	}
	@Override
	public RoleInfo selectRoleInfoByInfo(RoleInfo bean) {
		return roleInfoMapper.selectRoleInfoByInfo(bean);
	}
	
	@Override
	public void deleteDicRoleInfoByKey(DicRoleInfo bean) {
		roleInfoMapper.deleteDicRoleInfoByKey(bean);
	}
	@Override
	public void insertDicRoleInfo(DicRoleInfo bean) {
		roleInfoMapper.insertDicRoleInfo(bean);
	}
	@Override
	public List<DicRoleInfo> selectDicRoleInfo(DicRoleInfo model) {
		return roleInfoMapper.selectDicRoleInfo(model);
	}
	
}
