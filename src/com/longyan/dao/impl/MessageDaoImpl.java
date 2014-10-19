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

import com.longyan.dao.MessageDao;
import com.longyan.entity.Message;
/**
 * 留言信息持久层
 * @author tracyqiu
 *
 */
@Repository
public class MessageDaoImpl implements MessageDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 新增留言信息
	 */
	@Override
	public String insert(Message message) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，已存在； 1003默认表示插入失败，原因未知。
		String sql = "insert into message(customer_id, customer_phone, customer_name, content, status, ctime) values(?,?,?,?,?,?)";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			message.getCustomer_id(),
			message.getCustomer_name(),
			message.getCustomer_phone(),
			message.getContent(),
			message.getStatus(),
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
	public String update(Message message) {
		String flag = "2003";   //2001 更新成功；2002  不存在； 2003 其他原因更新失败
		String sql = "update message set customer_id=?, customer_phone=?, customer_name=?, content=?, status=?, utime=? where id=?";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			message.getCustomer_id(),
			message.getCustomer_name(),
			message.getCustomer_phone(),
			message.getContent(),
			message.getStatus(),
			new Date(),
			message.getId()
		});
		
		if(i > 0){
			flag = "2001";
		}
		return flag;
	}
	
	/**
	 * 更新信息状态
	 * @param id
	 * @param status
	 * @return
	 */
	public String updateStatus(Integer id, Integer status){
		String flag = "2003";   //2001 更新成功；2002  不存在； 2003 其他原因更新失败
		String sql = "update message set status=?, utime=? where id=?";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			status,
			new Date(),
			id
		});
		
		if(i > 0){
			flag = "2001";
		}
		return flag;
	}

	/**
	 * 删除留言信息
	 */
	@Override
	public String delete(Message message) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from message where id=?";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			message.getId()
		});
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}
	
	/**
	 * 批量删除留言信息
	 */
	@Override
	public String deleteMore(String ids) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from message where id in (" + ids + ")";
		
		int i = jdbcTemplate.update(sql);
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}

	@Override
	public Message findById(Integer id) {
		List<Message> message = null;
		String sql = "select * from message where id=?";
		
		message = (List<Message>)jdbcTemplate.query(sql, new Object[]{ id }, new RowMapper<Message>() {  
            @Override  
            public Message mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Message con = setMessageProperties(rs); 
                return con;  
            }  
        });
		
		return message.size() > 0 ? message.get(0) : null;
	}

	/**
	 * 取得所有留言信息
	 */
	@Override
	public List<Message> findAll() {
		String sql = "select * from message order by ctime desc";
		List<Message> messages = new ArrayList<Message>();
		
		messages = (List<Message>)jdbcTemplate.query(sql, new RowMapper<Message>() {  
            @Override  
            public Message mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Message con = setMessageProperties(rs); 
            	return con;
            }  
        });
		
		return messages;
	}

	/**
	 * 通过名称获取客人留言信息
	 */
	@Override
	public List<Message> findByName(String name) {
		String sql = "select * from message where customer_name like %?% order by ctime desc";
		List<Message> messages = new ArrayList<Message>();
		
		messages = (List<Message>)jdbcTemplate.query(sql, new Object[]{name}, new RowMapper<Message>() {  
            @Override  
            public Message mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Message con = setMessageProperties(rs); 
            	return con;
            }  
        });
		
		return messages;
	}
	
	/**
	 * 取得某个会员能看到的留言信息
	 */
	@Override
	public List<Message> findByCustomerId(Integer customerId) {
		String sql = "select * from message where customer_id=? and status=1 order by ctime desc";
		List<Message> messages = new ArrayList<Message>();
		
		messages = (List<Message>)jdbcTemplate.query(sql, new Object[]{customerId}, new RowMapper<Message>() {  
            @Override  
            public Message mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Message con = setMessageProperties(rs); 
            	return con;
            }  
        });
		
		return messages;
	}

	/**
	 * 根据日期区间和留言类别过滤
	 */
	@Override
	public List<Message> filteByDateAndStatus(Date startDate, Date endDate, Integer status) {
		String sql = "select * from message where ctime between ? and ? and status=? order by ctime desc";
		List<Message> messages = new ArrayList<Message>();
		
		messages = (List<Message>)jdbcTemplate.query(sql, new Object[]{startDate, endDate, status}, new RowMapper<Message>() {  
            @Override  
            public Message mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Message con = setMessageProperties(rs); 
            	return con;
            }  
        });
		
		return messages;
	}
	
	/**
	 * 根据日期区间和留言类别过滤
	 */
	@Override
	public List<Message> filteByDate(Date startDate, Date endDate) {
		String sql = "select * from message where ctime between ? and ? order by ctime desc";
		List<Message> messages = new ArrayList<Message>();
		
		messages = (List<Message>)jdbcTemplate.query(sql, new Object[]{startDate, endDate}, new RowMapper<Message>() {  
            @Override  
            public Message mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Message con = setMessageProperties(rs); 
            	return con;
            }  
        });
		
		return messages;
	}
	
	/**
	 * 设置留言属性
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Message setMessageProperties(ResultSet rs) throws SQLException {
		Message message = new Message();
		message.setId(rs.getInt("id"));
		message.setCustomer_id(rs.getInt("customer_id"));
		message.setCustomer_name(rs.getString("customer_name"));
		message.setCustomer_phone(rs.getString("customer_phone"));
		message.setContent(rs.getString("content"));
		message.setStatus(rs.getInt("status"));
		message.setCtime(rs.getDate("ctime"));
		message.setUtime(rs.getDate("utime"));
		
		return message;
	}
}
