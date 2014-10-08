package com.longyan.dao;

import java.util.List;

import com.longyan.entity.Customer;

/**
 * 会员管理持久层封装
 * @author tracyqiu
 *
 */
public interface CustomerDao {
	/**
	 * 新增会员
	 * @param customer
	 * @return
	 */
	public String insert(Customer customer);
	
	/**
	 * 更新会员信息
	 * @param customer
	 * @return
	 */
	public String update(Customer customer);
	
	/**
	 * 更新会员信息
	 * @param customer
	 * @return
	 */
	public String updatePassword(Customer customer, String password);
	
	/**
	 * 删除会员信息
	 * @param customer
	 * @return
	 */
	public String delete(Customer customer);
	
	/**
	 * 删除多条记录
	 * @param ids
	 * @return
	 */
	public String deleteMore(String ids);
	
	/**
	 * 根据ID查找会员
	 * @param id
	 * @return
	 */
	public Customer findById(Integer id);
	
	/**
	 * 取得所有会员
	 * @return
	 */
	public List<Customer> findAll();
	
	/**
	 * 根据名字查找相关会员
	 * @param name
	 * @return
	 */
	public List<Customer> findByName(String name);
}
