package com.longyan.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.longyan.service.EmployeeService;

/**
 * 
 * @author tracyqiu
 *
 */
@Controller
public class EmployeeController {
	
	@Resource(name="employeeService")
	private EmployeeService employeeService;

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
}
