package com.longyan.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longyan.dao.impl.MailSendLogDaoImpl;
import com.longyan.entity.MailSendLog;

/**
 * 邮件服务层
 * @author tracyqiu
 *
 */
@Service
public class MailSendLogService {

	@Autowired
	private MailSendLogDaoImpl mailSendLogDaoImpl;

	public MailSendLogDaoImpl getMailSendLogDaoImpl() {
		return mailSendLogDaoImpl;
	}

	public void setMailSendLogDaoImpl(MailSendLogDaoImpl mailSendLogDaoImpl) {
		this.mailSendLogDaoImpl = mailSendLogDaoImpl;
	}
	
	/**
	 * 添加邮件
	 * @param mailSendLog
	 * @return
	 */
	public String addMailSendLog(MailSendLog mailSendLog){
		return mailSendLogDaoImpl.insert(mailSendLog);
	}
	
	/**
	 * 修改邮件信息
	 * @param mailSendLog
	 * @return
	 */
	public String modifyMailSendLog(MailSendLog mailSendLog){
		return mailSendLogDaoImpl.update(mailSendLog);
	}
	
	/**
	 * 根据邮件ID删除邮件
	 * @param mailSendLog
	 * @return
	 */
	public String delMailSendLogById(MailSendLog mailSendLog){
		return mailSendLogDaoImpl.delete(mailSendLog);
	}
	
	/**
	 * 批量删除邮件
	 * @param ids
	 * @return
	 */
	public String delMoreMailSendLog(String ids){
		return mailSendLogDaoImpl.deleteMore(ids);
	}
	
	/**
	 * 获取所有邮件
	 * @return
	 */
	public List<MailSendLog> getAllMailSendLogs(){
		return mailSendLogDaoImpl.findAll();
	}
	
	/**
	 * 根据title查找邮件信息
	 * @param name
	 * @return
	 */
	public List<MailSendLog> getMailSendLogsByTitle(String title){
		return mailSendLogDaoImpl.findByName(title);
	}
	
	/**
	 * 根据时间区间查询邮件信息
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<MailSendLog> getMailSendLogsByDate(Date startDate, Date endDate){
		return mailSendLogDaoImpl.findByDate(startDate, endDate);
	}
}
