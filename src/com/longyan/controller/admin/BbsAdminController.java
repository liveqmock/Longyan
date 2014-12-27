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

import com.longyan.entity.Bbs;
import com.longyan.entity.BbsReply;
import com.longyan.entity.Column;
import com.longyan.entity.Customer;
import com.longyan.entity.Employee;
import com.longyan.service.BbsReplyService;
import com.longyan.service.BbsService;
import com.longyan.service.ColumnService;
import com.longyan.service.CustomerService;
import com.longyan.service.PermissionService;
import com.longyan.util.DateUtil;
import com.longyan.util.SessionUtil;

/**
 * 论坛管理页面
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class BbsAdminController {
	@Resource
	private PermissionService permissionService; 
	
	@Resource
	private BbsService bbsService; 
	
	@Resource
	private BbsReplyService bbsReplyService; 
	
	@Resource
	private CustomerService customerService;

	/**
	 * 跳转到论坛页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/bbs", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String bbs(
			Model model, 
			Integer type,
			Integer status,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		if(null == type) {
			type = 0;
		}
		
		if(null == status) {
			status = 0;
		}
		
		String dim = "bbs"; //声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		//员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 会员管理。
		Integer right = employee.getRight_level(); 

		model.addAttribute("username", employee.getName());
		model.addAttribute("type", type);
		model.addAttribute("status", status);
		model.addAttribute("right", right);
		model.addAttribute("dim", dim);
		model.addAttribute("permission", permissionService.getPermissionByEmployeeId(employee.getId()) == null ? 
				"" : permissionService.getPermissionByEmployeeId(employee.getId()).getColumn_ids());
		
		System.out.println("到达主页面");
		return "admin/filter/bbs";
	}
	
	/**
	 * 取得所有帖子
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-all-bbs")
	public @ResponseBody String getAllBbs(
			Model model, 
			Integer type,
			Integer status,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		if(null == type) {
			type = 0;
		}
		
		if(null == status) {
			status = 0;
		}
		
		String[] typeArr = {"", "健康生活", "活动专区"};
		List<Bbs> bbsList = bbsService.getBbsByParam(type, status);
		if(bbsList.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Bbs bbs:bbsList){
			jsonObject = new JSONObject();
			Customer cus = customerService.getCustomerById(bbs.getCutomer_id());
			
			jsonObject.put("id", bbs.getId());
			jsonObject.put("title", bbs.getTitle());
			jsonObject.put("status_text", getStatusText(bbs.getStatus()));
			jsonObject.put("status", bbs.getStatus());
			jsonObject.put("customer_name", cus.getRealname());
			jsonObject.put("type_text", typeArr[bbs.getType()]);
			jsonObject.put("type", bbs.getType());
			jsonObject.put("view_count", bbs.getView_count());
			jsonObject.put("reply_count", bbs.getReply_count());
			jsonObject.put("ctime", DateUtil.getFormateDate(bbs.getCtime()));
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	/**
	 * 取得所有帖子
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-bbs-by-param")
	public @ResponseBody String getBbsByParam(
			Model model, 
			Integer type,
			Integer status,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if(null == type) {
			type = 0;
		}
		
		if(null == status) {
			status = 0;
		}
		
		String[] typeArr = {"", "健康生活", "活动专区"};
		List<Bbs> bbsList = bbsService.getBbsByParam(type, status);
		if(bbsList.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Bbs bbs:bbsList){
			jsonObject = new JSONObject();
			Customer cus = customerService.getCustomerById(bbs.getCutomer_id());
			
			jsonObject.put("id", bbs.getId());
			jsonObject.put("title", bbs.getTitle());
			jsonObject.put("status_text", getStatusText(bbs.getStatus()));
			jsonObject.put("status", bbs.getStatus());
			jsonObject.put("customer_name", cus.getRealname());
			jsonObject.put("type_text", typeArr[bbs.getType()]);
			jsonObject.put("type", bbs.getType());
			jsonObject.put("view_count", bbs.getView_count());
			jsonObject.put("reply_count", bbs.getReply_count());
			jsonObject.put("ctime", DateUtil.getFormateDate(bbs.getCtime()));
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	/**
	 * 改变帖子状态
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/change-status")
	public @ResponseBody String changeStatus(
			Model model, 
			Integer id,
			Integer status,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if(null == id) {
			id = 0;
		}
		
		if(null == status) {
			status = 0;
		}
		
		JSONObject jsonObject = new JSONObject();
		Bbs bbs = bbsService.getBbsById(id);
		bbs.setStatus(status);
		String code = bbsService.modifyBbs(bbs);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 2001:
				err_msg = "add success";
				break;
			case 2002:
				err_msg = "the employer already exist!";
				break;
			case 2003:
				err_msg = "unkown error!";
				break;
		}
		
		jsonObject.put("code", code);
		jsonObject.put("msg", err_msg);
		return jsonObject.toString();
	}
	
	/**
	 * 取得帖子下面所有跟帖
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-bbsreply-by-bbsid")
	public @ResponseBody String getBbsReplyByBbsId(
			Model model, 
			Integer bbsId,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if(null == bbsId) {
			bbsId = -1;
		}
		
		List<BbsReply> replyList = bbsReplyService.getBbsReplyByBbsId(bbsId);
		if(replyList.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(BbsReply bbsReply:replyList){
			jsonObject = new JSONObject();
			Customer cus = customerService.getCustomerById(bbsReply.getCutomer_id());
			
			jsonObject.put("id", bbsReply.getId());
			jsonObject.put("bbs_id", bbsReply.getBbs_id());
			jsonObject.put("status_text", bbsReply.getStatus() == 1 ? "正常" : "已屏蔽");
			jsonObject.put("status", bbsReply.getStatus());
			jsonObject.put("content", bbsReply.getContent());
			jsonObject.put("customer_name", cus.getRealname());
			jsonObject.put("ctime", DateUtil.getFormateDate(bbsReply.getCtime()));
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	/**
	 * 改变跟帖状态
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/change-reply-status")
	public @ResponseBody String changeReplyStatus(
			Model model, 
			Integer id,
			Integer status,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if(null == id) {
			id = 0;
		}
		
		if(null == status) {
			status = 0;
		}
		
		JSONObject jsonObject = new JSONObject();
		BbsReply bbsReply = bbsReplyService.getBbsReplyById(id);
		bbsReply.setStatus(status);
		
		String code = bbsReplyService.modifyBbsReplyStatus(bbsReply);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 2001:
				err_msg = "add success";
				break;
			case 2002:
				err_msg = "the employer already exist!";
				break;
			case 2003:
				err_msg = "unkown error!";
				break;
		}
		
		jsonObject.put("code", code);
		jsonObject.put("msg", err_msg);
		return jsonObject.toString();
	}
	
	/**
	 * 取得帖子状态文案
	 * @param status
	 * @return
	 */
	private String getStatusText(Integer status){
		String ret = "";
		switch (status) {
		case 1:
			ret = "<span class=\"yellow\">待审核</span>";
			break;
		case 2:
			ret = "<span class=\"green\">审核通过</span>";
			break;
		case 3:
			ret = "<span class=\"red\">审核未通过</span>";
			break;
		case 4:
			ret = "<span class=\"gray\">已封帖</span>";
			break;
		case 5:
			ret = "<span class=\"gray\">已屏蔽</span>";
			break;
		}
		
		return ret;
	}
	
}
