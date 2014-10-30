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

import com.longyan.entity.Column;
import com.longyan.entity.Employee;
import com.longyan.service.ColumnService;
import com.longyan.util.SessionUtil;

/**
 * 员工管理页面
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class ColumnController {
	@Resource
	private ColumnService columnService; 

	/**
	 * 跳转到登录页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/column", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String column(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String dim = "column"; //声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		//员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 会员管理。
		Integer right = employee.getRight_level(); 

		model.addAttribute("username", employee.getName());
		model.addAttribute("right", right);
		model.addAttribute("dim", dim);
		
		System.out.println("到达主页面");
		return "admin/filter/column";
	}
	
	/**
	 * 取得所有员工
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-all-column")
	public @ResponseBody String getAllColumn(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		List<Column> columns = columnService.getAllColumns();
		if(columns.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Column column:columns){
			jsonObject = new JSONObject();
			
			jsonObject.put("id", column.getId());
			jsonObject.put("name", column.getName());
			jsonObject.put("site_id", column.getSite_id());
			jsonObject.put("code", column.getCode());
			jsonObject.put("template_id", column.getTemplate_id());
			jsonObject.put("img_url", column.getImg_url());
			jsonObject.put("create_user", column.getCreate_user());
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	/**
	 * 通过SITE_ID取得栏目
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-column-by-siteid")
	public @ResponseBody String getColumnById(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Integer site_id = Integer.parseInt(request.getParameter("site_id"));
		List<Column> columns = site_id == 0 ? columnService.getAllColumns() : columnService.getColumnsBySiteId(site_id);
		
		if(columns.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Column column:columns){
			jsonObject = new JSONObject();
			
			jsonObject.put("id", column.getId());
			jsonObject.put("name", column.getName());
			jsonObject.put("site_id", column.getSite_id());
			jsonObject.put("code", column.getCode());
			jsonObject.put("template_id", column.getTemplate_id());
			jsonObject.put("img_url", column.getImg_url());
			jsonObject.put("create_user", column.getCreate_user());
			
			jsonArray.add(jsonObject);
		}
			
		return jsonArray.toString();
	}
	
	/**
	 * 新增栏目
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/add-column")
	public @ResponseBody String addColumn(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		Integer site_id = Integer.parseInt(request.getParameter("site_id"));
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String img_url = request.getParameter("img_url");
		String create_user = employee.getName();
		
		Column column = new Column();
		column.setName(name);
		column.setCode(code);
		column.setCreate_user(create_user);
		column.setImg_url(img_url);
		column.setSite_id(site_id);
		
		String codeStr = columnService.addColumn(column);
		String err_msg = "";
		
		switch (Integer.parseInt(codeStr)) {
			case 1001:
				err_msg = "add success";
				break;
			case 1002:
				err_msg = "the column already exist!";
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
	 * 删除栏目
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/del-column")
	public @ResponseBody String delColumn(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String ids = request.getParameter("columnIds");
		
		String code = columnService.delMoreColumn(ids);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 3001:
				err_msg = "del success";
				break;
			case 3002:
				err_msg = "the column does not exist!";
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
	 * 修改栏目信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/update-column")
	public @ResponseBody String updateColumn(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		Integer id = Integer.parseInt(request.getParameter("id"));
		Integer site_id = Integer.parseInt(request.getParameter("site_id"));
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String img_url = request.getParameter("img_url");
		String create_user = employee.getName();
		
		Column column = columnService.getColumnById(id);
		column.setName(name);
		column.setCode(code);
		column.setCreate_user(create_user);
		column.setImg_url(img_url);
		column.setSite_id(site_id);
		
		String codeStr = columnService.modifyColumn(column);
		String err_msg = "";
		
		switch (Integer.parseInt(codeStr)) {
			case 2001:
				err_msg = "update success";
				break;
			case 2002:
				err_msg = "the column does not exist!";
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
