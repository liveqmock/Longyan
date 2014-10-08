package com.longyan.dao;

import java.util.Date;
import java.util.List;

import com.longyan.entity.Message;

/**
 * 留言信息
 * @author tracyqiu
 *
 */
public interface MessageDao {

	/**
	 * 新增留言信息
	 * @param message
	 * @return
	 */
	public String insert(Message message);
	
	/**
	 * 更新留言信息
	 * @param message
	 * @return
	 */
	public String update(Message message);
	
	/**
	 * 删除留言信息
	 * @param message
	 * @return
	 */
	public String delete(Message message);
	
	/**
	 * 删除多条记录
	 * @param ids
	 * @return
	 */
	public String deleteMore(String ids);
	
	/**
	 * 根据ID查找留言
	 * @param id
	 * @return
	 */
	public Message findById(Integer id);
	
	/**
	 * 取得所有留言
	 * @return
	 */
	public List<Message> findAll();
	
	/**
	 * 根据名字查找相关留言
	 * @param name
	 * @return
	 */
	public List<Message> findByName(String name);
	
	/**
	 * 取得会员能看到的所有留言
	 * @param customerId
	 * @return
	 */
	public List<Message> findByCustomerId(Integer customerId);
	
	/**
	 * 按照日期，状态过滤
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Message> filteByDateAndStatus(Date startDate, Date endDate, Integer status);
}
