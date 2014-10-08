package com.longyan.dao;

import java.util.List;

import com.longyan.entity.FriendLinks;

/**
 * 友情链接
 * @author tracyqiu
 *
 */
public interface FriendLinksDao {

	/**
	 * 新增友情链接
	 * @param friendLinks
	 * @return
	 */
	public String insert(FriendLinks friendLinks);
	
	/**
	 * 更新友情链接
	 * @param friendLinks
	 * @return
	 */
	public String update(FriendLinks friendLinks);
	
	/**
	 * 删除友情链接
	 * @param friendLinks
	 * @return
	 */
	public String delete(FriendLinks friendLinks);
	
	/**
	 * 删除多条记录
	 * @param ids
	 * @return
	 */
	public String deleteMore(String ids);
	
	/**
	 * 根据ID查找友情链接
	 * @param id
	 * @return
	 */
	public FriendLinks findById(Integer id);
	
	/**
	 * 取得所有友情链接
	 * @return
	 */
	public List<FriendLinks> findAll();
	
	/**
	 * 根据名字查找相关友情链接
	 * @param name
	 * @return
	 */
	public List<FriendLinks> findByName(String name);
}
