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

import com.longyan.entity.Customer;
import com.longyan.entity.Employee;
import com.longyan.service.CustomerService;
import com.longyan.service.PermissionService;
import com.longyan.util.MD5;
import com.longyan.util.SessionUtil;

/**
 * 会员管理页面
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class CustomerController {
	@Resource
	private PermissionService permissionService; 
	
	@Resource
	private CustomerService customerService; 

	/**
	 * 跳转到登录页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/customer", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String customer(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String dim = "customer"; //声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		//员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 会员管理。
		Integer right = employee.getRight_level(); 

		model.addAttribute("username", employee.getName());
		model.addAttribute("right", right);
		model.addAttribute("dim", dim);
		model.addAttribute("permission", permissionService.getPermissionByEmployeeId(employee.getId()) == null ? 
				"" : permissionService.getPermissionByEmployeeId(employee.getId()).getColumn_ids());
		
		System.out.println("到达主页面");
		return "admin/filter/customer";
	}
	
	/**
	 * 取得所有用户
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-all-customer")
	public @ResponseBody String getAllCustomer(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		List<Customer> customers = customerService.getAllCustomers();
		if(customers.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Customer customer:customers){
			jsonObject = new JSONObject();
			
			jsonObject.put("id", customer.getId());
			jsonObject.put("username", customer.getUsername());
			jsonObject.put("realname", customer.getRealname());
			jsonObject.put("telephone", customer.getTelephone());
			jsonObject.put("sex", customer.getSex());
			jsonObject.put("qq", customer.getQq());
			jsonObject.put("email", customer.getEmail());
			jsonObject.put("birthday", customer.getBirthday());
			jsonObject.put("address", customer.getAddress());
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	/**
	 * 根据名字查询用户
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-customer-by-name")
	public @ResponseBody String getCustomerByName(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String name = request.getParameter("customer_name");
		if("".equals(name)){
			return "[]";
		}
		
		List<Customer> customers = customerService.getCustomersByName(name);
		if(customers.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Customer customer:customers){
			jsonObject = new JSONObject();
			
			jsonObject.put("id", customer.getId());
			jsonObject.put("realname", customer.getRealname());
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	/**
	 * 通过ID取得用户
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-customer-by-id")
	public @ResponseBody String getCustomerById(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		Customer customer = customerService.getCustomerById(id);
		
		if(customer == null){
			return "[]";
		}
		
		JSONObject jsonObject = new JSONObject();
			
		jsonObject.put("username", customer.getUsername());
		jsonObject.put("realname", customer.getRealname());
		jsonObject.put("telephone", customer.getTelephone());
		jsonObject.put("sex", customer.getSex());
		jsonObject.put("qq", customer.getQq());
		jsonObject.put("email", customer.getEmail());
		jsonObject.put("birthday", customer.getBirthday());
		jsonObject.put("address", customer.getAddress());
			
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
	@RequestMapping("/admin/filter/add-customer")
	public @ResponseBody String addCustomer(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String username = request.getParameter("username");
		String realname = request.getParameter("realname");
		int flag = Integer.parseInt(request.getParameter("flag"));   //1表示管理员添加   2表示用户自己注册
		String telephone = request.getParameter("telephone");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String address = request.getParameter("address");
		String qq = request.getParameter("qq");
		String email = request.getParameter("email");
		
		//如果后台管理员新增会员，默认密码为6个1
		String password = flag == 1 ? "111111" : request.getParameter("password");
		
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
	 * 删除用户
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/del-customer")
	public @ResponseBody String delCustomer(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String ids = request.getParameter("customerIds");
		
		String code = customerService.delMoreCustomer(ids);
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
	 * 修改用户
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/update-customer")
	public @ResponseBody String updateCustomer(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		String username = request.getParameter("username");
		String realname = request.getParameter("realname");
		String telephone = request.getParameter("telephone");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String address = request.getParameter("address");
		String qq = request.getParameter("qq");
		String email = request.getParameter("email");
		
		Customer customer = customerService.getCustomerById(id);
		customer.setUsername(username);
		customer.setRealname(realname);
		customer.setTelephone(telephone);
		customer.setSex(sex);
		customer.setBirthday(birthday);
		customer.setAddress(address);
		customer.setQq(qq);
		customer.setEmail(email);
		
		String code = customerService.modifyCustomer(customer);
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
	 * 修改用户密码
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/update-customer-password")
	public @ResponseBody String updateCustomerPassword(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		
		Customer customer = customerService.getCustomerById(id);
		JSONObject jsonObject = new JSONObject();
		
		if(!MD5.verifyPassword(oldPassword, customer.getPassword())){
			jsonObject.put("code", 1004);
			jsonObject.put("msg", "原密码错误");
		}else {
			String code = customerService.modifyPassword(customer, newPassword);
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
