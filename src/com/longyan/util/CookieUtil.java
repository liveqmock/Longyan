package com.longyan.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.longyan.entity.Customer;
import com.longyan.service.CustomerService;

/**
 * 用于会员登录cookie验证
 * 
 * @author tracyqiu
 * 
 */
public class CookieUtil {

	/**
	 * 操作cookie，判断用户是否登录
	 * 
	 * @param request
	 * @param response
	 * @param customerService
	 * @return
	 */
	public static JSONArray isLogin(HttpServletRequest request,
			CustomerService customerService) {
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject login = new JSONObject();
		JSONObject cus = new JSONObject();
		Cookie cookie = getCookieByName(request, "cid");
		
		if (cookie == null) {
			login.put("isLogin", false);
			cus.put("customer", null);
			jsonArray.add(login);
			jsonArray.add(cus);
			return jsonArray;
		}
		String cid = cookie.getValue(); // 取得cookie值
		if ("".equals(cid)) {
			login.put("isLogin", false);
			cus.put("customer", null);
			jsonArray.add(login);
			jsonArray.add(cus);
			return jsonArray;
		} else {
			Integer customer_id = Integer.parseInt(MD5.convertMD5(MD5.convertMD5(cid)));
			Customer customer = customerService.getCustomerById(customer_id);
			login.put("isLogin", true);
			cus.put("customer", customer);
			jsonArray.add(login);
			jsonArray.add(cus);
			return jsonArray;
		}
	}

	/**
	 * 设置cookie
	 * 
	 * @param response
	 * @param name cookie名字
	 * @param value cookie值
	 * @param maxAge cookie生命周期 以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0){
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}

	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
