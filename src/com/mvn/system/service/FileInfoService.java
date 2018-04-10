package com.mvn.system.service;


import java.util.List;

import com.mvn.system.model.FileInfo;


public interface FileInfoService {
	
	public List<FileInfo> getFileInfoList();
	
	public FileInfo selectByFileInfo(FileInfo info);

	public int deleteFileInfo(String id);

	public int saveFileInfo(FileInfo info);

	public int updateFileInfo(FileInfo info);
}
