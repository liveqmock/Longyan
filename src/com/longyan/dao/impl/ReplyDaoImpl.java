package com.longyan.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.longyan.dao.ReplyDao;
import com.longyan.entity.Reply;
/**
 * 回复信息持久层
 * @author tracyqiu
 *
 */
@Repository
public class ReplyDaoImpl implements ReplyDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 新增回复信息
	 */
	@Override
	public String insert(Reply reply) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，已存在； 1003默认表示插入失败，原因未知。
		String sql = "insert into reply(message_id, reply_content, employee_name, ctime) values(?,?,?,?)";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			reply.getMessage_id(),
			reply.getReply_content(),
			reply.getEmployee_name(),
			new Date()
		});
		
		if(i > 0){
			flag = "1001";
		}
		return flag;
	}

	/**
	 * 删除回复信息
	 */
	@Override
	public String delete(Reply reply) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from reply where id=?";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			reply.getId()
		});
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}
	
	/**
	 * 通过message_id获取回复信息
	 * @return
	 */
	public Reply findByMessageId(Integer message_id){
		if(message_id == null) return null;
		List<Reply> reply = null;
		String sql = "select * from reply where message_id=?";
		reply = (List<Reply>)jdbcTemplate.query(sql, new Object[]{message_id}, new RowMapper<Reply>() {  
            @Override  
            public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Reply con = setReplyProperties(rs); 
                return con;  
            }  
        });
		
		return reply.size() > 0 ? reply.get(0) : null;
	}
	
	/**
	 * 设置回复属性
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Reply setReplyProperties(ResultSet rs) throws SQLException {
		Reply reply = new Reply();
		reply.setId(rs.getInt("id"));
		reply.setMessage_id(rs.getInt("message_id"));
		reply.setReply_content(rs.getString("reply_content"));
		reply.setEmployee_name(rs.getString("employee_name"));
		reply.setCtime(rs.getDate("ctime"));
		reply.setUtime(rs.getDate("utime"));
		
		return reply;
	}

}
