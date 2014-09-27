package com.longyan.entity;

import java.util.Date;

/**
 * 附件 
 * @author tracyqiu
 *
 */
public class Attachments {

	private String filename;   //附件文件名（不包含路径）
	
	private String path;  //附件文件路径，包含文件名
	
	private int column_id; //附件所属栏目
	
	private String create_user;  //创建人
	
	private Date ctime;       //创建时间
	
	private Date utime;       //更新时间

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getColumn_id() {
		return column_id;
	}

	public void setColumn_id(int column_id) {
		this.column_id = column_id;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}
}
