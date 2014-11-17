package com.longyan.controller.pages;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longyan.entity.Customer;
import com.longyan.service.CustomerService;
import com.longyan.util.CookieUtil;
import com.longyan.util.MD5;

/**
 * 登录注册
 * @author tracyqiu
 *
 */
@Controller
public class LoginOrJoinController {

	@Resource
	private CustomerService customerService; 
	
	/**
	 * 跳转到注册页面
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/join", method={RequestMethod.GET, RequestMethod.POST})
	public String join(Model model,
			HttpServletResponse response) throws IOException {
		
		System.out.println("进入注册页面");
		return "pages/join";
	}
	
	/**
	 * 跳转到注册页面
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/login", method={RequestMethod.GET, RequestMethod.POST})
	public String login(Model model,
			HttpServletResponse response) throws IOException {
		
		System.out.println("进入登录页面");
		return "pages/login";
	}
	
	/**
	 * 检查邮箱是否已经被注册过了
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/customer-email-check")
	public @ResponseBody String checkEmail(
			Model model, 
			String email,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if( null == email) {
			email = "";
		}
		
		JSONObject jsonObject = new JSONObject();
		List<Customer> customers = customerService.getCustomersByEmail(email);
		if(customers.size() == 0){
			jsonObject.put("code", 0);
		}else {
			jsonObject.put("code", 1);
		}
		
		return jsonObject.toString();
	}
	
	/**
	 * 新增用户
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/add-customer")
	public @ResponseBody String addCustomer(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String username = request.getParameter("username");
		String realname = request.getParameter("realname");
		String password = request.getParameter("password");
		String telephone = request.getParameter("telephone");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String address = request.getParameter("address");
		String qq = request.getParameter("qq");
		String email = request.getParameter("email");
		
		Customer customer = new Customer();
		customer.setUsername(username);
		customer.setRealname(realname);
		customer.setPassword(password);
		customer.setTelephone(telephone);
		customer.setSex(sex);
		customer.setBirthday(birthday);
		customer.setAddress(address);
		customer.setQq(qq);
		customer.setEmail(email);
		
		String code = customerService.addCustomer(customer);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 1001:
				err_msg = "add success";
				break;
			case 1002:
				err_msg = "the employer already exist!";
				break;
			case 1003:
				err_msg = "unkown error!";
				break;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("msg", err_msg);
		
		return jsonObject.toString();
	}
	
	/**
	 * 检查邮箱是否已经被注册过了
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/customer-login-check")
	public @ResponseBody String checkLogin(
			Model model, 
			String username,
			String password,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		if( null == username) {
			username = "";
		}
		
		if( null == password) {
			password = "";
		}
		
		password = MD5.getMD5ofStr(password);
		JSONObject jsonObject = new JSONObject();
		Customer customer = customerService.getCustomerByNameAndPass(username, password);
		if(customer == null){
			jsonObject.put("code", 1);
		}else {
			CookieUtil.addCookie(response, "cid", customer.getId() + "#" + MD5.getMD5ofStr(customer.getUsername()), 60 * 60);
			jsonObject.put("code", 0);
		}
		
		return jsonObject.toString();
	}
	
	/**
	 * 退出登录
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/logout")
	public @ResponseBody String logout(
			Model model, 
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		JSONObject jsonObject = new JSONObject();
		CookieUtil.remove(request, response, "cid");
		
		jsonObject.put("code", 0);
		return jsonObject.toString();
	}
}
