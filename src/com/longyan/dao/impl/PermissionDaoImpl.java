package com.longyan.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.longyan.dao.PermissionDao;
import com.longyan.entity.Permission;
/**
 * 权限信息持久层
 * @author tracyqiu
 *
 */
@Repository
public class PermissionDaoImpl implements PermissionDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 新增权限信息
	 */
	@Override
	public String insert(Permission permission) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，已存在； 1003默认表示插入失败，原因未知。
		String sql = "insert into permission(employee_id, column_ids, ctime) values(?,?,?)";
		Permission con = findByEmployeeId(permission.getEmployee_id());
		
		if(con != null){
			flag = "1002";
			return flag;
		}
		int i = jdbcTemplate.update(sql, new Object[]{
			permission.getEmployee_id(),
			permission.getColumn_ids(),
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
	public String update(Permission permission) {
		String flag = "2003";   //2001 更新成功；2002  不存在； 2003 其他原因更新失败
		String sql = "update permission set column_ids=?, utime=? where employee_id=?";
		Permission con = findByEmployeeId(permission.getEmployee_id());
		
		if(con == null){
			flag = "2002";
			return flag;
		}
		int i = jdbcTemplate.update(sql, new Object[]{
			permission.getColumn_ids(),
			new Date(),
			permission.getEmployee_id()
		});
		
		if(i > 0){
			flag = "2001";
		}
		return flag;
	}

	/**
	 * 获取某个员工的权限
	 */
	@Override
	public Permission findByEmployeeId(Integer employee_id) {
		if(employee_id == null) return null;
		Permission permission = null;
		String sql = "select * from permission where employee_id=?";
		permission = (Permission)jdbcTemplate.queryForObject(sql, new Object[]{ employee_id }, new RowMapper<Permission>() {  
            @Override  
            public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Permission con = setPermissionProperties(rs); 
                return con;  
            }  
        });
		
		return permission;
	}
	
	/**
	 * 设置权限属性
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Permission setPermissionProperties(ResultSet rs) throws SQLException {
		Permission permission = new Permission();
		permission.setId(rs.getInt("id"));
		permission.setEmployee_id(rs.getInt("employee_id"));
		permission.setColumn_ids(rs.getString("column_ids"));
		permission.setCtime(rs.getDate("ctime"));
		permission.setUtime(rs.getDate("utime"));
		
		return permission;
	}

}
