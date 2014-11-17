package com.longyan.controller.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.longyan.entity.Message;
import com.longyan.entity.Reply;
import com.longyan.service.CustomerService;
import com.longyan.service.MessageService;
import com.longyan.service.ReplyService;
import com.longyan.util.CookieUtil;
import com.longyan.util.MD5;

/**
 * 评论管理
 * @author tracyqiu
 *
 */
@Controller
public class FeedBackController {

	@Resource
	private CustomerService customerService; 
	
	@Resource
	private MessageService messageService;
	
	@Resource
	private ReplyService replyService;
	
	/**
	 * 用户反馈页面
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/add-feedback", method={RequestMethod.GET, RequestMethod.POST})
	public String addFeedback(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		JSONArray jsonArray = CookieUtil.isLogin(request, customerService);
		JSONObject loginJson = (JSONObject) jsonArray.get(0);
		JSONObject customerJson = (JSONObject) jsonArray.get(1);
		List<Object> feedbackList = new ArrayList<Object>();
		
		if(!Boolean.parseBoolean(loginJson.get("isLogin").toString())){
			return "pages/jump";
		}
		
		Customer customer = customerService.getCustomerById((Integer)customerJson.get("customer_id"));
		Integer customer_id = customer.getId();
		
		List<Message> messages = messageService.getMessagesByCustomerId(customer_id); 
		for(Message message : messages){
			Map map = new HashMap();
			Reply reply = replyService.getReplysByMessageId(message.getId());
			map.put("message", message);
			map.put("reply", reply);
			feedbackList.add(map);
		}
		
		model.addAttribute("feedbackList", feedbackList);
		System.out.println("进入反馈页面");
		return "pages/feedback";
	}
	
	
	/**
	 * 添加反馈信息
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/post-feedback", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String postFeedback(
			Model model,
			String content,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		JSONObject retObject = new JSONObject();
		JSONArray jsonArray = CookieUtil.isLogin(request, customerService);
		JSONObject loginJson = (JSONObject) jsonArray.get(0);
		JSONObject customerJson = (JSONObject) jsonArray.get(1);
		
		if(!Boolean.parseBoolean(loginJson.get("isLogin").toString())){
			retObject.put("code", 1);
			return retObject.toString();
		}
		
		Customer customer = customerService.getCustomerById((Integer)customerJson.get("customer_id"));
		if(null == content){
			content = "";
		}
		Message message = new Message();
		message.setContent(content);
		message.setCustomer_id(customer.getId());
		message.setCustomer_name(customer.getRealname());
		message.setCustomer_phone(customer.getTelephone());
		message.setStatus(0);
		
		String code = messageService.addMessage(message);
		retObject.put("code", code);
		
		return retObject.toString();
	}
}
