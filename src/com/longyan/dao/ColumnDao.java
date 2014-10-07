package com.longyan.dao;

import java.util.List;

import com.longyan.entity.Column;

/**
 * 栏目持久成封装
 * @author tracyqiu
 *
 */
public interface ColumnDao {
	
	/**
	 * 新增栏目
	 * @param column
	 * @return
	 */
	public String insert(Column column);
	
	/**
	 * 更新栏目信息
	 * @param column
	 * @return
	 */
	public String update(Column column);
	
	/**
	 * 删除栏目信息
	 * @param column
	 * @return
	 */
	public String delete(Column column);
	
	/**
	 * 根据ID查找栏目
	 * @param id
	 * @return
	 */
	public Column findById(Integer id);
	
	/**
	 * 取得所有栏目
	 * @return
	 */
	public List<Column> findAll();
	
	/**
	 * 根据名字查找相关栏目
	 * @param name
	 * @return
	 */
	public List<Column> findByName(String name);
	
	/**
	 * 取得某个站点下的所有栏目
	 * @param site_id
	 * @return
	 */
	public List<Column> findBySiteId(Integer site_id);
}
