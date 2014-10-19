package com.longyan.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 设置统一的字符集编码控制类
 * @author tracyqiu
 *
 */
public class EncodeFilter implements Filter {
	private String charEncode = null;

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		servletResponse.setCharacterEncoding("UTF-8");
		servletRequest.setCharacterEncoding(charEncode);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.charEncode = filterConfig.getInitParameter("charEncode");
	}

}
