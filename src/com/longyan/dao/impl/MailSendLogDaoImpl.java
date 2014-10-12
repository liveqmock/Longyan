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

import com.longyan.dao.MailSendLogDao;
import com.longyan.entity.MailSendLog;
/**
 * 邮件信息持久层
 * @author tracyqiu
 *
 */
@Repository
public class MailSendLogDaoImpl implements MailSendLogDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 新增邮件信息
	 */
	@Override
	public String insert(MailSendLog mailSendLog) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，已存在； 1003默认表示插入失败，原因未知。
		String sql = "insert into mail_send_log(employee_id, customer_id, recieve_name, phone_number, title, content, send_name, type, ctime) values(?,?,?,?,?,?,?,?,?)";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			mailSendLog.getEmployee_id(),
			mailSendLog.getCustomer_id(),
			mailSendLog.getRecieve_name(),
			mailSendLog.getPhone_number(),
			mailSendLog.getTitle(),
			mailSendLog.getContent(),
			mailSendLog.getSend_name(),
			mailSendLog.getType(),
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
	public String update(MailSendLog mailSendLog) {
		String flag = "2003";   //2001 更新成功；2002  不存在； 2003 其他原因更新失败
		String sql = "update mail_send_log employee_id=?, customer_id=?, recieve_name=?, phone_number=?, title=?, content=?, send_name=?, type=?, utime=?";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			mailSendLog.getEmployee_id(),
			mailSendLog.getCustomer_id(),
			mailSendLog.getRecieve_name(),
			mailSendLog.getPhone_number(),
			mailSendLog.getTitle(),
			mailSendLog.getContent(),
			mailSendLog.getSend_name(),
			mailSendLog.getType(),
			new Date()
		});
		
		if(i > 0){
			flag = "2001";
		}
		return flag;
	}

	/**
	 * 删除邮件信息
	 */
	@Override
	public String delete(MailSendLog mailSendLog) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from mail_send_log where id=?";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			mailSendLog.getId()
		});
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}
	
	@Override
	public String deleteMore(String ids) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from mail_send_log where id in (?)";
		
		int i = jdbcTemplate.update(sql, new Object[]{ ids });
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}

	@Override
	public MailSendLog findById(Integer id) {
		List<MailSendLog> mailSendLog = null;
		String sql = "select * from mail_send_log where id=?";
		
		mailSendLog = (List<MailSendLog>)jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<MailSendLog>() {  
            @Override  
            public MailSendLog mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	MailSendLog con = setMailSendLogProperties(rs); 
                return con;  
            }  
        });
		
		return mailSendLog.size() > 0 ? mailSendLog.get(0) : null ;
	}

	/**
	 * 取得所有邮件信息
	 */
	@Override
	public List<MailSendLog> findAll() {
		String sql = "select * from mail_send_log";
		List<MailSendLog> mailSendLogs = new ArrayList<MailSendLog>();
		
		mailSendLogs = (List<MailSendLog>)jdbcTemplate.query(sql, new RowMapper<MailSendLog>() {  
            @Override  
            public MailSendLog mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	MailSendLog con = setMailSendLogProperties(rs); 
            	return con;
            }  
        });
		
		return mailSendLogs;
	}

	/**
	 * 通过title获取客人邮件信息
	 */
	@Override
	public List<MailSendLog> findByName(String name) {
		String sql = "select * from mail_send_log where title like %?%";
		List<MailSendLog> mailSendLogs = new ArrayList<MailSendLog>();
		
		mailSendLogs = (List<MailSendLog>)jdbcTemplate.query(sql, new Object[]{name}, new RowMapper<MailSendLog>() {  
            @Override  
            public MailSendLog mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	MailSendLog con = setMailSendLogProperties(rs); 
            	return con;
            }  
        });
		
		return mailSendLogs;
	}
	
	/**
	 * 查询某个时间区间内的邮件
	 */
	@Override
	public List<MailSendLog> findByDate(Date startDate, Date endDate) {
		String sql = "select * from mail_send_log where ctime between ? and ?";
		List<MailSendLog> mailSendLogs = new ArrayList<MailSendLog>();
		
		mailSendLogs = (List<MailSendLog>)jdbcTemplate.query(sql, new Object[]{startDate, endDate}, new RowMapper<MailSendLog>() {  
            @Override  
            public MailSendLog mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	MailSendLog con = setMailSendLogProperties(rs); 
            	return con;
            }  
        });
		
		return mailSendLogs;
	}
	
	/**
	 * 设置邮件属性
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private MailSendLog setMailSendLogProperties(ResultSet rs) throws SQLException {
		MailSendLog mailSendLog = new MailSendLog();
		mailSendLog.setId(rs.getInt("id"));
		mailSendLog.setCustomer_id(rs.getInt("customer_id"));
		mailSendLog.setEmployee_id(rs.getInt("employee_id"));
		mailSendLog.setRecieve_name(rs.getString("recieve_name"));
		mailSendLog.setPhone_number(rs.getString("phone_number"));
		mailSendLog.setTitle(rs.getString("title"));
		mailSendLog.setContent(rs.getString("content"));
		mailSendLog.setSend_name(rs.getString("send_name"));
		mailSendLog.setType(rs.getInt("type"));
		mailSendLog.setCtime(rs.getDate("ctime"));
		mailSendLog.setUtime(rs.getDate("utime"));
		
		return mailSendLog;
	}

}
