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

import com.longyan.dao.CustomerDao;
import com.longyan.entity.Customer;

/**
 * 
 * @author tracyqiu
 * 
 */
@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 新增会员
	 */
	@Override
	public String insert(Customer customer) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，原因改邮箱已注册； 1003默认表示插入失败，原因未知。
		String sql = "insert into customer(username, realname, password, telephone, sex, birthday, address, qq, email, ctime) values(?,?,?,?,?,?,?,?,?,?)";
		List<Customer> customers = findCustomersByEmail(customer.getEmail());
		
		if(customers.size() > 0){
			flag = "1002";
			return flag;
		}
		int i = jdbcTemplate.update(sql, new Object[]{
				customer.getUsername(),
				customer.getRealname(),
				customer.getPassword(),
				customer.getTelephone(),
				customer.getSex(),
				customer.getBirthday(),
				customer.getAddress(),
				customer.getQq(),
				customer.getEmail(),
				new Date()
		});
		
		if(i > 0){
			flag = "1001";
		}
		return flag;
	}

	@Override
	public String update(Customer customer) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String delete(Customer customer) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Customer findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 通过邮箱获取会员信息
	 * 
	 * @param email
	 * @return
	 */
	public List<Customer> findCustomersByEmail(String email){
		List<Customer> customers = new ArrayList<Customer>();
		String sql = "select * from customer where email=?";
		customers = (List<Customer>) jdbcTemplate.query(sql, new RowMapper<Customer>() {  
                    @Override  
                    public Customer mapRow(ResultSet rs, int rowNum)  
                            throws SQLException {  
                    	Customer customer = new Customer();  
                    	customer.setId(rs.getInt("id"));
                        return customer;  
                    }  
                }); 
		
		return customers;
	}
}
