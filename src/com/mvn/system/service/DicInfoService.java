package com.mvn.system.service;

import java.util.HashMap;
import java.util.List;

import com.mvn.system.model.DicInfo;

public interface DicInfoService {
	/**
	 * 添加菜单资源
	 * @param model
	 * @return
	 */
	public int saveDicInfo(DicInfo model);
	/**
	 * 修改菜单资源
	 * @param model
	 * @return
	 */
	public int updateDicInfo(DicInfo model);
    /**
     * 删除菜单资源
     * @param id
     * @return
     */
	public int deleteDicInfo(Integer id);
    /**
     * 获取菜单资源数据根据id
     * @param id
     * @return
     */
	public DicInfo getDicInfoById(Integer id);
    /**
     * 获取所有菜单资源数据
     * @return
     */
	public List<DicInfo> getDicInfoByAll();
	/**
	 * 根据父级菜单id获取菜单数据
	 * @param id
	 * @return
	 */
	public List<DicInfo> getDicInfoNextById(Integer id);
	/**
	 * 根据资源编号获取数据
	 * @param dicCode
	 * @return
	 */
	public DicInfo getdicInfoCode(String dicCode);
	/**
	 * 获取所有菜单资源数据的id和name值数组
	 * @return
	 */
	public List<HashMap<String, Object>> findDicInfoByNameJson();

}