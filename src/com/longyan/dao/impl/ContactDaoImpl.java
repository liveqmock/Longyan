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

import com.longyan.dao.ContactDao;
import com.longyan.entity.Contact;
/**
 * 客服信息持久层
 * @author tracyqiu
 *
 */
@Repository
public class ContactDaoImpl implements ContactDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 新增客服信息
	 */
	@Override
	public String insert(Contact contact) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，已存在； 1003默认表示插入失败，原因未知。
		String sql = "insert into contact(qq, name, telephone, ctime) values(?,?,?,?)";
		Contact con = getContactByQq(contact.getQq());
		
		if(con != null){
			flag = "1002";
			return flag;
		}
		int i = jdbcTemplate.update(sql, new Object[]{
			contact.getQq(),
			contact.getName(),
			contact.getTelephone(),
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
	public String update(Contact contact) {
		String flag = "2003";   //2001 更新成功；2002  不存在； 2003 其他原因更新失败
		String sql = "update contact set qq=?, name=?, telephone=?, utime=?";
		Contact con = getContactByQq(contact.getQq());
		
		if(con == null){
			flag = "2002";
			return flag;
		}
		int i = jdbcTemplate.update(sql, new Object[]{
			contact.getQq(),
			contact.getName(),
			contact.getTelephone(),
			new Date()
		});
		
		if(i > 0){
			flag = "2001";
		}
		return flag;
	}

	/**
	 * 删除客服信息
	 */
	@Override
	public String delete(Contact contact) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from contact where id=?";
		
		Contact con = getContactByQq(contact.getQq());
		
		if(con == null){
			flag = "3002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			contact.getId()
		});
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}
	
	/**
	 * 批量删除客服信息
	 */
	@Override
	public String deleteMore(String ids) {
		String flag = "3003"; //3001删除成功；3002 不存在； 3003 未知原因删除失败
		String sql = "delete from contact where id in (?)";
		
		int i = jdbcTemplate.update(sql, new Object[]{ ids });
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}

	@Override
	public Contact findById(Integer id) {
		Contact contact = null;
		String sql = "select * from contact where id=?";
		
		contact = (Contact)jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Contact>() {  
            @Override  
            public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Contact con = setContactProperties(rs); 
                return con;  
            }  
        });
		
		return contact;
	}

	/**
	 * 取得所有客服信息
	 */
	@Override
	public List<Contact> findAll() {
		String sql = "select * from contact";
		List<Contact> contacts = new ArrayList<Contact>();
		
		contacts = (List<Contact>)jdbcTemplate.query(sql, new RowMapper<Contact>() {  
            @Override  
            public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Contact con = setContactProperties(rs); 
            	return con;
            }  
        });
		
		return contacts;
	}

	/**
	 * 通过名称获取客人客服信息
	 */
	@Override
	public List<Contact> findByName(String name) {
		String sql = "select * from contact where name like %?%";
		List<Contact> contacts = new ArrayList<Contact>();
		
		contacts = (List<Contact>)jdbcTemplate.query(sql, new Object[]{name}, new RowMapper<Contact>() {  
            @Override  
            public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Contact con = setContactProperties(rs); 
            	return con;
            }  
        });
		
		return contacts;
	}
	
	/**
	 * 通过qq获取客服信息
	 * @return
	 */
	private Contact getContactByQq(String qq){
		if(qq == "") return null;
		Contact contact = null;
		String sql = "select * from contact where qq=?";
		contact = (Contact)jdbcTemplate.queryForObject(sql, new Object[]{qq}, new RowMapper<Contact>() {  
            @Override  
            public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Contact con = setContactProperties(rs); 
                return con;  
            }  
        });
		
		return contact;
	}
	
	/**
	 * 设置客服属性
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Contact setContactProperties(ResultSet rs) throws SQLException {
		Contact contact = new Contact();
		contact.setId(rs.getInt("id"));
		contact.setName(rs.getString("name"));
		contact.setQq(rs.getString("qq"));
		contact.setTelephone(rs.getString("telephone"));
		contact.setCtime(rs.getDate("ctime"));
		contact.setUtime(rs.getDate("utime"));
		
		return contact;
	}

}
