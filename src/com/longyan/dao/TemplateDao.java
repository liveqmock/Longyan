package com.longyan.dao;

import com.longyan.entity.Template;

/**
 * 模板信息
 * @author tracyqiu
 *
 */
public interface TemplateDao {

	/**
	 * 新增模板信息
	 * @param template
	 * @return
	 */
	public String insert(Template template);
	
	/**
	 * 更新模板信息
	 * @param template
	 * @return
	 */
	public String update(Template template);
	
	/**
	 * 删除模板信息
	 * @param template
	 * @return
	 */
	public String delete(Template template);
	
	/**
	 * 根据栏目ID查找模板
	 * @param id
	 * @return
	 */
	public Template findByColumnId(Integer column_id);
	
	/**
	 * 根据内容ID查找模板
	 * @param id
	 * @return
	 */
	public Template findByContentId(Integer content_id);
}
