package com.longyan.entity;

import java.util.Date;

/**
 * 帖子
 * @author tracyqiu
 *
 */
public class Bbs {

	private int id; 
	
	private String title;   //标题
	 
	private String content; //内容
	
	private int status;  //1：待审核  2：审核通过 3：审核不通过 4：已封帖 5：被屏蔽
	
	private int cutomer_id; //发帖用户ID  -1：管理员  其他：会员
	
	private Date ctime;
	
	private Date utime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCutomer_id() {
		return cutomer_id;
	}

	public void setCutomer_id(int cutomer_id) {
		this.cutomer_id = cutomer_id;
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
