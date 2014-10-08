package com.longyan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longyan.dao.impl.AttachmentsDaoImpl;
import com.longyan.entity.Attachments;

/**
 * 附件服务层
 * @author tracyqiu
 *
 */
@Service
public class AttachmentsService {

	@Autowired
	private AttachmentsDaoImpl attachmentsDaoImpl;

	public AttachmentsDaoImpl getAttachmentsDaoImpl() {
		return attachmentsDaoImpl;
	}

	public void setAttachmentsDaoImpl(AttachmentsDaoImpl attachmentsDaoImpl) {
		this.attachmentsDaoImpl = attachmentsDaoImpl;
	}
	
	/**
	 * 添加附件
	 * @param attachments
	 * @return
	 */
	public String addAttachments(Attachments attachments){
		return attachmentsDaoImpl.insert(attachments);
	}
	
	/**
	 * 修改附件信息
	 * @param attachments
	 * @return
	 */
	public String modifyAttachments(Attachments attachments){
		return attachmentsDaoImpl.update(attachments);
	}
	
	/**
	 * 取得栏目下的所有附件
	 * @param column_id
	 * @return
	 */
	public List<Attachments> getAttachmentsByColumnId(Integer column_id){
		return attachmentsDaoImpl.getByColumnId(column_id);
	}
	
	/**
	 * 根据附件ID删除附件
	 * @param attachments
	 * @return
	 */
	public String delAttachmentsById(Attachments attachments){
		return attachmentsDaoImpl.delete(attachments);
	}
	
	/**
	 * 批量删除附件
	 * @param ids
	 * @return
	 */
	public String delMoreAttachment(String ids){
		return attachmentsDaoImpl.deleteMore(ids);
	}
}
