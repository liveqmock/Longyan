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

import com.longyan.entity.Content;
import com.longyan.entity.Employee;
import com.longyan.service.ContentService;
import com.longyan.service.PermissionService;
import com.longyan.util.SessionUtil;

/**
 * 内容管理页面
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class ContentController {
	@Resource
	private PermissionService permissionService; 
	
	@Resource
	private ContentService contentService; 

	/**
	 * 跳转到登录页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/content", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String content(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String dim = "content"; //声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		//内容权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 会员管理。
		Integer right = employee.getRight_level(); 

		model.addAttribute("username", employee.getName());
		model.addAttribute("right", right);
		model.addAttribute("dim", dim);
		model.addAttribute("permission", permissionService.getPermissionByEmployeeId(employee.getId()) == null ? 
				"" : permissionService.getPermissionByEmployeeId(employee.getId()).getColumn_ids());
		
		System.out.println("到达主页面");
		return "admin/filter/content";
	}
	
	/**
	 * 取得所有内容
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-all-content")
	public @ResponseBody String getAllContent(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		List<Content> contents = contentService.getAllContents();
		if(contents.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Content content:contents){
			jsonObject = new JSONObject();
			
			jsonObject.put("id", content.getId());
			jsonObject.put("title", content.getTitle());
			jsonObject.put("column_id", content.getColumn_id());
			jsonObject.put("code", content.getCode());
			jsonObject.put("describe", content.getDescribe());
			jsonObject.put("template_id", content.getTemplate_id());
			jsonObject.put("img_url", content.getImg_url());
			jsonObject.put("create_user", content.getCreate_user());
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	/**
	 * 通过column_id取得二级内容
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-content-by-columnid")
	public @ResponseBody String getContentByColumnId(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Integer column_id = Integer.parseInt(request.getParameter("column_id"));
		List<Content> contents = column_id == -1 ? contentService.getAllContents() : contentService.getContentsByColumnId(column_id);
		
		if(contents.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Content content:contents){
			jsonObject = new JSONObject();
			
			jsonObject.put("id", content.getId());
			jsonObject.put("title", content.getTitle());
			jsonObject.put("column_id", content.getColumn_id());
			jsonObject.put("code", content.getCode());
			jsonObject.put("describe", content.getDescribe());
			jsonObject.put("template_id", content.getTemplate_id());
			jsonObject.put("img_url", content.getImg_url());
			jsonObject.put("create_user", content.getCreate_user());
			
			jsonArray.add(jsonObject);
		}
			
		return jsonArray.toString();
	}
	
	/**
	 * 新增内容信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/add-content")
	public @ResponseBody String addContent(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		Integer column_id = Integer.parseInt(request.getParameter("column_id"));
		String title = request.getParameter("title");
		String code = request.getParameter("code");
		String describe = request.getParameter("describe");
		String img_url = request.getParameter("img_url");
		String create_user = employee.getName();
		
		Content content = new Content();
		content.setTitle(title);
		content.setCode(code);
		content.setDescribe(describe);
		content.setCreate_user(create_user);
		content.setImg_url(img_url);
		content.setColumn_id(column_id);
		
		String codeStr = contentService.addContent(content);
		String err_msg = "";
		
		switch (Integer.parseInt(codeStr)) {
			case 1001:
				err_msg = "add success";
				break;
			case 1002:
				err_msg = "the content already exist!";
				break;
			case 1003:
				err_msg = "unkown error!";
				break;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", codeStr);
		jsonObject.put("msg", err_msg);
		
		return jsonObject.toString();
	}
	
	/**
	 * 删除内容
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/del-content")
	public @ResponseBody String delContent(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String ids = request.getParameter("contentIds");
		
		String code = contentService.delMoreContent(ids);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 3001:
				err_msg = "del success";
				break;
			case 3002:
				err_msg = "the content does not exist!";
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
	 * 修改内容信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/update-content")
	public @ResponseBody String updateContent(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		Integer id = Integer.parseInt(request.getParameter("id"));
		Integer column_id = Integer.parseInt(request.getParameter("column_id"));
		String title = request.getParameter("title");
		String code = request.getParameter("code");
		String describe = request.getParameter("describe");
		String img_url = request.getParameter("img_url");
		String create_user = employee.getName();
		
		Content content = contentService.getContentById(id);
		content.setTitle(title);
		content.setCode(code);
		content.setDescribe(describe);
		content.setCreate_user(create_user);
		content.setImg_url(img_url);
		content.setColumn_id(column_id);
		
		String codeStr = contentService.modifyContent(content);
		String err_msg = "";
		
		switch (Integer.parseInt(codeStr)) {
			case 2001:
				err_msg = "update success";
				break;
			case 2002:
				err_msg = "the content does not exist!";
				break;
			case 2003:
				err_msg = "unkown error!";
				break;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", codeStr);
		jsonObject.put("msg", err_msg);
		
		return jsonObject.toString();
	}
	
}
