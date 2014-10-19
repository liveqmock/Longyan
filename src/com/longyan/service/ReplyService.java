package com.longyan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longyan.dao.impl.ReplyDaoImpl;
import com.longyan.entity.Reply;

/**
 * 回复服务层
 * @author tracyqiu
 *
 */
@Service
public class ReplyService {

	@Autowired
	private ReplyDaoImpl replyDaoImpl;

	public ReplyDaoImpl getReplyDaoImpl() {
		return replyDaoImpl;
	}

	public void setReplyDaoImpl(ReplyDaoImpl replyDaoImpl) {
		this.replyDaoImpl = replyDaoImpl;
	}
	
	/**
	 * 添加回复
	 * @param reply
	 * @return
	 */
	public String addReply(Reply reply){
		return replyDaoImpl.insert(reply);
	}
	
	/**
	 * 根据回复ID删除回复
	 * @param reply
	 * @return
	 */
	public String delReplyById(Reply reply){
		return replyDaoImpl.delete(reply);
	}
	
	/**
	 * 根据名称查找回复信息
	 * @param name
	 * @return
	 */
	public Reply getReplysByMessageId(Integer id){
		return replyDaoImpl.findByMessageId(id);
	}
}
