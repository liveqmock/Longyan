package com.longyan.controller.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longyan.entity.Column;
import com.longyan.entity.Contact;
import com.longyan.entity.Customer;
import com.longyan.entity.FriendLinks;
import com.longyan.service.ColumnService;
import com.longyan.service.ContactService;
import com.longyan.service.CustomerService;
import com.longyan.service.FriendLinksService;
import com.longyan.util.CookieUtil;

/**
 * 发帖，帖子详情管理
 * @author Tracyqiu
 *
 */
@Controller
public class BbsController {
	private final int SITE_ID = 2;  //新闻中心站点ID默认为2
	
	@Resource
	private CustomerService customerService; 
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private FriendLinksService friendLinksService;
	
	@Autowired
	private ColumnService columnService;
	
	/**
	 * 新增帖子
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/pages/add-bbs", method={RequestMethod.GET, RequestMethod.POST})
	public String addBbs(Model model,
			String dim,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		initModel(request, model, "index");
		JSONArray jsonArray = CookieUtil.isLogin(request, customerService);
		JSONObject loginJson = (JSONObject) jsonArray.get(0);
		JSONObject customerJson = (JSONObject) jsonArray.get(1);
		
		if(!Boolean.parseBoolean(loginJson.get("isLogin").toString())){
			return "pages/jump";
		}
		
		Customer customer = customerService.getCustomerById((Integer)customerJson.get("customer_id"));
		model.addAttribute("customer", customer);
		model.addAttribute("pageCode", "news");
		model.addAttribute("dim", dim);
		model.addAttribute("pageTitle", "社区首页");
		
		return "pages/filter/news/add-bbs";
	}
	
	/**
	 * 初始化公用model
	 * @param request
	 * @param model
	 * @param columnCode
	 */
	private void initModel(HttpServletRequest request, Model model, String columnCode){
		//初始化用户登录信息
		Customer customer = null;
		Integer customer_id = -1;
		String customer_name = "";
		
		JSONArray jsonArray = CookieUtil.isLogin(request, customerService);
		JSONObject loginJson = (JSONObject) jsonArray.get(0);
		JSONObject customerJson = (JSONObject) jsonArray.get(1);
		if(Boolean.parseBoolean(loginJson.get("isLogin").toString())){
			customer = customerService.getCustomerById((Integer)customerJson.get("customer_id"));
			customer_id = customer.getId();
			customer_name = customer.getRealname();
		}
		
		//初始化banner图
		List<Column> columns = columnService.getColumnsBySiteId(SITE_ID);
		JSONArray imgArray = new JSONArray(); 
		if("index".equals(columnCode)){  //主页
			for(Column column : columns){
				String imgUrl = column.getImg_url();
				String imgArr[] = imgUrl.split("##");
				for(int i = 0, len = imgArr.length; i < len; i++){
					imgArray.add(imgArr[i]);
				}
			}
		}else {
			Column column = columnService.getColumnByCode(columnCode);
			if(column != null){
				String imgUrl = column.getImg_url();
				String imgArr[] = imgUrl.split("##");
				for(int i = 0, len = imgArr.length; i < len; i++){
					imgArray.add(imgArr[i]);
				}
			}
		}
		
		//初始化客服
		List<Contact> contacts = contactService.getAllContacts();
		
		//初始化友情链接
		List<FriendLinks> friendLinks = friendLinksService.getAllFriendLinkss();
		
		model.addAttribute("customer_name", customer_name);
		model.addAttribute("customer_id", customer_id);
		model.addAttribute("img_urls", imgArray.toString());
		model.addAttribute("columns", columns);
		model.addAttribute("contacts", contacts);
		model.addAttribute("friendLinks", friendLinks);
	}
}
