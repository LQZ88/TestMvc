package com.mvn.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvn.system.model.FileInfo;
import com.mvn.system.service.FileInfoService;
import com.mvn.system.service.dao.FileInfoMapper;

@Service @Transactional
public class FileInfoServiceImpl implements FileInfoService {
	protected static Log log = LogFactory.getLog(FileInfoServiceImpl.class);
	@Resource
	private FileInfoMapper fileInfoMapper;
	@Override
	public List<FileInfo> getFileInfoList() {
		return fileInfoMapper.getFileInfoList();
	}
	@Override
	public FileInfo selectByFileInfo(FileInfo info) {
		return fileInfoMapper.selectByFileInfo(info);
	}
	@Override
	public int deleteFileInfo(String id) {
		return fileInfoMapper.deleteFileInfo(id);
	}
	@Override
	public int saveFileInfo(FileInfo info) {
		return fileInfoMapper.saveFileInfo(info);
	}
	@Override
	public int updateFileInfo(FileInfo info) {
		return fileInfoMapper.updateFileInfo(info);
	}

}
