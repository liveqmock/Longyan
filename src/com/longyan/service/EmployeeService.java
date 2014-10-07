package com.longyan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longyan.dao.impl.EmployeeDaoImpl;
import com.longyan.entity.Employee;

/**
 * 员工管理业务逻辑层
 * @author tracyqiu
 *
 */
@Service
public class EmployeeService {

	@Autowired
	private EmployeeDaoImpl employeeDaoImpl;

	public EmployeeDaoImpl getEmployeeDaoImpl() {
		return employeeDaoImpl;
	}

	public void setEmployeeDaoImpl(EmployeeDaoImpl employeeDaoImpl) {
		this.employeeDaoImpl = employeeDaoImpl;
	}
	
	/**
	 * 员工注册
	 * @param employee
	 * @return
	 */
	public String addEmployee(Employee employee){
		return employeeDaoImpl.insert(employee);
	}
	
	/**
	 * 删除员工信息
	 * @param employee
	 * @return
	 */
	public String delEmployee(Employee employee){
		return employeeDaoImpl.delete(employee);
	}
	
	/**
	 * 更新员工信息，不能修改密码
	 * @param employee
	 * @return
	 */
	public String modifyEmployee(Employee employee) {
		return employeeDaoImpl.update(employee);
	}
	
	/**
	 * 员工修改密码
	 * @param employee
	 * @param password
	 * @return
	 */
	public String modifyPassword(Employee employee, String password) {
		return employeeDaoImpl.updatePassword(employee, password);
	}
	
	/**
	 * 通过员工ID获取员工信息
	 * @param id
	 * @return
	 */
	public Employee getEmployeeById(Integer id){
		return employeeDaoImpl.findById(id);
	}
	
	/**
	 * 获取所有员工信息
	 * @return
	 */
	public List<Employee> getAllEmployees(){
		return employeeDaoImpl.findAll();
	}
	
	/**
	 * 通过名字查询员工信息
	 * @param name
	 * @return
	 */
	public List<Employee> getEmployeesByName(String name) {
		return employeeDaoImpl.findByName(name);
	}
}
