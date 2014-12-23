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

import com.longyan.dao.BbsReplyDao;
import com.longyan.entity.BbsReply;
/**
 * 栏目持久层封装
 * @author tracyqiu
 *
 */
@Repository
public class BbsReplyDaoImpl implements BbsReplyDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 新增跟帖
	 */
	@Override
	public String insert(BbsReply bbsReply) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，帖子已存在； 1003默认表示插入失败，原因未知。
		String sql = "insert into bbs_reply(content, bbs_id, cutomer_id, status, ctime, utime) values(?,?,?,?,?,?)";
		BbsReply col = getBbsReplyById(bbsReply.getId());
		
		if(col != null){
			flag = "1002";
			return flag;
		}
		int i = jdbcTemplate.update(sql, new Object[]{
			bbsReply.getContent(),
			bbsReply.getBbs_id(),
			bbsReply.getCutomer_id(),
			bbsReply.getStatus(),
			new Date(),
			new Date()
		});
		
		if(i > 0){
			flag = "1001";
		}
		return flag;
	}

	@Override
	public String changeStatus(BbsReply bbsReply) {
		String flag = "2003";     //2001 更新成功；2002  用户不存在； 2003 其他原因更新失败
		String sql = "update bbs_reply set status=?, utime=? where id=?";
		BbsReply col = getBbsReplyById(bbsReply.getId());
		
		if(col == null){
			flag = "2002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			bbsReply.getStatus(),
			new Date(),
			bbsReply.getId()
		});
		
		if(i > 0){
			flag = "2001";
		}

		return flag;
	}

	/**
	 * find by status
	 */
	@Override
	public List<BbsReply> findByStatus(Integer status, Integer start, Integer count) {
		String sql = "select * from bbs_reply where status=? order by ctime asc limit " + start + ", " + count;
		List<BbsReply> bbss = new ArrayList<BbsReply>();
		
		bbss = (List<BbsReply>)jdbcTemplate.query(sql, new Object[]{ status }, new RowMapper<BbsReply>() {  
            @Override  
            public BbsReply mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	BbsReply col = setBbsReplyProperties(rs); 
            	return col;
            }  
        });
		
		return bbss;
	}

	/**
	 * find by customer id
	 */
	@Override
	public List<BbsReply> findByCustomerId(Integer customer_id, Integer start, Integer count) {
		String sql = "select * from bbs_reply where customer_id=? order by ctime asc limit " + start + ", " + count;
		List<BbsReply> bbss = new ArrayList<BbsReply>();
		
		bbss = (List<BbsReply>)jdbcTemplate.query(sql, new Object[]{ customer_id }, new RowMapper<BbsReply>() {  
            @Override  
            public BbsReply mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	BbsReply col = setBbsReplyProperties(rs); 
            	return col;
            }  
        });
		
		return bbss;
	}
	
	/**
	 * 取得所有跟帖
	 */
	@Override
	public List<BbsReply> findAll(Integer start, Integer count) {
		String sql = "select * from bbs_reply order by ctime asc limit " + start + ", " + count;
		List<BbsReply> bbss = new ArrayList<BbsReply>();
		
		bbss = (List<BbsReply>)jdbcTemplate.query(sql, new RowMapper<BbsReply>() {  
            @Override  
            public BbsReply mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	BbsReply col = setBbsReplyProperties(rs); 
            	return col;
            }  
        });
		
		return bbss;
	}
	
	/**
	 * 取得某个帖子下面的所有跟帖
	 */
	@Override
	public List<BbsReply> findByBbsId(Integer bbs_id, Integer start, Integer count) {
		String sql = "select * from bbs_reply where bbs_id=? order by ctime asc limit " + start + ", " + count;
		List<BbsReply> bbss = new ArrayList<BbsReply>();
		
		bbss = (List<BbsReply>)jdbcTemplate.query(sql, new Object[]{ bbs_id }, new RowMapper<BbsReply>() {  
            @Override  
            public BbsReply mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	BbsReply col = setBbsReplyProperties(rs); 
            	return col;
            }  
        });
		
		return bbss;
	}

	/**
	 * 通过id获取跟帖
	 * @param id
	 * @return
	 */
	public BbsReply getBbsReplyById(Integer id){
		List<BbsReply> bbsReply = null;
		String sql = "select * from bbs_reply where id=?";
		bbsReply = (List<BbsReply>)jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<BbsReply>() {  
            @Override  
            public BbsReply mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	BbsReply col = setBbsReplyProperties(rs); 
                return col;  
            }  
        });
		
		return bbsReply.size() > 0 ? bbsReply.get(0) : null;
	}
	
	/**
	 * 设置跟帖属性
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private BbsReply setBbsReplyProperties(ResultSet rs) throws SQLException {
		BbsReply bbsReply = new BbsReply();
		
		bbsReply.setId(rs.getInt("id"));
		bbsReply.setContent(rs.getString("content"));
		bbsReply.setBbs_id(rs.getInt("bbs_id"));
		bbsReply.setCutomer_id(rs.getInt("customer_id"));
		bbsReply.setStatus(rs.getInt("status"));
		bbsReply.setCtime(rs.getTimestamp("ctime"));
		bbsReply.setUtime(rs.getTimestamp("utime"));
		return bbsReply;  
	}

}
