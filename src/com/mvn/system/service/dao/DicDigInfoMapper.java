package com.mvn.system.service.dao;

import java.util.List;

import com.mvn.system.model.DicDigInfo;
import com.mvn.utils.MyBatisRepository;
@MyBatisRepository
public interface DicDigInfoMapper {
    /*int deleteByPrimaryKey(Integer id);

    int insert(DicDigInfo record);

    int insertSelective(DicDigInfo record);

    DicDigInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DicDigInfo record);

    int updateByPrimaryKey(DicDigInfo record);*/
	
	public List<DicDigInfo> findDicdigByTypeAlias(String types);
}