package com.longyan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longyan.dao.impl.TemplateDaoImpl;
import com.longyan.entity.Template;

/**
 * 模板服务层
 * @author tracyqiu
 *
 */
@Service
public class TemplateService {

	@Autowired
	private TemplateDaoImpl templateDaoImpl;

	public TemplateDaoImpl getTemplateDaoImpl() {
		return templateDaoImpl;
	}

	public void setTemplateDaoImpl(TemplateDaoImpl templateDaoImpl) {
		this.templateDaoImpl = templateDaoImpl;
	}
	
	/**
	 * 添加模板
	 * @param template
	 * @return
	 */
	public String addTemplate(Template template){
		return templateDaoImpl.insert(template);
	}
	
	/**
	 * 修改模板信息
	 * @param template
	 * @return
	 */
	public String modifyTemplate(Template template){
		return templateDaoImpl.update(template);
	}
	
	/**
	 * 根据模板ID删除模板
	 * @param template
	 * @return
	 */
	public String delTemplateById(Template template){
		return templateDaoImpl.delete(template);
	}
	
	/**
	 * 通过栏目ID取得模板
	 * @param column_id
	 * @return
	 */
	public Template getTemplateByColumnId(Integer column_id){
		return templateDaoImpl.findByColumnId(column_id);
	}
	
	/**
	 * 通过内容ID取得模板
	 * @param content_id
	 * @return
	 */
	public Template getTemplateByContentId(Integer content_id){
		return templateDaoImpl.findByContentId(content_id);
	}
}
