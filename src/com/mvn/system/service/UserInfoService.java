package com.mvn.system.service;


import java.util.List;

import com.mvn.system.model.UserInfo;
import com.mvn.system.model.UserLoginInfo;
import com.mvn.system.model.UserRoleInfo;

/**
 * 
 * @author Admin
 *
 */
public interface UserInfoService {
	/**
	 * 获取用户登录信息
	 * @param LoginName
	 * @return
	 */
	public UserInfo getUserInfo(UserInfo model);
	public List<UserInfo> getUserInfoByInfo(UserInfo model);
	public int getUserInfoByInfoCount(UserInfo model);
	public void saveUserInfoData(UserInfo model);
	public void editUserInfoData(UserInfo model);
	public UserInfo getUserInfoByInfoData(String userId);
	
	
	/**
	 * 获取登录信息
	 * @param loginName 用户名称
	 * @return
	 */
	public UserLoginInfo getLoginUserInfo(String loginName);
	public void saveLoginUserInfoData(UserLoginInfo model);
	public void editLoginUserInfoData(UserLoginInfo model);


	public List<UserRoleInfo> selectByUserRoleInfo(UserRoleInfo bean);
	public void deleteUserRoleByKey(UserRoleInfo bean);
	public void insertUserRoleInfo(UserRoleInfo bean);

}
