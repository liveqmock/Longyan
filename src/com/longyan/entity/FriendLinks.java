package com.longyan.entity;

import java.util.Date;

/**
 * 友情链接
 * @author tracyqiu
 *
 */
public class FriendLinks {

	private String url;  //URL
	
	private String name;  //链接名称
	
	private Date ctime;       //创建时间
	
	private Date utime;       //更新时间

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
