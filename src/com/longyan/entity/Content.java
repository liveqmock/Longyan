package com.longyan.entity;

import java.util.Date;

/**
 * 栏目下面的内容列表
 * @author tracyqiu
 *
 */
public class Content {

	private String title;   //内容名称
	
	private String code;  //栏目代码
	
	private int template_id;   //没有二级内容的栏目会对应一个模板文件
	
	private int column_id;    //所属栏目
	
	private String create_user;  //创建人
	
	private Date ctime;       //创建时间
	
	private Date utime;       //更新时间

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(int template_id) {
		this.template_id = template_id;
	}

	public int getColumn_id() {
		return column_id;
	}

	public void setColumn_id(int column_id) {
		this.column_id = column_id;
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
