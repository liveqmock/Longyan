package com.longyan.entity;

import java.util.Date;

/**
 * 邮件记录 邮件服务既可以给内部员工群发短信，也可以给会员群发，所以员工ID和会员ID不能都为空
 * @author tracyqiu
 *
 */
public class MailSendLog {
	
	private int id;  //ID
	
	private int employee_id;   //员工ID
	
	private int customer_id;  //会员ID
	
	private String recieve_name;  //收信人名称
	
	private String phone_number;  //收信人电话
	
	private String content; //邮件内容
	
	private String send_name;  //发信人名称
	
	private int type;   //邮件类型  1 验证类型， 0 普通类型
	
	private Date ctime;       //创建时间
	
	private Date utime;       //更新时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getRecieve_name() {
		return recieve_name;
	}

	public void setRecieve_name(String recieve_name) {
		this.recieve_name = recieve_name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSend_name() {
		return send_name;
	}

	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
