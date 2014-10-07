package com.longyan.dao;

import java.util.List;

import com.longyan.entity.Attachments;

/**
 *附件处理
 * @author tracyqiu
 *
 */
public interface AttachmentsDao {

	/**
	 * 添加附件
	 * @param attachments
	 * @return
	 */
	public String insert(Attachments attachments);
	
	/**
	 * 更新附件信息
	 * @param attachments
	 * @return
	 */
	public String update(Attachments attachments);
	
	/**
	 * 通过附件路径获取附件
	 * @param path
	 * @return
	 */
	public Attachments getByPath(String path);
	
	/**
	 * 取得某个栏目下的所有附件
	 * @param column_id
	 * @return
	 */
	public List<Attachments> getByColumnId(Integer column_id);
	
	/**
	 * 删除某个附件
	 * @param attachments
	 * @return
	 */
	public String delete(Attachments attachments);
}
