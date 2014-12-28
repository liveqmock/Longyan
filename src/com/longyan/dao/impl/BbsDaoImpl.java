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

import com.longyan.dao.BbsDao;
import com.longyan.entity.Bbs;
/**
 * 栏目持久层封装
 * @author tracyqiu
 *
 */
@Repository
public class BbsDaoImpl implements BbsDao {

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
	public String insert(Bbs bbs) {
		String flag = "1003";   //1001 插入成功；1002 插入失败，帖子已存在； 1003默认表示插入失败，原因未知。
		String sql = "insert into bbs(title, content, status, customer_id, is_customer, view_count, reply_count, type, ctime, utime) values(?,?,?,?,?,?,?,?,?,?)";
		Bbs col = getBbsById(bbs.getId());
		
		if(col != null){
			flag = "1002";
			return flag;
		}
		int i = jdbcTemplate.update(sql, new Object[]{
			bbs.getTitle(),
			bbs.getContent(),
			bbs.getStatus(),
			bbs.getCutomer_id(),
			bbs.getIs_customer(),
			bbs.getView_count(),
			bbs.getReply_count(),
			bbs.getType(),
			new Date(),
			new Date()
		});
		
		if(i > 0){
			flag = "1001";
		}
		return flag;
	}

	/**
	 * 更新帖子信息
	 */
	@Override
	public String update(Bbs bbs) {
		String flag = "2003";     //2001 更新成功；2002  用户不存在； 2003 其他原因更新失败
		String sql = "update bbs set title=?, content=?, type=?, status=?, utime=? where id=?";
		Bbs col = getBbsById(bbs.getId());
		
		if(col == null){
			flag = "2002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			bbs.getTitle(),
			bbs.getContent(),
			bbs.getType(),
			bbs.getStatus(),
			new Date(),
			bbs.getId()
		});
		
		if(i > 0){
			flag = "2001";
		}

