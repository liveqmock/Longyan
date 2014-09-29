package com.longyan.entity;

import java.util.Date;

/**
 * 客服信息
 * @author tracyqiu
 *
 */
public class Contact {
	
	private int id;  //ID

	private String qq;   //qq
	
	private String name;  //客服名字
	
	private String telephone;  //客服电话
	
	private Date ctime;       //创建时间
	
	private Date utime;       //更新时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
