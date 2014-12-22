package com.longyan.entity;

import java.util.Date;

/**
 * 跟帖
 * @author tracyqiu
 *
 */
public class BbsReply {

	private int id;
	
	private String content;  //回复内容
	
	private int cutomer_id; 
	
	private int bbs_id;
	
	private int status; //跟帖状态  1： 正常   2：被屏蔽
	
	private Date ctime;
	
	private Date utime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCutomer_id() {
		return cutomer_id;
	}

	public void setCutomer_id(int cutomer_id) {
		this.cutomer_id = cutomer_id;
	}

	public int getBbs_id() {
		return bbs_id;
	}

	public void setBbs_id(int bbs_id) {
		this.bbs_id = bbs_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
