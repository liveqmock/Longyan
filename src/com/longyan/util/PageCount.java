package com.longyan.util;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 根据传入的参数，计算出所有的页数
 * @author tracyqiu
 */
public class PageCount implements TemplateMethodModel {

	@Override
	public Object exec(List args) throws TemplateModelException {
		Integer totalCount = 0;
		Integer pageSize = 0;
		try {
			totalCount = Integer.parseInt((String) args.get(0));
			pageSize = Integer.parseInt((String) args.get(1));
		} catch (NumberFormatException e) {
			throw new TemplateModelException("请输入正确的总记录数和页面记录数");
		}
		Integer pageCount = totalCount / pageSize
				+ (totalCount % pageSize == 0 ? 0 : 1);
		return pageCount;
	}

}


