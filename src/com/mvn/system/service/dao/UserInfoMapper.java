package com.mvn.system.service.dao;


import java.util.List;

import com.mvn.system.model.UserInfo;
import com.mvn.system.model.UserLoginInfo;
import com.mvn.system.model.UserRoleInfo;
import com.mvn.utils.MyBatisRepository;
/**
 * 
 * @author Admin
 *
 */
@MyBatisRepository
public interface UserInfoMapper {
	/**
	 * 获取登陆信息
	 * @param LoginName
	 * @return
	 */
	public UserInfo getUserInfo(UserInfo model);
	public List<UserInfo> getUserInfoByInfo(UserInfo model);
	public int getUserInfoByInfoCount(UserInfo model);
	public void saveUserInfoData(UserInfo model);
	public void editUserInfoData(UserInfo model);
	
	
	/**
	 * 获取登陆信息
	 * @param loginName
	 * @return
	 */
	public UserLoginInfo getLoginUserInfo(String loginName);
	public void saveLoginUserInfoData(UserLoginInfo model);
	public void editLoginUserInfoData(UserLoginInfo model);
	
	
	public List<UserRoleInfo> selectByUserRoleInfo(UserRoleInfo bean);
	public void deleteUserRoleByKey(UserRoleInfo bean);
	public void insertUserRoleInfo(UserRoleInfo bean);

}
