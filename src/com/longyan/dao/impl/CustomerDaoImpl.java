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
import com.longyan.util.MD5;

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
			MD5.getMD5ofStr(customer.getPassword()),
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

	/**
	 * 更新会员信息
	 */
	@Override
	public String update(Customer customer) {
		String flag = "2003";     //2001 更新成功；2002  用户不存在； 2003 其他原因更新失败
		String sql = "update customer set username=?, realname=?, telephone=?, sex=?, birthday=?, address=?, qq=?, email=?, utime=? where id=?";
		List<Customer> customers = findCustomersByEmail(customer.getEmail());
		
		if(customers.size() <= 0){
			flag = "2002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			customer.getUsername(),
			customer.getRealname(),
			customer.getTelephone(),
			customer.getSex(),
			customer.getBirthday(),
			customer.getAddress(),
			customer.getQq(),
			customer.getEmail(),
			new Date(),
			customer.getId()
		});
		
		if(i > 0){
			flag = "2001";
		}

		return flag;
	}

	/**
	 * 更新密码
	 */
	@Override
	public String updatePassword(Customer customer, String password) {
		String flag = "2003";     //2001 更新成功；2002  用户不存在； 2003 其他原因更新失败
		String sql = "update customer set password=?, utime=? where id=?";
		List<Customer> customers = findCustomersByEmail(customer.getEmail());
		
		if(customers.size() <= 0){
			flag = "2002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			MD5.getMD5ofStr(password),
			customer.getId(),
			new Date()
		});
		
		if(i > 0){
			flag = "2001";
		}

		return flag;
	}
	
	/**
	 * 根据ID删除会员
	 */
	@Override
	public String delete(Customer customer) {
		String flag = "3003"; //3001删除成功；3002 删除的人不存在； 3003 未知原因删除失败
		String sql = "delete from customer where id=?";
		
		List<Customer> customers = findCustomersByEmail(customer.getEmail());
		
		if(customers.size() <= 0){
			flag = "3002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			customer.getId()
		});
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}
	
	@Override
	public String deleteMore(String ids) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from customer where id in (" + ids + ")";
		
		int i = jdbcTemplate.update(sql);
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}

	/**
	 * 通过ID查找会员
	 */
	@Override
	public Customer findById(Integer id) {
		List<Customer> customer = null;
		String sql = "select * from customer where id=?";
		
		customer = (List<Customer>)jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<Customer>() {  
            @Override  
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Customer cus = setCustomerProperties(rs); 
                return cus;  
            }  
        });
		
		return customer.size() > 0 ? customer.get(0) : null;
	}

	/**
	 * 取得所有用户信息
	 */
	@Override
	public List<Customer> findAll() {
		String sql = "select * from customer order by ctime desc";
		List<Customer> customers = new ArrayList<Customer>();
		
		customers = (List<Customer>)jdbcTemplate.query(sql, new RowMapper<Customer>() {  
            @Override  
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Customer cus = setCustomerProperties(rs); 
            	return cus;
            }  
        });
		
		return customers;
	}
	
	/**
	 * 根据名字模糊查询
	 */
	@Override
	public List<Customer> findByName(String name) {
		List<Customer> customers = new ArrayList<Customer>();
		String sql = "select * from customer where realname like %?% order by ctime desc";
		customers = (List<Customer>) jdbcTemplate.query(sql, new Object[]{name}, new RowMapper<Customer>() {  
	        @Override  
	        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {  
	        	Customer cus = setCustomerProperties(rs); 
	            return cus;  
	        }  
	    }); 
		
		return customers;
	}

	/**
	 * 通过邮箱获取会员信息
	 * 
	 * @param email
	 * @return
	 */
	private List<Customer> findCustomersByEmail(String email){
		List<Customer> customers = new ArrayList<Customer>();
		String sql = "select * from customer where email=?";
		customers = (List<Customer>) jdbcTemplate.query(sql, new Object[]{email}, new RowMapper<Customer>() {  
            @Override  
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Customer cus = setCustomerProperties(rs); 
                return cus; 
            }  
        }); 
		
		return customers;
	}
	
	/**
	 * 设置会员信息
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	private Customer setCustomerProperties(ResultSet rs) throws SQLException{
		Customer cus = new Customer();  
		
		cus.setId(rs.getInt("id"));
		cus.setAddress(rs.getString("address"));
    	cus.setBirthday(rs.getString("birthday"));
    	cus.setEmail(rs.getString("email"));
    	cus.setQq(rs.getString("qq"));
    	cus.setRealname(rs.getString("realname"));
    	cus.setSex(rs.getString("sex"));
    	cus.setTelephone(rs.getString("telephone"));
    	cus.setUsername(rs.getString("username"));
    	cus.setCtime(rs.getDate("ctime"));
    	cus.setUtime(rs.getDate("utime"));
        return cus;
	}

}
