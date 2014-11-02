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

import com.longyan.entity.FriendLinks;
import com.longyan.entity.Employee;
import com.longyan.service.FriendLinksService;
import com.longyan.service.PermissionService;
import com.longyan.util.SessionUtil;

/**
 * 客服管理页面
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class FriendLinksController {
	@Resource
	private PermissionService permissionService; 
	
	@Resource
	private FriendLinksService friendLinksService; 

	/**
	 * 跳转到登录页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/friendLinks", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String friendLinks(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String dim = "friendLinks"; //声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		//员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 客服管理。
		Integer right = employee.getRight_level(); 

		model.addAttribute("username", employee.getName());
		model.addAttribute("right", right);
		model.addAttribute("dim", dim);
		model.addAttribute("permission", permissionService.getPermissionByEmployeeId(employee.getId()) == null ? 
				"" : permissionService.getPermissionByEmployeeId(employee.getId()).getColumn_ids());
		
		System.out.println("到达主页面");
		return "admin/filter/friendLinks";
	}
	
	/**
	 * 取得所有友情链接
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-all-friendLinks")
	public @ResponseBody String getAllFriendLinks(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		List<FriendLinks> friendLinkss = friendLinksService.getAllFriendLinkss();
		if(friendLinkss.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(FriendLinks friendLinks:friendLinkss){
			jsonObject = new JSONObject();
			
			jsonObject.put("id", friendLinks.getId());
			jsonObject.put("name", friendLinks.getName());
			jsonObject.put("url", friendLinks.getUrl());
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	
	/**
	 * 新增友情链接
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/add-friendLinks")
	public @ResponseBody String addFriendLinks(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		
		FriendLinks friendLinks = new FriendLinks();
		friendLinks.setName(name);
		friendLinks.setUrl(url);
		
		String code = friendLinksService.addFriendLinks(friendLinks);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 1001:
				err_msg = "add success";
				break;
			case 1002:
				err_msg = "the friendLinks already exist!";
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
	 * 删除友情链接
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/del-friendLinks")
	public @ResponseBody String delFriendLinks(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String ids = request.getParameter("friendLinksIds");
		
		String code = friendLinksService.delMoreFriendLinks(ids);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 3001:
				err_msg = "del success";
				break;
			case 3002:
				err_msg = "the friendLinks does not exist!";
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
	 * 修改友情链接信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/update-friendLinks")
	public @ResponseBody String updateFriendLinks(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		String url = request.getParameter("url");
		String name = request.getParameter("name");
		
		FriendLinks friendLinks = friendLinksService.getFriendLinksById(id);
		friendLinks.setName(name);
		friendLinks.setUrl(url);
		
		String code = friendLinksService.modifyFriendLinks(friendLinks);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 2001:
				err_msg = "update success";
				break;
			case 2002:
				err_msg = "the friendLinks does not exist!";
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
