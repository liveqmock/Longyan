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

import com.longyan.entity.Permission;
import com.longyan.entity.Employee;
import com.longyan.service.EmployeeService;
import com.longyan.service.PermissionService;
import com.longyan.util.SessionUtil;

/**
 * 权限管理页面
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class PermissionController {
	@Resource
	private PermissionService permissionService; 
	
	@Resource
	private EmployeeService employeeService; 

	/**
	 * 没有权限
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/unauthorized", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String unauthorized(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		return "admin/unauthorized";
	};
	
	/**
	 * 跳转到登录页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/permission", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String permission(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String dim = "permission"; //声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		//员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 客服管理。
		Integer right = employee.getRight_level(); 

		model.addAttribute("username", employee.getName());
		model.addAttribute("right", right);
		model.addAttribute("dim", dim);
		model.addAttribute("permission", permissionService.getPermissionByEmployeeId(employee.getId()) == null ? 
				"" : permissionService.getPermissionByEmployeeId(employee.getId()).getColumn_ids());
		
		System.out.println("到达主页面");
		return "admin/filter/permission";
	};
	
	/**
	 * 取得所有
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-all-permission")
	public @ResponseBody String getAllPermission(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		List<Employee> employees = employeeService.getAllEmployees();
		if(employees.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		Permission permission = null;
		for(Employee employee:employees){
			jsonObject = new JSONObject();
			permission = permissionService.getPermissionByEmployeeId(employee.getId());
			
			jsonObject.put("employee_id", employee.getId());
			jsonObject.put("column_ids", permission == null ? "" : permission.getColumn_ids());
			jsonObject.put("employee", employee.getName());
			jsonObject.put("telephone", employee.getTelephone());
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	
	/**
	 * 修改权限
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/update-or-add-permission")
	public @ResponseBody String updatePermission(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Integer employee_id = Integer.parseInt(request.getParameter("employee_id"));
		String column_ids = request.getParameter("column_ids");
		String code = "";
		
		Permission permission = permissionService.getPermissionByEmployeeId(employee_id);
		if(permission == null){
			permission = new Permission();
			permission.setEmployee_id(employee_id);
			permission.setColumn_ids(column_ids);
			
			code = permissionService.addPermission(permission);
		}else {
			permission.setColumn_ids(column_ids);
			code = permissionService.modifyPermission(permission);
		}
		
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 1001:
				err_msg = "add success";
				break;
			case 1002:
				err_msg = "the permission already exist!";
				break;
			case 1003:
				err_msg = "unkown error!";
				break;
			case 2001:
				err_msg = "update success";
				break;
			case 2002:
				err_msg = "the permission does not exist!";
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
	
}
