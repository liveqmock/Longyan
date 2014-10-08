package com.longyan.dao;

import java.util.Date;
import java.util.List;

import com.longyan.entity.MailSendLog;

/**
 * 邮件信息
 * @author tracyqiu
 *
 */
public interface MailSendLogDao {

	/**
	 * 新增邮件信息
	 * @param mailSendLog
	 * @return
	 */
	public String insert(MailSendLog mailSendLog);
	
	/**
	 * 更新服客信息
	 * @param mailSendLog
	 * @return
	 */
	public String update(MailSendLog mailSendLog);
	
	/**
	 * 删除邮件信息
	 * @param mailSendLog
	 * @return
	 */
	public String delete(MailSendLog mailSendLog);
	
	/**
	 * 删除多条记录
	 * @param ids
	 * @return
	 */
	public String deleteMore(String ids);
	
	/**
	 * 根据ID查找邮件
	 * @param id
	 * @return
	 */
	public MailSendLog findById(Integer id);
	
	/**
	 * 取得所有邮件
	 * @return
	 */
	public List<MailSendLog> findAll();
	
	/**
	 * 根据title查找相关邮件
	 * @param name
	 * @return
	 */
	public List<MailSendLog> findByName(String name);
	
	/**
	 * 根据日期区间查询相关邮件
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<MailSendLog> findByDate(Date startDate, Date endDate);
}
