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

import com.longyan.dao.EmployeeDao;
import com.longyan.entity.Employee;
import com.longyan.util.MD5;

/**
 * 
 * @author tracyqiu
 * 
 */
@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 新增员工
	 */
	@Override
	public String insert(Employee employee) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，该员工信息已存在； 1003默认表示插入失败，原因未知。
		String sql = "insert into employee(name, password, telephone, id_card, email, sex, birthday, address, province, right_level, qq, ctime) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Employee emp = findEmployeeByIdCard(employee.getId_card());
		
		if(emp != null){
			flag = "1002";
			return flag;
		}
		int i = jdbcTemplate.update(sql, new Object[]{
			employee.getName(),
			MD5.getMD5ofStr(employee.getPassword()),
			employee.getTelephone(),
			employee.getId_card(),
			employee.getEmail(),
			employee.getSex(),
			employee.getBirthday(),
			employee.getAddress(),
			employee.getProvince(),
			employee.getRight_level(),
			employee.getQq(),
			new Date()
		});
		
		if(i > 0){
			flag = "1001";
		}
		return flag;
	}

	/**
	 * 更新员工信息
	 */
	@Override
	public String update(Employee employee) {
		String flag = "2003";     //2001 更新成功；2002  用户不存在； 2003 其他原因更新失败
		String sql = "update employee set name=?, telephone=?, id_card=?, email=?, sex=?, birthday=?, address=?, province=?, right_level=?, qq=?, utime=? where id=?";
		Employee emp = findEmployeeByIdCard(employee.getId_card());
		
		if(emp == null){
			flag = "2002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			employee.getName(),
			employee.getTelephone(),
			employee.getId_card(),
			employee.getEmail(),
			employee.getSex(),
			employee.getBirthday(),
			employee.getAddress(),
			employee.getProvince(),
			employee.getRight_level(),
			employee.getQq(),
			new Date(),
			employee.getId()
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
	public String updatePassword(Employee employee, String password) {
		String flag = "2003";     //2001 更新成功；2002  用户不存在； 2003 其他原因更新失败
		String sql = "update employee set password=?, utime=?";
		Employee emp = findEmployeeByIdCard(employee.getId_card());
		
		if(emp == null){
			flag = "2002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			MD5.getMD5ofStr(password),
			new Date()
		});
		
		if(i > 0){
			flag = "2001";
		}

		return flag;
	}
	
	/**
	 * 根据ID删除员工
	 */
	@Override
	public String delete(Employee employee) {
		String flag = "3003"; //3001删除成功；3002 删除的人不存在； 3003 未知原因删除失败
		String sql = "delete from employee where id=?";
		
		Employee emp = findEmployeeByIdCard(employee.getId_card());
		
		if(emp == null){
			flag = "3002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			employee.getId()
		});
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}
	
	@Override
	public String deleteMore(String ids) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from employee where id in (" + ids + ")";
		
		int i = jdbcTemplate.update(sql);
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}

	/**
	 * 通过ID查找员工
	 */
	@Override
	public Employee findById(Integer id) {
		List<Employee> employee = null;
		String sql = "select * from employee where id=?";
		
		employee = (List<Employee>)jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<Employee>() {  
            @Override  
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Employee emp = setEmployeeProperties(rs); 
                return emp;  
            }  
        });
		
		return employee.size() > 0 ? employee.get(0) : null;
	}

	/**
	 * 取得所有用户信息
	 */
	@Override
	public List<Employee> findAll() {
		String sql = "select * from employee order by ctime desc";
		List<Employee> employees = new ArrayList<Employee>();
		
		employees = (List<Employee>)jdbcTemplate.query(sql, new RowMapper<Employee>() {  
            @Override  
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Employee emp = setEmployeeProperties(rs); 
            	return emp;
            }  
        });
		
		return employees;
	}
	
	/**
	 * 根据名字模糊查询
	 */
	@Override
	public List<Employee> findByName(String name) {
		List<Employee> employees = new ArrayList<Employee>();
		String sql = "select * from employee where name like %?% order by ctime desc";
		employees = (List<Employee>) jdbcTemplate.query(sql, new RowMapper<Employee>() {  
	        @Override  
	        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {  
	        	Employee employee = setEmployeeProperties(rs); 
	            return employee;  
	        }  
	    }); 
		
		return employees;
	}

	/**
	 * 通过身份证号获取员工信息
	 * 
	 * @param email
	 * @return
	 */
	private Employee findEmployeeByIdCard(String id_card){
		List<Employee> employee = null;
		String sql = "select * from employee where id_card=?";
		employee = (List<Employee>) jdbcTemplate.query(sql, new Object[]{ id_card }, new RowMapper<Employee>() {  
            @Override  
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Employee emp = setEmployeeProperties(rs); 
                return emp; 
            }  
        }); 
		
		return employee.size() > 0 ? employee.get(0) : null;
	}
	
	/**
	 * 登录验证
	 * @param username
	 * @param password
	 * @return
	 */
	public Employee checkLogin(String username, String password){
		List<Employee> employee = null;
		String sql = "select * from employee where name=? and password=?";
		employee = (List<Employee>) jdbcTemplate.query(sql, new Object[]{ username, password }, new RowMapper<Employee>() {  
            @Override  
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Employee emp = setEmployeeProperties(rs); 
                return emp; 
            }  
        }); 
		
		return employee.size() > 0 ? employee.get(0) : null;
	}
	
	/**
	 * 设置员工信息
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	private Employee setEmployeeProperties(ResultSet rs) throws SQLException{
		Employee employee = new Employee();  
		
		employee.setId(rs.getInt("id"));
		employee.setAddress(rs.getString("address"));
		employee.setPassword(rs.getString("password"));
		employee.setBirthday(rs.getString("birthday"));
		employee.setEmail(rs.getString("email"));
		employee.setQq(rs.getString("qq"));
		employee.setName(rs.getString("name"));
		employee.setSex(rs.getString("sex"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setProvince(rs.getString("province"));
		employee.setId_card(rs.getString("id_card"));
		employee.setRight_level(rs.getInt("right_level"));
    	employee.setCtime(rs.getDate("ctime"));
    	employee.setUtime(rs.getDate("utime"));
    	
        return employee;
	}

}
