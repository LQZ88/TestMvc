package com.mvn.system.service;


import java.util.List;

import com.mvn.system.model.TreeModel;


public interface TreeInfoService {
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