		return flag;
	}

	@Override
	public String changeStatus(Bbs bbs) {
		String flag = "2003";     //2001 更新成功；2002  用户不存在； 2003 其他原因更新失败
		String sql = "update bbs set status=?, utime=? where id=?";
		Bbs col = getBbsById(bbs.getId());
		
		if(col == null){
			flag = "2002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			bbs.getStatus(),
			new Date(),
			bbs.getId()
		});
		
		if(i > 0){
			flag = "2001";
		}

		return flag;
	}
	
	/**
	 * 增加浏览量
	 * @param bbs
	 * @return
	 */
	public String addViewCount(Bbs bbs) {
		String flag = "2003";     //2001 更新成功；2002  用户不存在； 2003 其他原因更新失败
		String sql = "update bbs set view_count=view_count+1 where id=?";
		Bbs col = getBbsById(bbs.getId());
		
		if(col == null){
			flag = "2002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			bbs.getId()
		});
		
		if(i > 0){
			flag = "2001";
		}

		return flag;
	}
	
	/**
	 * 增加回复数量
	 * @param bbs
	 * @return
	 */
	public String addReplyCount(Bbs bbs) {
		String flag = "2003";     //2001 更新成功；2002  用户不存在； 2003 其他原因更新失败
		String sql = "update bbs set reply_count=reply_count+1 where id=?";
		Bbs col = getBbsById(bbs.getId());
		
		if(col == null){
			flag = "2002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			bbs.getId()
		});
		
		if(i > 0){
			flag = "2001";
		}

		return flag;
	}
	
	/**
	 * 更新帖子时间
	 * @param bbs
	 * @return
	 */
	public String updateTime(Bbs bbs) {
		String flag = "2003";     //2001 更新成功；2002  用户不存在； 2003 其他原因更新失败
		String sql = "update bbs set utime=? where id=?";
		Bbs col = getBbsById(bbs.getId());
		
		if(col == null){
			flag = "2002";
			return flag;
		}
		
		int i = jdbcTemplate.update(sql, new Object[]{
			new Date(),
			bbs.getId()
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
	public List<Bbs> findByStatus(Integer status, Integer start, Integer count) {
		String sql = "select * from bbs where status=? order by utime desc limit " + start + ", " + count;
		List<Bbs> bbss = new ArrayList<Bbs>();
		
		bbss = (List<Bbs>)jdbcTemplate.query(sql, new Object[]{ status }, new RowMapper<Bbs>() {  
            @Override  
            public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Bbs col = setBbsProperties(rs); 
            	return col;
            }  
        });
		
		return bbss;
	}
	
	/**
	 * 根据帖子类型获取帖子  -- 后台接口
	 * @param type
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Bbs> findByType(Integer type, Integer start, Integer count) {
		String sql = "select * from bbs where type=? order by utime desc limit " + start + ", " + count;
		List<Bbs> bbss = new ArrayList<Bbs>();
		
		bbss = (List<Bbs>)jdbcTemplate.query(sql, new Object[]{ type }, new RowMapper<Bbs>() {  
            @Override  
            public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Bbs col = setBbsProperties(rs); 
            	return col;
            }  
        });
		
		return bbss;
	}
	
	/**
	 * 取得某个类型下的帖子总数
	 * @param type
	 * @return
	 */
	public int getBbsCountByType(Integer type) {
		String sql = "select * from bbs where type=? and (status = 2 or status = 4)";
		List<Bbs> bbss = new ArrayList<Bbs>();
		
		bbss = (List<Bbs>)jdbcTemplate.query(sql, new Object[]{ type }, new RowMapper<Bbs>() {  
            @Override  
            public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Bbs col = setBbsProperties(rs); 
            	return col;
            }  
        });
		
		return bbss.size();
	}
	
	/**
	 * 根据帖子类型获取帖子
	 * @param type
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Bbs> findPassedAndTimeoutByType(Integer type, Integer start, Integer count) {
		String sql = "select * from bbs where type=? and (status = 2 or status = 4) order by utime desc limit " + start + ", " + count;
		List<Bbs> bbss = new ArrayList<Bbs>();
		
		bbss = (List<Bbs>)jdbcTemplate.query(sql, new Object[]{ type }, new RowMapper<Bbs>() {  
            @Override  
            public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Bbs col = setBbsProperties(rs); 
            	return col;
            }  
        });
		
		return bbss;
	}
	
	/**
	 * 根据帖子类型获取帖子
	 * @param type
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Bbs> findHotBbsByType(Integer type, Integer start, Integer count) {
		String sql = "select * from bbs where type=? and (status = 2 or status = 4) order by reply_count desc limit " + start + ", " + count;
		List<Bbs> bbss = new ArrayList<Bbs>();
		
		bbss = (List<Bbs>)jdbcTemplate.query(sql, new Object[]{ type }, new RowMapper<Bbs>() {  
            @Override  
            public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Bbs col = setBbsProperties(rs); 
            	return col;
            }  
        });
		
		return bbss;
	}
	
	/**
	 * 取得审核通过的帖子供前段展现
	 */
	public List<Bbs> findPassedAndTimeoutedBbs(Integer start, Integer count) {
		String sql = "select * from bbs where status=2 or status=4 order by utime desc limit " + start + ", " + count;
		List<Bbs> bbss = new ArrayList<Bbs>();
		
		bbss = (List<Bbs>)jdbcTemplate.query(sql, new RowMapper<Bbs>() {  
            @Override  
            public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Bbs col = setBbsProperties(rs); 
            	return col;
            }  
        });
		
		return bbss;
	}

	/**
	 * find by customer id
	 */
	@Override
	public List<Bbs> findByCustomerId(Integer customer_id, Integer start, Integer count) {
		String sql = "select * from bbs where customer_id=? and status != 6 order by utime desc limit " + start + ", " + count; 
		List<Bbs> bbss = new ArrayList<Bbs>();
		
		bbss = (List<Bbs>)jdbcTemplate.query(sql, new Object[]{ customer_id }, new RowMapper<Bbs>() {  
            @Override  
            public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Bbs col = setBbsProperties(rs); 
            	return col;
            }  
        });
		
		return bbss;
	}
	
	/**
	 * 取得某个用户下的帖子总数
	 * @param type
	 * @return
	 */
	public int getBbsCountByCustomerId(Integer customer_id) {
		String sql = "select * from bbs where customer_id=? and status != 6";
		List<Bbs> bbss = new ArrayList<Bbs>();
		
		bbss = (List<Bbs>)jdbcTemplate.query(sql, new Object[]{ customer_id }, new RowMapper<Bbs>() {  
            @Override  
            public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Bbs col = setBbsProperties(rs); 
            	return col;
            }  
        });
		
		return bbss.size();
	}
	
	/**
	 * 取得所有栏目
	 */
	@Override
	public List<Bbs> findAll(Integer start, Integer count) {
		String sql = "select * from bbs order by utime desc limit " + start + ", " + count;
		List<Bbs> bbss = new ArrayList<Bbs>();
		
		bbss = (List<Bbs>)jdbcTemplate.query(sql, new RowMapper<Bbs>() {  
            @Override  
            public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Bbs col = setBbsProperties(rs); 
            	return col;
            }  
        });
		
		return bbss;
	}
	
	/**
	 * 取得所有栏目--后台用
	 */
	public List<Bbs> findAll() {
		String sql = "select * from bbs order by utime desc";
		List<Bbs> bbss = new ArrayList<Bbs>();
		
		bbss = (List<Bbs>)jdbcTemplate.query(sql, new RowMapper<Bbs>() {  
            @Override  
            public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Bbs col = setBbsProperties(rs); 
            	return col;
            }  
        });
		
		return bbss;
	}
	
	/**
	 * 根据条件查询
	 * @return
	 */
	public List<Bbs> getBbsByParam(Integer type, Integer status) {
		String sql = "";
		if(type == 0 && status == 0){
			sql = "select * from bbs order by utime desc";
		}else if(type == 0 && status != 0){
			sql = "select * from bbs where status=" + status + " order by utime desc";
		}else if(status == 0 && type != 0){
			sql = "select * from bbs where type=" + type + " order by utime desc";
		}else {
			sql = "select * from bbs where type=" + type +" and status=" + status + " order by utime desc";
		}
		List<Bbs> bbss = new ArrayList<Bbs>();
		
		bbss = (List<Bbs>)jdbcTemplate.query(sql, new RowMapper<Bbs>() {  
            @Override  
            public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Bbs col = setBbsProperties(rs); 
            	return col;
            }  
        });
		
		return bbss;
	}

	/**
	 * 通过id获取帖子
	 * @param code
	 * @return
	 */
	public Bbs getBbsById(Integer id){
		List<Bbs> bbs = null;
		String sql = "select * from bbs where id=?";
		bbs = (List<Bbs>)jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<Bbs>() {  
            @Override  
            public Bbs mapRow(ResultSet rs, int rowNum) throws SQLException {  
            	Bbs col = setBbsProperties(rs); 
                return col;  
            }  
        });
		
		return bbs.size() > 0 ? bbs.get(0) : null;
	}
	
	/**
	 * 设置帖子属性
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Bbs setBbsProperties(ResultSet rs) throws SQLException {
		Bbs bbs = new Bbs();
		
		bbs.setId(rs.getInt("id"));
		bbs.setTitle(rs.getString("title"));
		bbs.setContent(rs.getString("content"));
		bbs.setCutomer_id(rs.getInt("customer_id"));
		bbs.setStatus(rs.getInt("status"));
		bbs.setIs_customer(rs.getInt("is_customer"));
		bbs.setView_count(rs.getInt("view_count"));
		bbs.setReply_count(rs.getInt("reply_count"));
		bbs.setType(rs.getInt("type"));
		bbs.setCtime(rs.getTimestamp("ctime"));
		bbs.setUtime(rs.getTimestamp("utime"));
		return bbs;  
	}

}
