package com.longyan.controller.admin;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longyan.entity.Employee;
import com.longyan.service.EmployeeService;
import com.longyan.util.SessionUtil;

/**
 * 后台登录
 * @author tracyqiu
 *
 */
@Controller
public class LoginController {
	@Resource
	private EmployeeService employeeService;
	/**
	 * 跳转到登录页面
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/admin/login", method={RequestMethod.GET, RequestMethod.POST})
	public String login(Model model,
			HttpServletResponse response) throws IOException {
		
		System.out.println("进入登录页面");
		return "admin/login";
	}
	
	/**登录验证
	 */
	@RequestMapping("/admin/loginCheck")
	public @ResponseBody String loginCheck(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Employee emp = employeeService.checkLogin(username, password);
		JSONObject jsonObject = new JSONObject();
		if(emp != null) {  //登录人存在
			//添加session
			SessionUtil.setSession(emp, response, request);
			jsonObject.put("code", 1);
		} else {
			jsonObject.put("code", 0);
		}
		
		return jsonObject.toString();
	}
	
	/**
	 * 登出操作
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/admin/logout", method={RequestMethod.GET, RequestMethod.POST})
	public void login(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		SessionUtil.clearSession(response, request);
		response.sendRedirect(request.getContextPath() + "/admin/login");
	}
}
