package com.longyan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longyan.dao.impl.CustomerDaoImpl;
import com.longyan.entity.Customer;

/**
 * 会员管理业务逻辑层
 * @author tracyqiu
 *
 */
@Service
public class CustomerService {

	@Autowired
	private CustomerDaoImpl customerDaoImpl;

	public CustomerDaoImpl getCustomerDaoImpl() {
		return customerDaoImpl;
	}

	public void setCustomerDaoImpl(CustomerDaoImpl customerDaoImpl) {
		this.customerDaoImpl = customerDaoImpl;
	}
	
	/**
	 * 会员注册
	 * @param customer
	 * @return
	 */
	public String addCustomer(Customer customer){
		return customerDaoImpl.insert(customer);
	}
	
	/**
	 * 删除会员信息
	 * @param customer
	 * @return
	 */
	public String delCustomer(Customer customer){
		return customerDaoImpl.delete(customer);
	}
	
	/**
	 * 批量删除会员信息
	 * @param ids
	 * @return
	 */
	public String delMoreCustomer(String ids){
		return customerDaoImpl.deleteMore(ids);
	}
	
	/**
	 * 更新会员信息，不能修改密码
	 * @param customer
	 * @return
	 */
	public String modifyCustomer(Customer customer) {
		return customerDaoImpl.update(customer);
	}
	
	/**
	 * 会员修改密码
	 * @param customer
	 * @param password
	 * @return
	 */
	public String modifyPassword(Customer customer, String password) {
		return customerDaoImpl.updatePassword(customer, password);
	}
	
	/**
	 * 通过会员ID获取会员信息
	 * @param id
	 * @return
	 */
	public Customer getCustomerById(Integer id){
		return customerDaoImpl.findById(id);
	}
	
	/**
	 * 验证用户登录
	 * @param username
	 * @param pass
	 * @return
	 */
	public Customer getCustomerByNameAndPass(String username, String pass){
		return customerDaoImpl.findByUsernameAndPassword(username, pass);
	}
	
	/**
	 * 获取所有会员信息
	 * @return
	 */
	public List<Customer> getAllCustomers(){
		return customerDaoImpl.findAll();
	}
	
	/**
	 * 通过名字查询会员信息
	 * @param name
	 * @return
	 */
	public List<Customer> getCustomersByName(String name) {
		return customerDaoImpl.findByName(name);
	}
	
	/**
	 * 通过邮箱查询会员信息
	 * @param name
	 * @return
	 */
	public List<Customer> getCustomersByEmail(String email) {
		return customerDaoImpl.findCustomersByEmail(email);
	}
}
