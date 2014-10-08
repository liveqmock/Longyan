package com.longyan.dao;

import java.util.List;

import com.longyan.entity.Contact;

/**
 * 客服信息
 * @author tracyqiu
 *
 */
public interface ContactDao {

	/**
	 * 新增客服信息
	 * @param contact
	 * @return
	 */
	public String insert(Contact contact);
	
	/**
	 * 更新服客信息
	 * @param contact
	 * @return
	 */
	public String update(Contact contact);
	
	/**
	 * 删除服客信息
	 * @param contact
	 * @return
	 */
	public String delete(Contact contact);
	
	/**
	 * 删除多条记录
	 * @param ids
	 * @return
	 */
	public String deleteMore(String ids);
	
	/**
	 * 根据ID查找服客
	 * @param id
	 * @return
	 */
	public Contact findById(Integer id);
	
	/**
	 * 取得所有服客
	 * @return
	 */
	public List<Contact> findAll();
	
	/**
	 * 根据名字查找相关服客
	 * @param name
	 * @return
	 */
	public List<Contact> findByName(String name);
}
