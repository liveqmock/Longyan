package com.longyan.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longyan.dao.impl.MessageDaoImpl;
import com.longyan.entity.Message;

/**
 * 留言服务层
 * @author tracyqiu
 *
 */
@Service
public class MessageService {

	@Autowired
	private MessageDaoImpl messageDaoImpl;

	public MessageDaoImpl getMessageDaoImpl() {
		return messageDaoImpl;
	}

	public void setMessageDaoImpl(MessageDaoImpl messageDaoImpl) {
		this.messageDaoImpl = messageDaoImpl;
	}
	
	/**
	 * 添加留言
	 * @param message
	 * @return
	 */
	public String addMessage(Message message){
		return messageDaoImpl.insert(message);
	}
	
	/**
	 * 修改留言信息
	 * @param message
	 * @return
	 */
	public String modifyMessage(Message message){
		return messageDaoImpl.update(message);
	}
	
	/**
	 * 根据留言ID删除留言
	 * @param message
	 * @return
	 */
	public String delMessageById(Message message){
		return messageDaoImpl.delete(message);
	}
	
	/**
	 * 批量删除留言
	 * @param ids
	 * @return
	 */
	public String delMoreMessage(String ids){
		return messageDaoImpl.deleteMore(ids);
	}
	
	/**
	 * 获取所有留言
	 * @return
	 */
	public List<Message> getAllMessages(){
		return messageDaoImpl.findAll();
	}
	
	/**
	 * 根据名称查找留言信息
	 * @param name
	 * @return
	 */
	public List<Message> getMessagesByName(String name){
		return messageDaoImpl.findByName(name);
	}
	
	/**
	 * 获取会员能看到的留言
	 * @param customer_id
	 * @return
	 */
	public List<Message> getMessagesByCustomerId(Integer customer_id){
		return messageDaoImpl.findByCustomerId(customer_id);
	}
	
	/**
	 * 根据时间，状态进行过滤
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @return
	 */
	public List<Message> getMessagesByDateAndStatus(Date startDate, Date endDate, Integer status){
		return messageDaoImpl.filteByDateAndStatus(startDate, endDate, status);
	}
}
