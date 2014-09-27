package com.longyan.entity;

import java.util.Date;

/**
 * 权限   针对系统设置里面的权限
 * @author tracyqiu
 *
 */
public class Permission {

	private int employee_id;  //员工ID
	
	private String column_ids; //有权限的栏目ID组成的字符串，栏目ID用逗号分隔
	
	private Date ctime;       //创建时间
	
	private Date utime;       //更新时间

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public String getColumn_ids() {
		return column_ids;
	}

	public void setColumn_ids(String column_ids) {
		this.column_ids = column_ids;
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
