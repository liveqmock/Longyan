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

import com.longyan.entity.Contact;
import com.longyan.entity.Employee;
import com.longyan.service.ContactService;
import com.longyan.service.PermissionService;
import com.longyan.util.SessionUtil;

/**
 * 客服管理页面
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class ContactController {
	@Resource
	private PermissionService permissionService; 
	
	@Resource
	private ContactService contactService; 

	/**
	 * 跳转到登录页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/contact", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String contact(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String dim = "contact"; //声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		//员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 客服管理。
		Integer right = employee.getRight_level(); 

		model.addAttribute("username", employee.getName());
		model.addAttribute("right", right);
		model.addAttribute("dim", dim);
		model.addAttribute("permission", permissionService.getPermissionByEmployeeId(employee.getId()) == null ? 
				"" : permissionService.getPermissionByEmployeeId(employee.getId()).getColumn_ids());
		
		System.out.println("到达主页面");
		return "admin/filter/contact";
	}
	
	/**
	 * 取得所有客服
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-all-contact")
	public @ResponseBody String getAllContact(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		List<Contact> contacts = contactService.getAllContacts();
		if(contacts.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Contact contact:contacts){
			jsonObject = new JSONObject();
			
			jsonObject.put("id", contact.getId());
			jsonObject.put("name", contact.getName());
			jsonObject.put("telephone", contact.getTelephone());
			jsonObject.put("qq", contact.getQq());
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	
	/**
	 * 新增客服
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/add-contact")
	public @ResponseBody String addContact(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String qq = request.getParameter("qq");
		
		Contact contact = new Contact();
		contact.setName(name);
		contact.setTelephone(telephone);
		contact.setQq(qq);
		
		String code = contactService.addContact(contact);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 1001:
				err_msg = "add success";
				break;
			case 1002:
				err_msg = "the contact already exist!";
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
	@RequestMapping("/admin/filter/del-contact")
	public @ResponseBody String delContact(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String ids = request.getParameter("contactIds");
		
		String code = contactService.delMoreContact(ids);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 3001:
				err_msg = "del success";
				break;
			case 3002:
				err_msg = "the contact does not exist!";
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
	 * 修改客服信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/update-contact")
	public @ResponseBody String updateContact(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		String qq = request.getParameter("qq");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		
		Contact contact = contactService.getContactById(id);
		contact.setName(name);
		contact.setTelephone(telephone);
		contact.setQq(qq);
		
		String code = contactService.modifyContact(contact);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 2001:
				err_msg = "update success";
				break;
			case 2002:
				err_msg = "the contact does not exist!";
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
