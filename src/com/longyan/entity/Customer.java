package com.longyan.entity;

import java.util.Date;

/**
 * 会员
 * @author tracyqiu
 *
 */
public class Customer {
	
	private String username;  //会员名称
	
	private String realname;  //会员真实姓名
	
	private String password;  //登录密码
	
	private String telephone; //电话号码
	
	private String sex;       //性别
	
	private String birthday;  //生日
	
	private String address;   //地址
	
	private String qq;        //qq
	
	private Date ctime;       //创建时间
	
	private Date utime;       //更新时间

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
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
