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

import com.longyan.dao.AttachmentsDao;
import com.longyan.entity.Attachments;
import com.longyan.entity.Employee;

/**
 * 附件处理
 * @author tracyqiu
 *
 */
@Repository
public class AttachmentsDaoImpl implements AttachmentsDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 添加附件，如果附件之前已经添加，则从新更新附件信息
	 */
	@Override
	public String insert(Attachments attachments) {
		String flag = "1002";  //1001 插入成功, 1002插入失败, 2001 更新成功， 2003更新失败
		Attachments att = getByPath(attachments.getPath());
		if(att != null){
			flag = update(attachments);
		}else {
			String sql = "insert into attachments (filename, path, create_user, column_id, ctime) values (?,?,?,?,?)";
			
			int i = jdbcTemplate.update(sql, new Object[]{
				attachments.getFilename(),
				attachments.getPath(),
				attachments.getCreate_user(),
				attachments.getColumn_id(),
				new Date()
			});
			
			if(i > 0){
				flag = "1001";
			}
		}
		return flag;
	}

	/**
	 * 更新附件信息
	 */
	@Override
	public String update(Attachments attachments) {
		String flag = "2003"; //2001 更新成功， 2002附件不存在, 2003附件更新失败
		String sql = "update attachments set filename=?, path=?, create_user=?, column_id=?, utime=?";
		
		Attachments att = getByPath(attachments.getPath());
		if(att == null){
			flag = "2003";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			attachments.getFilename(),
			attachments.getPath(),
			attachments.getCreate_user(),
			attachments.getColumn_id(),
			new Date()
		});
		
		if(i > 0){
			flag = "2001";
		}

		return flag;
	}

	/**
	 * 通过附件路径查找附件
	 */
	@Override
	public Attachments getByPath(String path) {
		if(path == ""){
			return null;
		}
		Attachments attachments = null;
		String sql = "select * from attachments where path=?";
		attachments = (Attachments)jdbcTemplate.queryForObject(sql, new Object[]{path}, new RowMapper<Attachments>() {  
            @Override  
            public Attachments mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Attachments att = setAttachmentsProperties(rs); 
                return att;  
            }  
        });
		
		return attachments;
	}
	
	/**
	 * 取得栏目下的所有附件
	 */
	@Override
	public List<Attachments> getByColumnId(Integer column_id) {
		String sql = "select * from attachments where column_id=?";
		if(column_id == null) return null;
		
		List<Attachments> attachmentses = new ArrayList<Attachments>();
		
		attachmentses = (List<Attachments>)jdbcTemplate.query(sql, new Object[]{column_id}, new RowMapper<Attachments>() {  
            @Override  
            public Attachments mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Attachments att = setAttachmentsProperties(rs); 
            	return att;
            }  
        });
		
		return attachmentses;
	}
	
	/**
	 * 删除某个附件
	 */
	@Override
	public String delete(Attachments attachments) {
		String flag = "3003"; //3001删除成功；3002 删除的附件不存在； 3003 未知原因删除失败
		String sql = "delete from attachments where id=?";
		
		Attachments att = getByPath(attachments.getPath());
		
		if(att == null){
			flag = "3002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			attachments.getId()
		});
		
		if(i > 0){
			flag = "3001";
		}

		return flag;
	}
	
	/**
	 * 设置附件信息
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Attachments setAttachmentsProperties(ResultSet rs) throws SQLException {
		Attachments attachments= new Attachments();
		
		attachments.setId(rs.getInt("id"));
		attachments.setColumn_id(rs.getInt("column_id"));
		attachments.setCreate_user(rs.getString("create_user"));
		attachments.setCtime(rs.getDate("ctime"));
		attachments.setFilename(rs.getString("filename"));
		attachments.setPath(rs.getString("path"));
		attachments.setUtime(rs.getDate("utime"));
		
		return attachments;
	}
}
