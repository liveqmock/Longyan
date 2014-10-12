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

import com.longyan.dao.FriendLinksDao;
import com.longyan.entity.FriendLinks;
/**
 * 友情链接信息持久层
 * @author tracyqiu
 *
 */
@Repository
public class FriendLinksDaoImpl implements FriendLinksDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 新增友情链接信息
	 */
	@Override
	public String insert(FriendLinks friendLinks) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，已存在； 1003默认表示插入失败，原因未知。
		String sql = "insert into friend_links(url, name, ctime) values(?,?,?)";

		int i = jdbcTemplate.update(sql, new Object[]{
			friendLinks.getUrl(),
			friendLinks.getName(),
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
	public String update(FriendLinks friendLinks) {
		String flag = "2003";   //2001 更新成功；2002  不存在； 2003 其他原因更新失败
		String sql = "update friend_links set url=?, name=?, utime=?";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			friendLinks.getUrl(),
			friendLinks.getName(),
			new Date()
		});
		
		if(i > 0){
			flag = "2001";
		}
		return flag;
	}

	/**
	 * 删除友情链接信息
	 */
	@Override
	public String delete(FriendLinks friendLinks) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from friend_links where id=?";
		
		int i = jdbcTemplate.update(sql, new Object[]{
			friendLinks.getId()
		});
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}
	
	@Override
	public String deleteMore(String ids) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from friend_links where id in (?)";
		
		int i = jdbcTemplate.update(sql, new Object[]{ ids });
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}

	@Override
	public FriendLinks findById(Integer id) {
		List<FriendLinks> friendLinks = null;
		String sql = "select * from friend_links where id=?";
		
		friendLinks = (List<FriendLinks>)jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<FriendLinks>() {  
            @Override  
            public FriendLinks mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	FriendLinks con = setFriendLinksProperties(rs); 
                return con;  
            }  
        });
		
		return friendLinks.size() > 0 ? friendLinks.get(0) : null;
	}

	/**
	 * 取得所有友情链接信息
	 */
	@Override
	public List<FriendLinks> findAll() {
		String sql = "select * from friend_links";
		List<FriendLinks> friendLinkses = new ArrayList<FriendLinks>();
		
		friendLinkses = (List<FriendLinks>)jdbcTemplate.query(sql, new RowMapper<FriendLinks>() {  
            @Override  
            public FriendLinks mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	FriendLinks con = setFriendLinksProperties(rs); 
            	return con;
            }  
        });
		
		return friendLinkses;
	}

	/**
	 * 通过名称获取客人友情链接信息
	 */
	@Override
	public List<FriendLinks> findByName(String name) {
		String sql = "select * from friend_links where name like %?%";
		List<FriendLinks> friendLinkses = new ArrayList<FriendLinks>();
		
		friendLinkses = (List<FriendLinks>)jdbcTemplate.query(sql, new Object[]{name}, new RowMapper<FriendLinks>() {  
            @Override  
            public FriendLinks mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	FriendLinks con = setFriendLinksProperties(rs); 
            	return con;
            }  
        });
		
		return friendLinkses;
	}
	
	/**
	 * 设置友情链接属性
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private FriendLinks setFriendLinksProperties(ResultSet rs) throws SQLException {
		FriendLinks friendLinks = new FriendLinks();
		friendLinks.setId(rs.getInt("id"));
		friendLinks.setUrl(rs.getString("url"));
		friendLinks.setName(rs.getString("name"));
		friendLinks.setCtime(rs.getDate("ctime"));
		friendLinks.setUtime(rs.getDate("utime"));
		
		return friendLinks;
	}

}
