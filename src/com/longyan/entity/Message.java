package com.longyan.entity;

import java.util.Date;

/**
 * 用户留言
 * @author tracyqiu
 *
 */
public class Message {
	
	private int id;  //ID

	private int customer_id;   //会员ID
	
	private String customer_phone;  //会员电话
	
	private String customer_name; //会员名字
	
	private String content;  //留言内容
	
	private int status;   //留言是否已回复  0未回复  1已回复
	
	private Date ctime;       //创建时间
	
	private Date utime;       //更新时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_phone() {
		return customer_phone;
	}

	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
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
