package com.longyan.entity;

import java.util.Date;

/**
 * 模板
 * @author tracyqiu
 *
 */
public class Template {
	
	private int id;  //ID
	
	private int column_id;   //模板所属栏目ID
	
	private int content_id;  //模板所属内容列表ID

	private String filename;   //模板文件名（不包含路径）
	
	private String path;  //模板文件路径，包含文件名
	
	private String create_user;  //创建人
	
	private Date ctime;       //创建时间
	
	private Date utime;       //更新时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getColumn_id() {
		return column_id;
	}

	public void setColumn_id(int column_id) {
		this.column_id = column_id;
	}

	public int getContent_id() {
		return content_id;
	}

	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}

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
