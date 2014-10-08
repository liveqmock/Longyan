package com.longyan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longyan.dao.impl.PermissionDaoImpl;
import com.longyan.entity.Permission;

/**
 * 权限服务层
 * @author tracyqiu
 *
 */
@Service
public class PermissionService {

	@Autowired
	private PermissionDaoImpl permissionDaoImpl;

	public PermissionDaoImpl getPermissionDaoImpl() {
		return permissionDaoImpl;
	}

	public void setPermissionDaoImpl(PermissionDaoImpl permissionDaoImpl) {
		this.permissionDaoImpl = permissionDaoImpl;
	}
	
	/**
	 * 添加权限
	 * @param permission
	 * @return
	 */
	public String addPermission(Permission permission){
		return permissionDaoImpl.insert(permission);
	}
	
	/**
	 * 修改权限信息
	 * @param permission
	 * @return
	 */
	public String modifyPermission(Permission permission){
		return permissionDaoImpl.update(permission);
	}
	
	/**
	 * 根据名称查找权限信息
	 * @param name
	 * @return
	 */
	public Permission getPermissionsByEmployeeId(Integer id){
		return permissionDaoImpl.findByEmployeeId(id);
	}
}
