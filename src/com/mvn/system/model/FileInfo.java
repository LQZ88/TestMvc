package com.mvn.system.model;
/**
 * 
 * @author Admin
 *
 */
public class FileInfo {
	private String id;

    private String fileid;

    private String filepath;

    private byte[] filetext;
    
    private String filetexts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid == null ? null : fileid.trim();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public byte[] getFiletext() {
        return filetext;
    }

    public void setFiletext(byte[] filetext) {
        this.filetext = filetext;
    }
    public String getFiletexts() {
        return filetexts;
    }

    public void setFiletexts(String filetexts) {
        this.filetexts = filetexts;
    }
}
