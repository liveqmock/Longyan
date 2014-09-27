package com.longyan.entity;

import java.util.Date;

/**
 * 留言回复
 * @author tracyqiu
 *
 */
public class Reply {
	
	private int message_id;   //留言ID
	
	private String reply_content;  //回复内容
	
	private int employee_name; //回复员工名字
	
	private Date ctime;       //创建时间
	
	private Date utime;       //更新时间

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

	public int getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(int employee_name) {
		this.employee_name = employee_name;
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
