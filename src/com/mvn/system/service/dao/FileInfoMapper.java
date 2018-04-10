package com.mvn.system.service.dao;

import java.util.List;

import com.mvn.system.model.FileInfo;
import com.mvn.utils.MyBatisRepository;
@MyBatisRepository
public interface FileInfoMapper {
	public List<FileInfo> getFileInfoList();
	
	public FileInfo selectByFileInfo(FileInfo info);

	public int deleteFileInfo(String id);

	public int saveFileInfo(FileInfo info);

	public int updateFileInfo(FileInfo info);
}