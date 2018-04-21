package com.mvn.system.service;


import java.util.List;

import com.mvn.system.model.FileInfo;

/**
 * 
 * @author Admin
 *
 */
public interface FileInfoService {
	/**
	 * 获取附件集合
	 * @return
	 */
	public List<FileInfo> getFileInfoList();
	/**
	 * 根据附件信息查询
	 * @param info
	 * @return
	 */
	public FileInfo selectByFileInfo(FileInfo info);
	/**
	 * 根据附件id删除
	 * @param id
	 * @return
	 */
	public int deleteFileInfo(String id);
	/**
	 * 添加附件
	 * @param info
	 * @return
	 */
	public int saveFileInfo(FileInfo info);
	/**
	 * 修改附件
	 * @param info
	 * @return
	 */
	public int updateFileInfo(FileInfo info);
}
