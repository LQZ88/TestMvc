package com.mvn.system.service.dao;

import java.util.List;

import com.mvn.system.model.DicRoleInfo;
import com.mvn.system.model.RoleInfo;
import com.mvn.utils.MyBatisRepository;
/**
 * 
 * @author Admin
 *
 */
@MyBatisRepository
public interface RoleInfoMapper {

	public List<RoleInfo> selectRoleInfoByList(RoleInfo model);

	public int selectRoleInfoByCount(RoleInfo model);
    
	public int insertRoleInfo(RoleInfo model);
    
	public int updateRoleInfo(RoleInfo model);

	public RoleInfo selectRoleInfoByInfo(RoleInfo bean);
	
	public void deleteDicRoleInfoByKey(DicRoleInfo bean);

	public void insertDicRoleInfo(DicRoleInfo bean);
	
	public List<DicRoleInfo> selectDicRoleInfo(DicRoleInfo model);
}