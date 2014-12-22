package com.longyan.dao;

import java.util.List;

import com.longyan.entity.BbsReply;

/**
 * 跟帖管理接口
 * @author tracyqiu
 *
 */
public interface BbsReplyDao {
	/**
	 * 新增跟帖
	 * @param BbsReply
	 * @return
	 */
	public String insert(BbsReply bbsReply);
	
	
	/**
	 * 更新某条跟帖状态
	 * @param BbsReply
	 * @return
	 */
	public String changeStatus(BbsReply BbsReply);
	
	/**
	 * 根据状态查找跟帖
	 * @param status
	 * @return
	 */
	public List<BbsReply> findByStatus(Integer status, Integer start, Integer count);
	
	/**
	 * 取得所有跟帖
	 * @return
	 */
	public List<BbsReply> findAll(Integer start, Integer count);
	
	/**
	 * 取得某个帖子的跟帖
	 * @param bbs_id
	 * @return
	 */
	public List<BbsReply> findByBbsId(Integer bbs_id, Integer start, Integer count);
	
	/**
	 * 取得某个用户的跟帖
	 * @param customer_id
	 * @return
	 */
	public List<BbsReply> findByCustomerId(Integer customer_id, Integer start, Integer count);
}
