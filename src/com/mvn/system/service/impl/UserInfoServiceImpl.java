package com.mvn.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvn.system.model.UserInfo;
import com.mvn.system.model.UserLoginInfo;
import com.mvn.system.model.UserRoleInfo;
import com.mvn.system.service.UserInfoService;
import com.mvn.system.service.dao.UserInfoMapper;
/**
 * 
 * @author Admin
 *
 */
@Service 
@Transactional(rollbackFor =  Exception.class)
public class UserInfoServiceImpl implements UserInfoService {
	protected static Log log = LogFactory.getLog(UserInfoServiceImpl.class);
	@Resource
	private UserInfoMapper userInfoMapper;

	@Override
	public UserInfo getUserInfo(UserInfo model) {
		return userInfoMapper.getUserInfo(model);
	}
	@Override
	public List<UserInfo> getUserInfoByInfo(UserInfo model) {
		return (List<UserInfo>) userInfoMapper.getUserInfoByInfo(model);
	}
	@Override
	public int getUserInfoByInfoCount(UserInfo model) {
		return userInfoMapper.getUserInfoByInfoCount(model);
	}
	@Override
	public void saveUserInfoData(UserInfo model) {
		userInfoMapper.saveUserInfoData(model);
	}
	@Override
	public void editUserInfoData(UserInfo model) {
		userInfoMapper.editUserInfoData(model);
		
	}
	
	
	
	
	
	@Override
	public UserLoginInfo getLoginUserInfo(String loginName) {
		return userInfoMapper.getLoginUserInfo(loginName);
	}
	@Override
	public void saveLoginUserInfoData(UserLoginInfo model) {
		userInfoMapper.saveLoginUserInfoData(model);		
	}
	@Override
	public void editLoginUserInfoData(UserLoginInfo model) {
		userInfoMapper.editLoginUserInfoData(model);		
	}
	@Override
	public UserInfo getUserInfoByInfoData(String userId) {
		UserInfo model = new UserInfo();
		model.setId(userId);
		List<UserInfo> userInfoByInfo = userInfoMapper.getUserInfoByInfo(model);
		return userInfoByInfo.get(0);
	}
	
	
	
	@Override
	public List<UserRoleInfo> selectByUserRoleInfo(UserRoleInfo bean) {
		return userInfoMapper.selectByUserRoleInfo(bean);
	}
	@Override
	public void deleteUserRoleByKey(UserRoleInfo bean) {
		userInfoMapper.deleteUserRoleByKey(bean);
	}
	@Override
	public void insertUserRoleInfo(UserRoleInfo bean) {
		userInfoMapper.insertUserRoleInfo(bean);
	}

}
