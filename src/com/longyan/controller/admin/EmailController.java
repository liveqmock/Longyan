package com.longyan.controller.admin;

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

import com.longyan.entity.MailSendLog;
import com.longyan.entity.Employee;
import com.longyan.service.MailSendLogService;
import com.longyan.service.PermissionService;
import com.longyan.util.SessionUtil;

/**
 * 员工管理页面
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class EmailController {
	@Resource
	private PermissionService permissionService; 
	
	@Resource
	private MailSendLogService mailSendLogService; 

	/**
	 * 跳转到登录页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/email", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String mailSendLog(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String dim = "mailSendLog"; //声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		//员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 会员管理。
		Integer right = employee.getRight_level(); 

		model.addAttribute("username", employee.getName());
		model.addAttribute("right", right);
		model.addAttribute("dim", dim);
		model.addAttribute("permission", permissionService.getPermissionByEmployeeId(employee.getId()) == null ? 
				"" : permissionService.getPermissionByEmployeeId(employee.getId()).getColumn_ids());
		
		System.out.println("到达主页面");
		return "admin/filter/email";
	}
	
}
