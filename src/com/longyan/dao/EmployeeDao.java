package com.longyan.dao;

import java.util.List;

import com.longyan.entity.Employee;

/**
 * 员工管理持久层封装
 * @author tracyqiu
 *
 */
public interface EmployeeDao {
	/**
	 * 新增员工信息
	 * @param employee
	 * @return
	 */
	public String insert(Employee employee);
	
	/**
	 * 更新员工信息
	 * @param employee
	 * @return
	 */
	public String update(Employee employee);
	
	/**
	 * 更新员工信息
	 * @param employee
	 * @return
	 */
	public String updatePassword(Employee employee, String password);
	
	/**
	 * 删除员工信息
	 * @param employee
	 * @return
	 */
	public String delete(Employee employee);
	
	/**
	 * 根据ID查找员工
	 * @param id
	 * @return
	 */
	public Employee findById(Integer id);
	
	/**
	 * 取得所有员工
	 * @return
	 */
	public List<Employee> findAll();
	
	/**
	 * 根据名字查找相关员工
	 * @param name
	 * @return
	 */
	public List<Employee> findByName(String name);
}
