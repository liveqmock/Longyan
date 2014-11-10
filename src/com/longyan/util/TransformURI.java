package com.longyan.util;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
/**
 * 根据传入的uri，在uri后面加上分页参数
 * @author tracyqiu
 *
 */
public class TransformURI implements TemplateMethodModel{

	@Override
	public Object exec(List args) throws TemplateModelException {
		String uri = (String) args.get(0);
		int n = uri.lastIndexOf("?");
		if (n == -1) {
			return uri + "?pager_offset=";
		}
		if (uri.lastIndexOf("?pager_offset") != -1) {
			uri = uri.substring(0, uri.lastIndexOf("=") + 1);
			return uri;
		}
		String queryString = uri.substring(n + 1, uri.length());
		String suburi = uri.substring(0, n + 1);
		String[] strings = queryString.split("&");
		for (int i = 0; i < strings.length; i++) {
			if (strings[i].startsWith("pager_offset")) {
				continue;
			}
			suburi += strings[i];
			suburi += "&pager_offset=";
		}
		return suburi;
	}
	
	
	
}
