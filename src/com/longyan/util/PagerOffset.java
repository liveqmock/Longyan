package com.longyan.util;

import java.util.List;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
/**
 * 得到当前的页码
 * @author tracyqiu
 *
 */
public class PagerOffset implements TemplateMethodModel {

	@Override
	public Object exec(List args) throws TemplateModelException {
		String uri = (String) args.get(0);
		String[] string = uri.split("pager_offset=");
		if (string.length == 1) {
			return 1;
		} else {
			Integer pager_offset = 1;
			try {
				pager_offset = Integer.parseInt(string[1]);
			} catch (NumberFormatException e) {
				pager_offset = 1;
			}
			return pager_offset;
		}
	}
}

