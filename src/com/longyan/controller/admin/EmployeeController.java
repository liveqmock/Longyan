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

import com.longyan.entity.Employee;
import com.longyan.service.EmployeeService;
import com.longyan.service.PermissionService;
import com.longyan.util.MD5;
import com.longyan.util.SessionUtil;

/**
 * 员工管理页面
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class EmployeeController {
	@Resource
	private PermissionService permissionService; 
	
	@Resource
	private EmployeeService employeeService; 

	/**
	 * 跳转到登录页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/employee", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String employee(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String dim = "employee"; //声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		//员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 会员管理。
		Integer right = employee.getRight_level(); 

		model.addAttribute("username", employee.getName());
		model.addAttribute("right", right);
		model.addAttribute("dim", dim);
		model.addAttribute("permission", permissionService.getPermissionByEmployeeId(employee.getId()) == null ? 
				"" : permissionService.getPermissionByEmployeeId(employee.getId()).getColumn_ids());
		
		System.out.println("到达主页面");
		return "admin/filter/employee";
	}
	
	/**
	 * 取得所有员工
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-all-employee")
	public @ResponseBody String getAllEmployee(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		List<Employee> employees = employeeService.getAllEmployees();
		if(employees.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Employee employee:employees){
			jsonObject = new JSONObject();
			
			jsonObject.put("id", employee.getId());
			jsonObject.put("name", employee.getName());
			jsonObject.put("id_card", employee.getId_card());
			jsonObject.put("telephone", employee.getTelephone());
			jsonObject.put("sex", employee.getSex());
			jsonObject.put("qq", employee.getQq());
			jsonObject.put("email", employee.getEmail());
			jsonObject.put("birthday", employee.getBirthday());
			jsonObject.put("address", employee.getAddress());
			jsonObject.put("province", employee.getProvince());
			jsonObject.put("right_level", employee.getRight_level());
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	/**
	 * 通过ID取得员工
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-employee-by-id")
	public @ResponseBody String getEmployeeById(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		Employee employee = employeeService.getEmployeeById(id);
		
		if(employee == null){
			return "[]";
		}
		
		JSONObject jsonObject = new JSONObject();
			
		jsonObject.put("name", employee.getName());
		jsonObject.put("id_card", employee.getId_card());
		jsonObject.put("telephone", employee.getTelephone());
		jsonObject.put("sex", employee.getSex());
		jsonObject.put("qq", employee.getQq());
		jsonObject.put("email", employee.getEmail());
		jsonObject.put("birthday", employee.getBirthday());
		jsonObject.put("address", employee.getAddress());
		jsonObject.put("province", employee.getProvince());
		jsonObject.put("right_level", employee.getRight_level());
			
		return jsonObject.toString();
	}
	
	/**
	 * 新增员工
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/add-employee")
	public @ResponseBody String addEmployee(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String name = request.getParameter("name");
		String id_card = request.getParameter("id_card");
		String telephone = request.getParameter("telephone");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String address = request.getParameter("address");
		String qq = request.getParameter("qq");
		String email = request.getParameter("email");
		String province = request.getParameter("province");
		int right_level = Integer.parseInt(request.getParameter("right_level"));
		//员工信息由管理员录入，默认密码为6个1
		String password = "111111";
		
		Employee employee = new Employee();
		
		employee.setName(name);
		employee.setId_card(id_card);
		employee.setPassword(password);
		employee.setTelephone(telephone);
		employee.setSex(sex);
		employee.setBirthday(birthday);
		employee.setAddress(address);
		employee.setQq(qq);
		employee.setEmail(email);
		employee.setProvince(province);
		employee.setRight_level(right_level);
		
		String code = employeeService.addEmployee(employee);
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
	 * 删除员工
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/del-employee")
	public @ResponseBody String delEmployee(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String ids = request.getParameter("employeeIds");
		
		String code = employeeService.delMoreEmployee(ids);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 3001:
				err_msg = "del success";
				break;
			case 3002:
				err_msg = "the employer does not exist!";
				break;
			case 3003:
				err_msg = "unkown error!";
				break;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("msg", err_msg);
		
		return jsonObject.toString();
	}
	
	/**
	 * 修改员工信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/update-employee")
	public @ResponseBody String updateEmployee(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String id_card = request.getParameter("id_card");
		String telephone = request.getParameter("telephone");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String address = request.getParameter("address");
		String qq = request.getParameter("qq");
		String email = request.getParameter("email");
		String province = request.getParameter("province");
		int right_level = Integer.parseInt(request.getParameter("right_level"));
		
		Employee employee = employeeService.getEmployeeById(id);
		employee.setName(name);
		employee.setId_card(id_card);
		employee.setTelephone(telephone);
		employee.setSex(sex);
		employee.setBirthday(birthday);
		employee.setAddress(address);
		employee.setQq(qq);
		employee.setEmail(email);
		employee.setProvince(province);
		employee.setRight_level(right_level);
		
		String code = employeeService.modifyEmployee(employee);
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
	 * 修改员工密码
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/update-employee-password")
	public @ResponseBody String updateEmployeePassword(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		
		Employee employee = employeeService.getEmployeeById(id);
		JSONObject jsonObject = new JSONObject();
		
		if(!MD5.verifyPassword(oldPassword, employee.getPassword())){
			jsonObject.put("code", 1004);
			jsonObject.put("msg", "原密码错误");
		}else {
			String code = employeeService.modifyPassword(employee, newPassword);
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
			
			jsonObject.put("code", code);
			jsonObject.put("msg", err_msg);
		}
		
		return jsonObject.toString();
	}
}
