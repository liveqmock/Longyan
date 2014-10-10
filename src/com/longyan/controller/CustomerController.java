package com.longyan.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.longyan.service.CustomerService;

/**
 * 
 * @author tracyqiu
 *
 */
@Controller
public class CustomerController {
	
	@Resource(name="customerService")
	private CustomerService customerService;

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
}
