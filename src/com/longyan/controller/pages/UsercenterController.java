package com.longyan.controller.pages;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longyan.entity.Customer;
import com.longyan.entity.Order;
import com.longyan.service.CustomerService;
import com.longyan.service.OrderService;
import com.longyan.util.CookieUtil;
import com.longyan.util.MD5;

/**
 * 用户个人中心
 * @author tracyqiu
 *
 */
@Controller
public class UsercenterController {
	private final int PAGE_SIZE = 20; 

	@Resource
	private CustomerService customerService; 
	
	@Resource
	private OrderService orderService; 
	
	/**
	 * 跳转到个人中心页面
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/usercenter/userinfo", method={RequestMethod.GET, RequestMethod.POST})
	public String userinfo(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		model.addAttribute("pageTitle", "个人资料");
		model.addAttribute("dim", "userinfo");
		System.out.println("进入个人中心页面");
		String templeteUrl = initModel(request, model, "pages/usercenter/userinfo");
		return templeteUrl;
	}
	
	/**
	 * 跳转到修改密码
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/usercenter/modify-pass", method={RequestMethod.GET, RequestMethod.POST})
	public String modifyPass(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		model.addAttribute("pageTitle", "密码修改");
		model.addAttribute("dim", "password");
		System.out.println("进入修改密码页面");
		String templeteUrl = initModel(request, model, "pages/usercenter/update-pass");
		return templeteUrl;
	}
	
	/**
	 * 跳转到我的订单
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/usercenter/my-order", method={RequestMethod.GET, RequestMethod.POST})
	public String myOrder(
			Model model,
			Integer pager_offset,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		model.addAttribute("pageTitle", "我的订单");
		model.addAttribute("dim", "order");
		System.out.println("进入我的订单页面");
		String templeteUrl = initModel(request, model, "pages/usercenter/my-order");
		
		if(pager_offset == null){
			pager_offset = 1;
		}
		if(templeteUrl.indexOf("jump") == -1){  //说明取到用户了
			Customer customer = getCustomerFromCookie(request);
			int start = (pager_offset - 1) * PAGE_SIZE;
			int totalCount = orderService.getOrderCountByCustomerId(customer.getId());
			List<Order> orderList = orderService.getOrdersByCustomerId(customer.getId(), start, PAGE_SIZE);
			
			model.addAttribute("orderList", orderList);
			model.addAttribute("request", request);
			model.addAttribute("totalCount", totalCount);
			model.addAttribute("pageSize", PAGE_SIZE);
		}
		return templeteUrl;
	}
	
	/**
	 * 用户自己修改
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/customer/update-customer")
	public @ResponseBody String updateCustomer(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Customer customer = getCustomerFromCookie(request);
		
		String username = request.getParameter("username");
		String telephone = request.getParameter("telephone");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String address = request.getParameter("address");
		String qq = request.getParameter("qq");
		String code = "";
		
		if(customer != null){
			customer.setUsername(username);
			customer.setTelephone(telephone);
			customer.setSex(sex);
			customer.setBirthday(birthday);
			customer.setAddress(address);
			customer.setQq(qq);
			
			code = customerService.modifyCustomer(customer);
		}else {
			code = "2004";   //cookie已失效
		}
		
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 2001:
				err_msg = "update success";
				break;
			case 2002:
				err_msg = "the employer does not exist!";
				break;
			case 2003:
				err_msg = "unkown error!";
				break;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("msg", err_msg);
		
		return jsonObject.toString();
	}
	
	/**
	 * 用户自己密码
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/customer/update-password")
	public @ResponseBody String updatePassword(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Customer customer = getCustomerFromCookie(request);
		
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		String code = "";
		
		if(customer != null){
			if(MD5.verifyPassword(password, customer.getPassword())){
				code = customerService.modifyPassword(customer, newPassword);
			}else{
				code = "2005";
			}
		}else {
			code = "2004";   //cookie已失效
		}
		
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 2001:
				err_msg = "update success";
				break;
			case 2002:
				err_msg = "the employer does not exist!";
				break;
			case 2003:
				err_msg = "unkown error!";
				break;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("msg", err_msg);
		
		return jsonObject.toString();
	}
	
	
	/**
	 * 初始化公用model
	 * @param request
	 * @param model
	 */
	private String initModel(HttpServletRequest request, Model model, String retUrl){
		//初始化用户登录信息
		Customer customer = null;
		Integer customer_id = -1;
		String customer_name = "";
		
		JSONArray jsonArray = CookieUtil.isLogin(request, customerService);
		JSONObject loginJson = (JSONObject) jsonArray.get(0);
		JSONObject customerJson = (JSONObject) jsonArray.get(1);
		if(Boolean.parseBoolean(loginJson.get("isLogin").toString())){
			customer = customerService.getCustomerById((Integer)customerJson.get("customer_id"));
			customer_id = customer.getId();
			customer_name = customer.getRealname();
		}else {
			return "pages/jump";
		}
		
		model.addAttribute("customer_name", customer_name);
		model.addAttribute("customer_id", customer_id);
		model.addAttribute("customer", customer);
		return retUrl;
	}
	
	/**
	 * 从cookie中取得用户
	 * @param request
	 * @return
	 */
	private Customer getCustomerFromCookie(HttpServletRequest request){
		Customer customer = null;
		JSONArray jsonArray = CookieUtil.isLogin(request, customerService);
		JSONObject loginJson = (JSONObject) jsonArray.get(0);
		JSONObject customerJson = (JSONObject) jsonArray.get(1);
		
		if(Boolean.parseBoolean(loginJson.get("isLogin").toString())){
			customer = customerService.getCustomerById((Integer)customerJson.get("customer_id"));
		}

		return customer;
	}
}
