package com.longyan.dao;

import com.longyan.entity.Reply;

/**
 * 回复信息
 * @author tracyqiu
 *
 */
public interface ReplyDao {

	/**
	 * 新增回复信息
	 * @param reply
	 * @return
	 */
	public String insert(Reply reply);
	
	/**
	 * 删除回复信息
	 * @param reply
	 * @return
	 */
	public String delete(Reply reply);
	
	/**
	 * 根据ID查找回复
	 * @param id
	 * @return
	 */
	public Reply findByMessageId(Integer message_id);
	
}
