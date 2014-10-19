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

import com.longyan.dao.ColumnDao;
import com.longyan.entity.Column;
/**
 * 栏目持久层封装
 * @author tracyqiu
 *
 */
@Repository
public class ColumnDaoImpl implements ColumnDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 新增栏目
	 */
	@Override
	public String insert(Column column) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，栏目已存在； 1003默认表示插入失败，原因未知。
		String sql = "insert into column(site_id, name, code, template_id, create_user, ctime) values(?,?,?,?,?,?)";
		Column col = getColumnByCode(column.getCode());
		
		if(col != null){
			flag = "1002";
			return flag;
		}
		int i = jdbcTemplate.update(sql, new Object[]{
			column.getSite_id(),
			column.getName(),
			column.getCode(),
			column.getTemplate_id(),
			column.getCreate_user(),
			new Date()
		});
		
		if(i > 0){
			flag = "1001";
		}
		return flag;
	}

	/**
	 * 更新栏目信息
	 */
	@Override
	public String update(Column column) {
		String flag = "2003";     //2001 更新成功；2002  用户不存在； 2003 其他原因更新失败
		String sql = "update column set site_id=?, name=?, code=?, template_id=?, create_user=?, utime=? where id=?";
		Column col = getColumnByCode(column.getCode());
		
		if(col == null){
			flag = "2002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			column.getSite_id(),
			column.getName(),
			column.getCode(),
			column.getTemplate_id(),
			column.getCreate_user(),
			new Date(),
			column.getId()
		});
		
		if(i > 0){
			flag = "2001";
		}

		return flag;
	}

	/**
	 * 删除某个栏目
	 */
	@Override
	public String delete(Column column) {
		
		String flag = "3003"; //3001删除成功；3002 删除的人不存在； 3003 未知原因删除失败
		String sql = "delete from column where id=?";
		
		Column col = getColumnByCode(column.getCode());
		
		if(col == null){
			flag = "3002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			column.getId()
		});
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}
	
	/**
	 * 批量删除栏目
	 */
	@Override
	public String deleteMore(String ids) {
		String flag = "3003"; //3001删除成功；3002 删除的人不存在； 3003 未知原因删除失败
		String sql = "delete from column where id in (" + ids + ")";
		
		int i = jdbcTemplate.update(sql);
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}

	/**
	 * 通过ID获取栏目
	 */
	@Override
	public Column findById(Integer id) {
		List<Column> column = null;
		String sql = "select * from column where id=?";
		
		column = (List<Column>)jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<Column>() {  
            @Override  
            public Column mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Column col = setColumnProperties(rs); 
                return col;  
            }  
        });
		
		return column.size() > 0 ? column.get(0) : null;
	}

	/**
	 * 取得所有栏目
	 */
	@Override
	public List<Column> findAll() {
		String sql = "select * from column order by ctime desc";
		List<Column> columns = new ArrayList<Column>();
		
		columns = (List<Column>)jdbcTemplate.query(sql, new RowMapper<Column>() {  
            @Override  
            public Column mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Column col = setColumnProperties(rs); 
            	return col;
            }  
        });
		
		return columns;
	}

	/**
	 * 根据名称查找栏目
	 */
	@Override
	public List<Column> findByName(String name) {
		String sql = "select * from column where name like %?% order by ctime desc";
		List<Column> columns = new ArrayList<Column>();
		
		columns = (List<Column>)jdbcTemplate.query(sql, new Object[]{name}, new RowMapper<Column>() {  
            @Override  
            public Column mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Column col = setColumnProperties(rs); 
            	return col;
            }  
        });
		
		return columns;
	}

	/**
	 * 获取某个站点下的所有栏目
	 */
	@Override
	public List<Column> findBySiteId(Integer site_id) {
		String sql = "select * from column where site_id=? order by ctime desc";
		List<Column> columns = new ArrayList<Column>();
		
		columns = (List<Column>)jdbcTemplate.query(sql, new Object[]{site_id}, new RowMapper<Column>() {  
            @Override  
            public Column mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Column col = setColumnProperties(rs); 
            	return col;
            }  
        });
		
		return columns;
	}
	
	/**
	 * 通过栏目code获取栏目
	 * @param code
	 * @return
	 */
	private Column getColumnByCode(String code){
		if(code == "") return null;
		List<Column> column = null;
		String sql = "select * from column where code=?";
		column = (List<Column>)jdbcTemplate.query(sql, new Object[]{code}, new RowMapper<Column>() {  
            @Override  
            public Column mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Column col = setColumnProperties(rs); 
                return col;  
            }  
        });
		
		return column.size() > 0 ? column.get(0) : null;
	}
	
	/**
	 * 设置栏目属性
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Column setColumnProperties(ResultSet rs) throws SQLException {
		Column column = new Column();
		
		column.setId(rs.getInt("id"));
		column.setCode(rs.getString("code"));
		column.setCreate_user(rs.getString("create_user"));
		column.setCtime(rs.getDate("ctime"));
		column.setName(rs.getString("name"));
		column.setSite_id(rs.getInt("site_id"));
		column.setTemplate_id(rs.getInt("template_id"));
		column.setUtime(rs.getDate("utime"));
		return column;
	}
}
