package com.longyan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longyan.dao.impl.ContentDaoImpl;
import com.longyan.entity.Content;

/**
 * 二级内容服务层封装
 * @author tracyqiu
 *
 */
@Service
public class ContentService {

	@Autowired
	private ContentDaoImpl contentDaoImpl;

	public ContentDaoImpl getContentDaoImpl() {
		return contentDaoImpl;
	}

	public void setContentDaoImpl(ContentDaoImpl contentDaoImpl) {
		this.contentDaoImpl = contentDaoImpl;
	}
	
	/**
	 * 添加二级内容
	 * @param content
	 * @return
	 */
	public String addContent(Content content){
		return contentDaoImpl.insert(content);
	}
	
	/**
	 * 删除二级内容
	 * @param content
	 * @return
	 */
	public String delContent(Content content){
		return contentDaoImpl.delete(content);
	}
	
	/**
	 * 批量删除二级内容
	 * @param ids
	 * @return
	 */
	public String delMoreContent(String ids){
		return contentDaoImpl.deleteMore(ids);
	}
	
	/**
	 * 更新二级内容信息
	 * @param content
	 * @return
	 */
	public String modifyContent(Content content){
		return contentDaoImpl.update(content);
	}
	
	/**
	 * 通过ID获取二级内容
	 * @param id
	 * @return
	 */
	public Content getContentById(Integer id){
		return contentDaoImpl.findById(id);
	}
	
	/**
	 * 通过名称查找二级内容
	 * @param name
	 * @return
	 */
	public List<Content> getContentsByTitle(String title){
		return contentDaoImpl.findByName(title);
	}
	
	/**
	 * 获取某个栏目下的所有二级内容
	 * @param site_id
	 * @return
	 */
	public List<Content> getContentsByColumnId(Integer column_id, Integer start, Integer count){
		return contentDaoImpl.findByColumnId(column_id, start, count);
	}
	
	/**
	 * 获取某个栏目下的所有二级内容
	 * @param site_id
	 * @return
	 */
	public List<Content> getContentsByColumnId(Integer column_id){
		return contentDaoImpl.findByColumnId(column_id);
	}
	
	/**
	 * 取得所有二级内容
	 * @return
	 */
	public List<Content> getAllContents(){
		return contentDaoImpl.findAll();
	}
}
