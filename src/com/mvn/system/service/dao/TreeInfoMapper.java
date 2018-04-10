package com.mvn.system.service.dao;

import java.util.List;

import com.mvn.system.model.TreeModel;
import com.mvn.utils.MyBatisRepository;
@MyBatisRepository
public interface TreeInfoMapper {
	/**
	 * 查询所有资源
	 * @return
	 */
	public List<TreeModel> getAllDicInfo();
	/**
	 * 查询用户资源
	 * @return
	 */
	public List<TreeModel> getUserDicInfo(String userId);
}
