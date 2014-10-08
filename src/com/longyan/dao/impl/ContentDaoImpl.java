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

import com.longyan.dao.ContentDao;
import com.longyan.entity.Content;
/**
 * 二级内容持久层封装
 * @author tracyqiu
 *
 */
@Repository
public class ContentDaoImpl implements ContentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 新增二级内容
	 */
	@Override
	public String insert(Content content) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，二级内容已存在； 1003默认表示插入失败，原因未知。
		String sql = "insert into content(title, code, template_id,column_id, create_user, ctime) values(?,?,?,?,?,?)";
		Content col = getContentByCode(content.getCode());
		
		if(col != null){
			flag = "1002";
			return flag;
		}
		int i = jdbcTemplate.update(sql, new Object[]{
			content.getTitle(),
			content.getCode(),
			content.getTemplate_id(),
			content.getColumn_id(),
			content.getCreate_user(),
			new Date()
		});
		
		if(i > 0){
			flag = "1001";
		}
		return flag;
	}

	/**
	 * 更新二级内容信息
	 */
	@Override
	public String update(Content content) {
		String flag = "2003";     //2001 更新成功；2002  用户不存在； 2003 其他原因更新失败
		String sql = "update content set title=?, code=?, template_id=?, column_id=?, create_user=?, utime=?";
		Content col = getContentByCode(content.getCode());
		
		if(col == null){
			flag = "2002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			content.getTitle(),
			content.getCode(),
			content.getTemplate_id(),
			content.getColumn_id(),
			content.getCreate_user(),
			new Date()
		});
		
		if(i > 0){
			flag = "2001";
		}

		return flag;
	}

	/**
	 * 删除某个二级内容
	 */
	@Override
	public String delete(Content content) {
		
		String flag = "3003"; //3001删除成功；3002 删除的人不存在； 3003 未知原因删除失败
		String sql = "delete from content where id=?";
		
		Content col = getContentByCode(content.getCode());
		
		if(col == null){
			flag = "3002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			content.getId()
		});
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}
	
	/**
	 * 批量删除二级内容
	 */
	@Override
	public String deleteMore(String ids) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from content where id in (?)";
		
		int i = jdbcTemplate.update(sql, new Object[]{ ids });
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}

	/**
	 * 通过ID获取二级内容
	 */
	@Override
	public Content findById(Integer id) {
		Content content = null;
		String sql = "select * from content where id=?";
		
		content = (Content)jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Content>() {  
            @Override  
            public Content mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Content col = setContentProperties(rs); 
                return col;  
            }  
        });
		
		return content;
	}

	/**
	 * 取得所有二级内容
	 */
	@Override
	public List<Content> findAll() {
		String sql = "select * from content";
		List<Content> contents = new ArrayList<Content>();
		
		contents = (List<Content>)jdbcTemplate.query(sql, new RowMapper<Content>() {  
            @Override  
            public Content mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Content col = setContentProperties(rs); 
            	return col;
            }  
        });
		
		return contents;
	}

	/**
	 * 根据名称查找二级内容
	 */
	@Override
	public List<Content> findByName(String title) {
		String sql = "select * from content where title like %?%";
		List<Content> contents = new ArrayList<Content>();
		
		contents = (List<Content>)jdbcTemplate.query(sql, new Object[]{title}, new RowMapper<Content>() {  
            @Override  
            public Content mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Content col = setContentProperties(rs); 
            	return col;
            }  
        });
		
		return contents;
	}

	/**
	 * 获取某个站点下的所有二级内容
	 */
	@Override
	public List<Content> findByColumnId(Integer column_id) {
		String sql = "select * from content where column_id=?";
		List<Content> contents = new ArrayList<Content>();
		
		contents = (List<Content>)jdbcTemplate.query(sql, new Object[]{column_id}, new RowMapper<Content>() {  
            @Override  
            public Content mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Content col = setContentProperties(rs); 
            	return col;
            }  
        });
		
		return contents;
	}
	
	/**
	 * 通过二级内容code获取二级内容
	 * @param code
	 * @return
	 */
	private Content getContentByCode(String code){
		if(code == "") return null;
		Content content = null;
		String sql = "select * from content where code=?";
		content = (Content)jdbcTemplate.queryForObject(sql, new Object[]{code}, new RowMapper<Content>() {  
            @Override  
            public Content mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Content col = setContentProperties(rs); 
                return col;  
            }  
        });
		
		return content;
	}
	
	/**
	 * 设置二级内容属性
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Content setContentProperties(ResultSet rs) throws SQLException {
		Content content = new Content();
		
		content.setId(rs.getInt("id"));
		content.setCode(rs.getString("code"));
		content.setCreate_user(rs.getString("create_user"));
		content.setCtime(rs.getDate("ctime"));
		content.setTitle(rs.getString("title"));
		content.setColumn_id(rs.getInt("column_id"));
		content.setTemplate_id(rs.getInt("template_id"));
		content.setUtime(rs.getDate("utime"));
		return content;
	}

}
