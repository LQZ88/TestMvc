package com.mvn.system.service.dao;

import java.util.HashMap;
import java.util.List;

import com.mvn.system.model.DicInfo;
import com.mvn.utils.MyBatisRepository;
/***
 * 
 * @author Admin
 *
 */
@MyBatisRepository
public interface DicInfoMapper {

	public int saveDicInfo(DicInfo model);

	public int updateDicInfo(DicInfo model);
    
	public int deleteDicInfo(Integer id);
    
	public DicInfo getDicInfoById(Integer id);
    
	public List<DicInfo> getDicInfoByAll();
	
	public List<DicInfo> getDicInfoNextById(Integer id);

	public DicInfo getdicInfoCode(String dicCode);

	public List<HashMap<String, Object>> findDicInfoByNameJson();

}