package com.longyan.dao;

import java.util.List;

import com.longyan.entity.Content;

/**
 * 二级内容二级内容持久成封装
 * @author tracyqiu
 *
 */
public interface ContentDao {
	
	/**
	 * 新增二级内容
	 * @param content
	 * @return
	 */
	public String insert(Content content);
	
	/**
	 * 更新二级内容信息
	 * @param content
	 * @return
	 */
	public String update(Content content);
	
	/**
	 * 删除二级内容信息
	 * @param content
	 * @return
	 */
	public String delete(Content content);
	
	/**
	 * 删除多条记录
	 * @param ids
	 * @return
	 */
	public String deleteMore(String ids);
	
	/**
	 * 根据ID查找二级内容
	 * @param id
	 * @return
	 */
	public Content findById(Integer id);
	
	/**
	 * 取得所有二级内容
	 * @return
	 */
	public List<Content> findAll();
	
	/**
	 * 根据名字查找相关二级内容
	 * @param name
	 * @return
	 */
	public List<Content> findByName(String name);
	
	/**
	 * 取得某个栏目下的所有二级内容
	 * @param column_id
	 * @return
	 */
	public List<Content> findByColumnId(Integer column_id);
}
