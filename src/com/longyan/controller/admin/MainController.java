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
 * 后台主界面
 * @author tracyqiu
 *
 */
@Controller
public class MainController {
	@Resource
	private EmployeeService employeeService;
	/**
	 * 跳转到登录页面
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/admin/filter/main", method={RequestMethod.GET, RequestMethod.POST})
	public String main(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Employee employee = (Employee)SessionUtil.getSession(response, request);// 登录人
		
		model.addAttribute("username", employee.getName());
		System.out.println("到达主页面");
		return "admin/filter/main";
	}
	
}
