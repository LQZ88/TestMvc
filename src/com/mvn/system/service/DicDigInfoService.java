package com.mvn.system.service;

import java.util.List;

import com.mvn.system.model.DicDigInfo;
/**
 * 
 * @author Admin
 *
 */
public interface DicDigInfoService {
    /*int deleteByPrimaryKey(Integer id);

    int insert(DicDigInfo record);

    int insertSelective(DicDigInfo record);

    DicDigInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DicDigInfo record);

    int updateByPrimaryKey(DicDigInfo record);*/
    /**
     * 获取数字字典数据
     * @param types类型名称
     * @return
     */
    public List<DicDigInfo> findDicdigByTypeAlias(String types);
}