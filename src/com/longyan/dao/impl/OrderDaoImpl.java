package com.longyan.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.longyan.dao.OrderDao;
import com.longyan.entity.Order;
import com.longyan.util.DateUtil;
/**
 * 订单信息持久层
 * @author tracyqiu
 *
 */
@Repository
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 新增订单信息
	 */
	@Override
	public String insert(Order order) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，已存在； 1003默认表示插入失败，原因未知。
		String sql = "insert into order_table(customer_id, code, goods_name, goods_price, price, count, discount, goods_info, remark, employee_name, employee_id, ctime) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			order.getCustomer_id(),
			order.getCode(),
			order.getGoods_name(),
			order.getGoods_price(),
			order.getPrice(),
			order.getCount(),
			order.getDiscount(),
			order.getGoods_info(),
			order.getRemark(),
			order.getEmployee_name(),
			order.getEmployee_id(),
			new Date()
		});
		
		if(i > 0){
			flag = "1001";
		}
		return flag;
	}

	/**
	 * 更新信息
	 */
	@Override
	public String update(Order order) {
		String flag = "2003";   //2001 更新成功；2002  不存在； 2003 其他原因更新失败
		String sql = "update order_table set customer_id=?, code=?, goods_name=?, goods_price=?, price=?, count=?, discount=?, goods_info=?, remark=?, employee_name=?, employee_id=?, utime=? where id=?";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			order.getCustomer_id(),
			order.getCode(),
			order.getGoods_name(),
			order.getGoods_price(),
			order.getPrice(),
			order.getCount(),
			order.getDiscount(),
			order.getGoods_info(),
			order.getRemark(),
			order.getEmployee_name(),
			order.getEmployee_id(),
			new Date(),
			order.getId()
		});
		
		if(i > 0){
			flag = "2001";
		}
		return flag;
	}
	
	/**
	 * 删除订单信息
	 */
	@Override
	public String delete(Order order) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from order_table where id=?";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			order.getId()
		});
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}
	
	/**
	 * 批量删除订单信息
	 */
	@Override
	public String deleteMore(String ids) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from order_table where id in (" + ids + ")";
		
		int i = jdbcTemplate.update(sql, new Object[]{ ids });
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}

	@Override
	public Order findById(Integer id) {
		List<Order> order = null;
		String sql = "select * from order_table where id=?";
		
		order = (List<Order>)jdbcTemplate.query(sql, new Object[]{ id }, new RowMapper<Order>() {  
            @Override  
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Order con = setOrderProperties(rs); 
                return con;  
            }  
        });
		
		return order.size() > 0 ? order.get(0) : null;
	}

	/**
	 * 取得所有订单信息
	 */
	@Override
	public List<Order> findAll() {
		String sql = "select * from order_table order by ctime desc";
		List<Order> orders = new ArrayList<Order>();
		
		orders = (List<Order>)jdbcTemplate.query(sql, new RowMapper<Order>() {  
            @Override  
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Order con = setOrderProperties(rs); 
            	return con;
            }  
        });
		
		return orders;
	}

	/**
	 * 通过名称获取客人订单信息
	 */
	@Override
	public List<Order> findByName(String goods_name) {
		String sql = "select * from order_table where goods_name like %?% order by ctime desc";
		List<Order> orders = new ArrayList<Order>();
		
		orders = (List<Order>)jdbcTemplate.query(sql, new Object[]{goods_name}, new RowMapper<Order>() {  
            @Override  
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Order con = setOrderProperties(rs); 
            	return con;
            }  
        });
		
		return orders;
	}
	
	/**
	 * 取得某个会员能看到的订单信息
	 */
	@Override
	public List<Order> findByCustomerId(Integer customerId) {
		String sql = "select * from order_table where customer_id=? order by ctime desc";
		List<Order> orders = new ArrayList<Order>();
		
		orders = (List<Order>)jdbcTemplate.query(sql, new Object[]{ customerId }, new RowMapper<Order>() {  
            @Override  
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Order con = setOrderProperties(rs); 
            	return con;
            }  
        });
		
		return orders;
	}

	
	/**
	 * 获取一个时间段内的日期
	 */
	@Override
	public List<Order> findByDate(Date startDate, Date endDate) {
		String sql = "select * from order_table where ctime between ? and ? order by ctime desc";
		List<Order> orders = new ArrayList<Order>();
		
		orders = (List<Order>)jdbcTemplate.query(sql, new Object[]{startDate, endDate}, new RowMapper<Order>() {  
            @Override  
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Order con = setOrderProperties(rs); 
            	return con;
            }  
        });
		
		return orders;
	}
	
	/**
	 * 设置订单属性
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Order setOrderProperties(ResultSet rs) throws SQLException {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setCustomer_id(rs.getInt("customer_id"));
		order.setCode(rs.getString("code"));
		order.setGoods_name(rs.getString("goods_name"));
		order.setGoods_price(rs.getFloat("goods_price"));
		order.setPrice(rs.getFloat("price"));
		order.setCount(rs.getInt("count"));
		order.setDiscount(rs.getFloat("discount"));
		order.setGoods_info(rs.getString("goods_info"));
		order.setRemark(rs.getString("remark"));
		order.setEmployee_name(rs.getString("employee_name"));
		order.setEmployee_id(rs.getInt("employee_id"));
		order.setCtime(rs.getTimestamp("ctime"));
		order.setUtime(rs.getTimestamp("utime"));
		
		return order;
	}

}
