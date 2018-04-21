package com.mvn.system.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvn.system.model.DicInfo;
import com.mvn.system.service.DicInfoService;
import com.mvn.system.service.dao.DicInfoMapper;
/**
 * 
 * @author Admin
 *
 */
@Service 
@Transactional(rollbackFor =  Exception.class)
public class DicInfoServiceImpl implements DicInfoService {
	protected static Log log = LogFactory.getLog(DicInfoServiceImpl.class);
	@Resource
	private DicInfoMapper dicInfoMapper;
	@Override
	public int saveDicInfo(DicInfo model) {
		return dicInfoMapper.saveDicInfo(model);
	}

	@Override
	public int updateDicInfo(DicInfo model) {
		return dicInfoMapper.updateDicInfo(model);
	}

	@Override
	public int deleteDicInfo(Integer id) {
		return dicInfoMapper.deleteDicInfo(id);
	}

	@Override
	public DicInfo getDicInfoById(Integer id) {
		return dicInfoMapper.getDicInfoById(id);
	}

	@Override
	public List<DicInfo> getDicInfoByAll() {
		return dicInfoMapper.getDicInfoByAll();
	}

	@Override
	public List<DicInfo> getDicInfoNextById(Integer id) {
		return dicInfoMapper.getDicInfoNextById(id);
	}

	@Override
	public DicInfo getdicInfoCode(String code) {
		return dicInfoMapper.getdicInfoCode(code);
	}

	@Override
	public List<HashMap<String, Object>> findDicInfoByNameJson() {
		return dicInfoMapper.findDicInfoByNameJson();
	}


}
