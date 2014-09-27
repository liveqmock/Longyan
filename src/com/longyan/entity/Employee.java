package com.longyan.entity;

import java.util.Date;

/**
 * 员工
 * @author tracyqiu
 *
 */
public class Employee {
	
	private String name;  //员工名称
	
	private String password;  //登录密码
	
	private String telephone; //电话号码
	
	private String id_card;   //身份证号码
	
	private String sex;       //性别
	
	private String birthday;  //生日
	
	private String address;   //地址
	
	private String qq;        //qq
	
	private String province;  //籍贯
	
	private int right_level = 1; //权限代号
	
	private Date ctime;       //创建时间
	
	private Date utime;       //更新时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public int getRight_level() {
		return right_level;
	}

	public void setRight_level(int right_level) {
		this.right_level = right_level;
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
