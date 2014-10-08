package com.longyan.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longyan.dao.impl.OrderDaoImpl;
import com.longyan.entity.Order;

/**
 * 订单服务层
 * @author tracyqiu
 *
 */
@Service
public class OrderService {

	@Autowired
	private OrderDaoImpl orderDaoImpl;

	public OrderDaoImpl getOrderDaoImpl() {
		return orderDaoImpl;
	}

	public void setOrderDaoImpl(OrderDaoImpl orderDaoImpl) {
		this.orderDaoImpl = orderDaoImpl;
	}
	
	/**
	 * 添加订单
	 * @param order
	 * @return
	 */
	public String addOrder(Order order){
		return orderDaoImpl.insert(order);
	}
	
	/**
	 * 修改订单信息
	 * @param order
	 * @return
	 */
	public String modifyOrder(Order order){
		return orderDaoImpl.update(order);
	}
	
	/**
	 * 根据订单ID删除订单
	 * @param order
	 * @return
	 */
	public String delOrderById(Order order){
		return orderDaoImpl.delete(order);
	}
	
	/**
	 * 批量删除订单
	 * @param ids
	 * @return
	 */
	public String delMoreOrder(String ids){
		return orderDaoImpl.deleteMore(ids);
	}
	
	/**
	 * 获取所有订单
	 * @return
	 */
	public List<Order> getAllOrders(){
		return orderDaoImpl.findAll();
	}
	
	/**
	 * 根据名称查找订单信息
	 * @param name
	 * @return
	 */
	public List<Order> getOrdersByName(String name){
		return orderDaoImpl.findByName(name);
	}
	
	/**
	 * 获取会员能看到的订单
	 * @param customer_id
	 * @return
	 */
	public List<Order> getOrdersByCustomerId(Integer customer_id){
		return orderDaoImpl.findByCustomerId(customer_id);
	}
	
	/**
	 * 根据时间，状态进行过滤
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	public List<Order> getOrdersByDate(Date startDate, Date endDate){
		return orderDaoImpl.findByDate(startDate, endDate);
	}
}
