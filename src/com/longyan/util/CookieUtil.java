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
		String cookie_str = cookie.getValue(); // 取得cookie值
		String userInfo[] = cookie_str.split("#");
		
		if (userInfo.length != 2 || (userInfo.length != 2 && ("".equals(userInfo[0]) || "".equals(userInfo[1])))) {
			login.put("isLogin", false);
			cus.put("customer", null);
			jsonArray.add(login);
			jsonArray.add(cus);
			return jsonArray;
		} else {
			Customer customer = customerService.getCustomerById(Integer.parseInt(userInfo[0]));
			Integer customer_id = -1;
			
			if(customer != null) {
				customer_id = Integer.parseInt(userInfo[0]);
			}
			login.put("isLogin", true);
			cus.put("customer_id", customer_id);
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
	
	 /**
     * 清除cookie
     * @param req
     * @param res
     * @param name
     */
    public static void remove(HttpServletRequest req,HttpServletResponse res,String name) {
        Cookie cookieName =  getCookieByName(req, name);
        if(null != cookieName) {
            Cookie cookie = new Cookie(cookieName.getName(),null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            res.addCookie(cookie);
        }
    }
	
}
