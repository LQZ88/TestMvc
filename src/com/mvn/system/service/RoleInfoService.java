package com.mvn.system.service;


import java.util.List;

import com.mvn.system.model.DicRoleInfo;
import com.mvn.system.model.RoleInfo;

/**
 * 
 * @author Admin
 *
 */
public interface RoleInfoService {
	
	public List<RoleInfo> selectRoleInfoByList(RoleInfo model);

	public int selectRoleInfoByCount(RoleInfo model);
    
	public int insertRoleInfo(RoleInfo model);
    
	public int updateRoleInfo(RoleInfo model);

	public RoleInfo selectRoleInfoByInfo(RoleInfo bean);
	
	public void deleteDicRoleInfoByKey(DicRoleInfo bean);

	public void insertDicRoleInfo(DicRoleInfo bean);

	public List<DicRoleInfo> selectDicRoleInfo(DicRoleInfo model);
}
