package com.longyan.dao;

import com.longyan.entity.Permission;

/**
 * 权限信息
 * @author tracyqiu
 *
 */
public interface PermissionDao {

	/**
	 * 新增权限信息
	 * @param permission
	 * @return
	 */
	public String insert(Permission permission);
	
	/**
	 * 新增服客信息
	 * @param permission
	 * @return
	 */
	public String update(Permission permission);
	
	/**
	 * 取得某个员工的权限
	 * @param employee_id
	 * @return
	 */
	public Permission findByEmployeeId(Integer employee_id);
	
}
