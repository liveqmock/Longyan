package com.longyan.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 处理登录
 * 
 * @author tracyqiu
 * 
 */
public class SessionUtil {

	/**
	 * 判断是否已登录
	 * 
	 * @param sessionId
	 * @param response
	 * @param request
	 * @return
	 */
	public static Object getSession(HttpServletResponse response,
			HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		Object user = (Object) session.getAttribute("user");

		return user;
	}

	/**
	 * 登录成功写入session
	 * @param sessionId
	 * @param response
	 * @param request
	 */
	public static void setSession(Object user,
			HttpServletResponse response, HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		session.setAttribute("user", user);
	}
}
