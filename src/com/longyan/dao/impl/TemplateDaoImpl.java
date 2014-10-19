package com.longyan.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.longyan.dao.TemplateDao;
import com.longyan.entity.Template;
/**
 * 模板信息持久层
 * @author tracyqiu
 *
 */
@Repository
public class TemplateDaoImpl implements TemplateDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 新增模板信息
	 */
	@Override
	public String insert(Template template) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，已存在； 1003默认表示插入失败，原因未知。
		String sql = "insert into template(column_id, content_id, filename, path, create_user, ctime) values(?,?,?,?,?,?)";
		Template con = getTemplateByFileName(template.getFilename());
		
		if(con != null){
			flag = "1002";
			return flag;
		}
		int i = jdbcTemplate.update(sql, new Object[]{
			template.getColumn_id(),
			template.getContent_id(),
			template.getFilename(),
			template.getPath(),
			template.getCreate_user(),
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
	public String update(Template template) {
		String flag = "2003";   //2001 更新成功；2002  不存在； 2003 其他原因更新失败
		String sql = "update template set column_id=?, content_id=?, filename=?, path=?, create_user=?, utime=? where id=?";
		Template con = getTemplateByFileName(template.getFilename());
		
		if(con == null){
			flag = "2002";
			return flag;
		}
		int i = jdbcTemplate.update(sql, new Object[]{
			template.getColumn_id(),
			template.getContent_id(),
			template.getFilename(),
			template.getPath(),
			template.getCreate_user(),
			new Date(),
			template.getId()
		});
		
		if(i > 0){
			flag = "2001";
		}
		return flag;
	}

	/**
	 * 删除模板信息
	 */
	@Override
	public String delete(Template template) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from template where id=?";
		
		Template con = getTemplateByFileName(template.getFilename());
		
		if(con == null){
			flag = "3002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			template.getId()
		});
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}


	/**
	 * 通过filename获取模板信息
	 * @return
	 */
	private Template getTemplateByFileName(String filename){
		if(filename == "") return null;
		List<Template> template = null;
		String sql = "select * from template where filename=?";
		template = (List<Template>)jdbcTemplate.query(sql, new Object[]{ filename }, new RowMapper<Template>() {  
            @Override  
            public Template mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Template con = setTemplateProperties(rs); 
                return con;  
            }  
        });
		
		return template.size() > 0 ? template.get(0) : null;
	}
	
	/**
	 * 根据栏目ID查找模板
	 */
	@Override
	public Template findByColumnId(Integer column_id) {
		if(column_id == null) return null;
		List<Template> template = null;
		String sql = "select * from template where column_id=?";
		template = (List<Template>)jdbcTemplate.query(sql, new Object[]{ column_id }, new RowMapper<Template>() {  
            @Override  
            public Template mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Template con = setTemplateProperties(rs); 
                return con;  
            }  
        });
		
		return template.size() > 0 ? template.get(0) : null;
	}

	/**
	 * 根据contentID查找模板
	 */
	@Override
	public Template findByContentId(Integer content_id) {
		if(content_id == null) return null;
		List<Template> template = null;
		String sql = "select * from template where column_id=?";
		template = (List<Template>)jdbcTemplate.query(sql, new Object[]{ content_id }, new RowMapper<Template>() {  
            @Override  
            public Template mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Template con = setTemplateProperties(rs); 
                return con;  
            }  
        });
		
		return template.size() > 0 ? template.get(0) : null;
	}
	
	/**
	 * 设置模板属性
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Template setTemplateProperties(ResultSet rs) throws SQLException {
		Template template = new Template();
		template.setId(rs.getInt("id"));
		template.setColumn_id(rs.getInt("column_id"));
		template.setContent_id(rs.getInt("content_id"));
		template.setFilename(rs.getString("filename"));
		template.setPath(rs.getString("path"));
		template.setCreate_user(rs.getString("create_user"));
		template.setCtime(rs.getDate("ctime"));
		template.setUtime(rs.getDate("utime"));
		
		return template;
	}

}
