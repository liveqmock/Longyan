package com.longyan.dao;

import java.util.List;

import com.longyan.entity.Bbs;

/**
 * 帖子管理接口
 * @author tracyqiu
 *
 */
public interface BbsDao {
	/**
	 * 新增帖子
	 * @param Bbs
	 * @return
	 */
	public String insert(Bbs bbs);
	
	/**
	 * 更新帖子信息
	 * @param Bbs
	 * @return
	 */
	public String update(Bbs Bbs);
	
	/**
	 * 更新某条帖子状态
	 * @param Bbs
	 * @return
	 */
	public String changeStatus(Bbs Bbs);
	
	/**
	 * 根据状态查找帖子
	 * @param status
	 * @return
	 */
	public List<Bbs> findByStatus(Integer status, Integer start, Integer count);
	
	/**
	 * 取得所有帖子
	 * @return
	 */
	public List<Bbs> findAll(Integer start, Integer count);
	
	/**
	 * 取得某个用户发布的帖子
	 * @param customer_id
	 * @return
	 */
	public List<Bbs> findByCustomerId(Integer customer_id, Integer start, Integer count);
}
