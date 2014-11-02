package com.longyan.controller.admin;

import java.io.IOException;
import java.util.Date;
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
import com.longyan.entity.Message;
import com.longyan.entity.Reply;
import com.longyan.service.MessageService;
import com.longyan.service.PermissionService;
import com.longyan.service.ReplyService;
import com.longyan.util.DateUtil;
import com.longyan.util.SessionUtil;

/**
 * 留言管理页面
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class MessageController {
	@Resource
	private PermissionService permissionService; 
	
	@Resource
	private MessageService messageService; 
	
	@Resource
	private ReplyService replyService; 

	/**
	 * 跳转到登录页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/message", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String message(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String dim = "message"; //声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		//员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 会员管理。
		Integer right = employee.getRight_level(); 

		model.addAttribute("username", employee.getName());
		model.addAttribute("right", right);
		model.addAttribute("dim", dim);
		model.addAttribute("permission", permissionService.getPermissionByEmployeeId(employee.getId()) == null ? 
				"" : permissionService.getPermissionByEmployeeId(employee.getId()).getColumn_ids());
		
		System.out.println("到达主页面");
		return "admin/filter/message";
	}
	
	/**
	 * 取得所有留言
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-all-message")
	public @ResponseBody String getAllMessage(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		List<Message> messages = messageService.getAllMessages();
		if(messages.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Message message:messages){
			Reply reply = replyService.getReplysByMessageId(message.getId());
			jsonObject = new JSONObject();
			
			jsonObject.put("id", message.getId());
			jsonObject.put("customer_id", message.getCustomer_id());
			jsonObject.put("customer_name", message.getCustomer_name());
			jsonObject.put("customer_phone", message.getCustomer_phone());
			jsonObject.put("content", message.getContent());
			jsonObject.put("status", message.getStatus());
			jsonObject.put("status_str", message.getStatus() == 0 ? "未回复" : "已回复");
			jsonObject.put("ctime", DateUtil.getFormateDate(message.getCtime()));
			
			if(reply != null){
				jsonObject.put("reply_id", reply.getId());
				jsonObject.put("reply_content", reply.getReply_content());
				jsonObject.put("reply_emplyoee_name", reply.getEmployee_name());
				jsonObject.put("reply_ctime", DateUtil.getFormateDate(reply.getCtime()));
			}else{
				jsonObject.put("reply_id", "");
				jsonObject.put("reply_content", "");
				jsonObject.put("reply_emplyoee_name", "");
				jsonObject.put("reply_ctime", "");
			}
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	/**
	 * 新增回复
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/add-message-reply")
	public @ResponseBody String addReply(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Employee employee = (Employee) SessionUtil.getSession(response, request);//登录会员
		int messageId = Integer.parseInt(request.getParameter("message_id"));
		String content = request.getParameter("reply_content");
		
		Reply reply = new Reply();
		
		reply.setEmployee_name(employee.getName());
		reply.setMessage_id(messageId);
		reply.setReply_content(content);
		
		String code = replyService.addReply(reply);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 1001:
				err_msg = "add success";
				messageService.modifyMessageStatus(messageId, 1);
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
	 * 删除留言
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/del-message")
	public @ResponseBody String delMessage(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String ids = request.getParameter("messageIds");
		
		String code = messageService.delMoreMessage(ids);
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
	 * 更具日期和回复状态
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-message-by-date")
	public @ResponseBody String geMessageByDate(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Date startDate = DateUtil.StrToDate(request.getParameter("start_date"));
		Date endDate = DateUtil.StrToDate(request.getParameter("end_date"));
		int status = Integer.parseInt(request.getParameter("status"));
		
		List<Message> messages = status == -1 ? messageService.getMessagesByDate(startDate, endDate): 
					messageService.getMessagesByDateAndStatus(startDate, endDate, status);
		
		if(messages.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Message message:messages){
			Reply reply = replyService.getReplysByMessageId(message.getId());
			jsonObject = new JSONObject();
			
			jsonObject.put("id", message.getId());
			jsonObject.put("customer_id", message.getCustomer_id());
			jsonObject.put("customer_name", message.getCustomer_name());
			jsonObject.put("customer_phone", message.getCustomer_phone());
			jsonObject.put("content", message.getContent());
			jsonObject.put("status", message.getStatus());
			jsonObject.put("status_str", message.getStatus() == 0 ? "未回复" : "已回复");
			jsonObject.put("ctime", DateUtil.getFormateDate(message.getCtime()));
			
			if(reply != null){
				jsonObject.put("reply_id", reply.getId());
				jsonObject.put("reply_content", reply.getReply_content());
				jsonObject.put("reply_emplyoee_name", reply.getEmployee_name());
				jsonObject.put("reply_ctime", DateUtil.getFormateDate(reply.getCtime()));
			}else{
				jsonObject.put("reply_id", "");
				jsonObject.put("reply_content", "");
				jsonObject.put("reply_emplyoee_name", "");
				jsonObject.put("reply_ctime", "");
			}
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
}
