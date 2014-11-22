package com.longyan.dao;

import java.util.Date;
import java.util.List;

import com.longyan.entity.Order;

/**
 * 订单信息
 * @author tracyqiu
 *
 */
public interface OrderDao {

	/**
	 * 新增订单信息
	 * @param order
	 * @return
	 */
	public String insert(Order order);
	
	/**
	 * 更新订单信息
	 * @param order
	 * @return
	 */
	public String update(Order order);
	
	/**
	 * 删除订单信息
	 * @param order
	 * @return
	 */
	public String delete(Order order);
	
	/**
	 * 删除多条记录
	 * @param ids
	 * @return
	 */
	public String deleteMore(String ids);
	
	/**
	 * 根据ID查找订单
	 * @param id
	 * @return
	 */
	public Order findById(Integer id);
	
	/**
	 * 取得所有订单
	 * @return
	 */
	public List<Order> findAll();
	
	/**
	 * 根据名字查找相关订单
	 * @param name
	 * @return
	 */
	public List<Order> findByName(String name);
	
	/**
	 * 取得会员能看到的所有订单
	 * @param customerId
	 * @return
	 */
	public List<Order> findByCustomerId(Integer customerId, Integer start, Integer pageSize);
	
	/**
	 * 按照日期
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Order> findByDate(Date startDate, Date endDate);
}
