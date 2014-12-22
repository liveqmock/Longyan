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
	
	private int type;   // 1: 健康养生   2：活动专区
	
	private int cutomer_id; //发帖用户ID  -1：管理员  其他：会员
	
	private int is_customer; //是否是管理员发帖  0:否 1：是
	
	private int view_count;  //浏览量
	
	private int reply_count; //回复数量
	
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCutomer_id() {
		return cutomer_id;
	}

	public void setCutomer_id(int cutomer_id) {
		this.cutomer_id = cutomer_id;
	}

	public int getIs_customer() {
		return is_customer;
	}

	public void setIs_customer(int is_customer) {
		this.is_customer = is_customer;
	}

	public int getView_count() {
		return view_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;
	}

	public int getReply_count() {
		return reply_count;
	}

	public void setReply_count(int reply_count) {
		this.reply_count = reply_count;
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
