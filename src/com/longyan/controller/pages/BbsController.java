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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longyan.entity.Bbs;
import com.longyan.entity.BbsReply;
import com.longyan.entity.Column;
import com.longyan.entity.Contact;
import com.longyan.entity.Customer;
import com.longyan.entity.FriendLinks;
import com.longyan.service.BbsReplyService;
import com.longyan.service.BbsService;
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
	private final int PAGE_SIZE = 20;  
	
	@Resource
	private CustomerService customerService; 
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private FriendLinksService friendLinksService;
	
	@Autowired
	private ColumnService columnService;
	
	@Autowired
	private BbsService bbsService;
	
	@Autowired
	private BbsReplyService bbsReplyService;
	
	/**
	 * 中间跳转，判断是否已经登录
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/add-bbs", method={RequestMethod.GET, RequestMethod.POST})
	public void addBbsEntry(Model model,
			String dim,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		JSONArray jsonArray = CookieUtil.isLogin(request, customerService);
		JSONObject loginJson = (JSONObject) jsonArray.get(0);
		
		if(!Boolean.parseBoolean(loginJson.get("isLogin").toString())){
			response.sendRedirect(request.getContextPath() + "/login");
		}else{
			response.sendRedirect(request.getContextPath() + "/pages/add-bbs?dim=" + dim);
		}
		
	}
	
	/**
	 * 初始化新增帖子页面
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
		
		initModel(request, model, dim);
		JSONArray jsonArray = CookieUtil.isLogin(request, customerService);
		JSONObject customerJson = (JSONObject) jsonArray.get(1);
		
		Customer customer = customerService.getCustomerById((Integer)customerJson.get("customer_id"));
		model.addAttribute("customer", customer);
		model.addAttribute("pageCode", "news");
		model.addAttribute("dim", dim);
		model.addAttribute("title", "");
		model.addAttribute("content", "");
		model.addAttribute("method", "new");   //表示新增帖子
		model.addAttribute("id", -1);
		model.addAttribute("status", 1);
		model.addAttribute("type", 1);
		model.addAttribute("pageTitle", "健康社区");
		
		return "pages/filter/news/bbs";
	}
	
	/**
	 * 初始化修改帖子页面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/pages/update-bbs", method={RequestMethod.GET, RequestMethod.POST})
	public String updateBbs(Model model,
			Integer id,
			Integer type,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		initModel(request, model, "mybbs");
		JSONArray jsonArray = CookieUtil.isLogin(request, customerService);
		JSONObject customerJson = (JSONObject) jsonArray.get(1);
		
		Customer customer = customerService.getCustomerById((Integer)customerJson.get("customer_id"));
		String title = "";
		String content = "";
		String method = "new";
		Integer status = 1;
		Bbs bbs = null;
		
		if(null == id) {
			id = -1;
		}else{
			bbs = bbsService.getBbsById(id);
			title = bbs.getTitle();
			content = bbs.getContent();
			type = bbs.getType();
			status = bbs.getStatus();
			status = status == 3 ? 1 : status;   //审核未通过的帖子，修改之后重新进入审核流程
			method = "update";
		}
		
		if(null == type) {
			type = 1;
		}
		model.addAttribute("customer", customer);
		model.addAttribute("pageCode", "news");
		model.addAttribute("dim", "mybbs");
		model.addAttribute("title", title);
		model.addAttribute("content", content);
		model.addAttribute("method", method);   //表示新增帖子
		model.addAttribute("id", id);
		model.addAttribute("status", status);
		model.addAttribute("type", type);
		model.addAttribute("pageTitle", "健康社区");
		
		return "pages/filter/news/bbs";
	}
	
	/**
	 * 新增或修改帖子
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/bbs/post")
	public @ResponseBody String postBbs(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		JSONObject jsonObject = new JSONObject();
		Integer customer_id;
		
		JSONArray jsonArray = CookieUtil.isLogin(request, customerService);
		JSONObject loginJson = (JSONObject) jsonArray.get(0);
		JSONObject customerJson = (JSONObject) jsonArray.get(1);
		if(!Boolean.parseBoolean(loginJson.get("isLogin").toString())){
			jsonObject.put("code", 1004);
			jsonObject.put("msg", "not login");
			return jsonObject.toString();
		}
		
		customer_id = (Integer)customerJson.get("customer_id");
		String method = request.getParameter("method");
		Integer id = Integer.parseInt(request.getParameter("id"));
		Integer status = Integer.parseInt(request.getParameter("status"));
		String content = request.getParameter("content");
		String title = request.getParameter("title");
		Integer type = Integer.parseInt(request.getParameter("type"));
		
		String codeStr = "";
		String err_msg = "";
		if("new".equals(method) && id == -1) {   //新增帖子
			Bbs bbs = new Bbs();
			bbs.setContent(content);
			bbs.setCutomer_id(customer_id);
			bbs.setIs_customer(0);
			bbs.setReply_count(0);
			bbs.setStatus(1);
			bbs.setTitle(title);
			bbs.setType(type);
			bbs.setView_count(0);
			
			codeStr = bbsService.addBbs(bbs);
		} else {
			Bbs bbs = bbsService.getBbsById(id);
			bbs.setContent(content);
			bbs.setTitle(title);
			bbs.setType(type);
			bbs.setStatus(status);
			
			codeStr = bbsService.modifyBbs(bbs);
		}
		
		switch (Integer.parseInt(codeStr)) {
			case 1001:
				err_msg = "add success";
				break;
			case 1002:
				err_msg = "the bbs already exist!";
				break;
			case 1003:
				err_msg = "unkown error!";
				break;
			case 2001:
				err_msg = "update";
				break;
			case 2002:
				err_msg = "the bbs does not exist!";
				break;
			case 2003:
				err_msg = "unkown error!";
				break;
		}
		
		jsonObject.put("code", codeStr);
		jsonObject.put("msg", err_msg);
		
		return jsonObject.toString();
	}
	
	/**
	 * 删除帖子
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/bbs/delete")
	public @ResponseBody String deleteBbs(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		JSONObject jsonObject = new JSONObject();
		
		JSONArray jsonArray = CookieUtil.isLogin(request, customerService);
		JSONObject loginJson = (JSONObject) jsonArray.get(0);
		if(!Boolean.parseBoolean(loginJson.get("isLogin").toString())){
			jsonObject.put("code", 1004);
			jsonObject.put("msg", "not login");
			return jsonObject.toString();
		}
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		Integer status = Integer.parseInt(request.getParameter("status"));
		
		String codeStr = "";
		String err_msg = "";
		if(status != 2 && status != 3 && status != 4){
			codeStr = "2004";
		}else{
			Bbs bbs = bbsService.getBbsById(id);
			bbs.setStatus(6);
			codeStr = bbsService.modifyBbsStatus(bbs);
		}
		
		switch (Integer.parseInt(codeStr)) {
			case 2001:
				err_msg = "update";
				break;
			case 2002:
				err_msg = "the bbs does not exist!";
				break;
			case 2003:
				err_msg = "unkown error!";
				break;
			case 2004:
				err_msg = "status error!";
				break;
		}
		
		jsonObject.put("code", codeStr);
		jsonObject.put("msg", err_msg);
		
		return jsonObject.toString();
	}
	
	/**
	 * 关闭帖子
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/bbs/close")
	public @ResponseBody String closeBbs(
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		JSONObject jsonObject = new JSONObject();
		
		JSONArray jsonArray = CookieUtil.isLogin(request, customerService);
		JSONObject loginJson = (JSONObject) jsonArray.get(0);
		if(!Boolean.parseBoolean(loginJson.get("isLogin").toString())){
			jsonObject.put("code", 1004);
			jsonObject.put("msg", "not login");
			return jsonObject.toString();
		}
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		Integer status = Integer.parseInt(request.getParameter("status"));
		
		String codeStr = "";
		String err_msg = "";
		if(status != 2){
			codeStr = "2004";
		}else{
			Bbs bbs = bbsService.getBbsById(id);
			bbs.setStatus(4);
			codeStr = bbsService.modifyBbsStatus(bbs);
		}
		
		switch (Integer.parseInt(codeStr)) {
			case 2001:
				err_msg = "update";
				break;
			case 2002:
				err_msg = "the bbs does not exist!";
				break;
			case 2003:
				err_msg = "unkown error!";
				break;
			case 2004:
				err_msg = "status error!";
				break;
		}
		
		jsonObject.put("code", codeStr);
		jsonObject.put("msg", err_msg);
		
		return jsonObject.toString();
	}
	
	/**
	 * 进入帖子详情
	 * @param model
	 * @param bbsId
	 * @param dim
	 * @param pager_offset
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/pages/bbs/{bbsId}", method={RequestMethod.GET, RequestMethod.POST})
	public String bbsDetail(Model model, 
			@PathVariable("bbsId") Integer bbsId, 
			String dim,
			Integer pager_offset,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		if(null == dim){
			dim = "index";
		}
		
		if(pager_offset == null){
			pager_offset = 1;
		}
		
		initModel(request, model, dim);
		
		Map bbsLz = new HashMap();   //帖子信息和楼主信息
		Bbs bbs = bbsService.getBbsById(bbsId);
		Customer lz = customerService.getCustomerById(bbs.getCutomer_id());
		bbsLz.put("bbs", bbs);
		bbsLz.put("lz", lz);
		
		int start = (pager_offset - 1) * PAGE_SIZE;
		int replyCount = bbsReplyService.getPassedReplyCountByBbsId(bbsId) + 1;
		List replyInfoList = new ArrayList();
		List<BbsReply> replyList = bbsReplyService.getPasssedBbsReplyByBbsId(bbsId, start, PAGE_SIZE);
		
		for(BbsReply bbsReply : replyList){
			Map bbsInfoMap = new HashMap();
			Customer cus = customerService.getCustomerById(bbsReply.getCutomer_id());
			bbsInfoMap.put("reply", bbsReply);
			bbsInfoMap.put("customer", cus);
			
			replyInfoList.add(bbsInfoMap);
		}
		
		model.addAttribute("pageCode", "news");
		model.addAttribute("dim", dim);
		model.addAttribute("bbsLz", bbsLz);
		model.addAttribute("replyInfoList", replyInfoList);
		model.addAttribute("request", request);
		model.addAttribute("totalCount", replyCount);
		model.addAttribute("pageSize", PAGE_SIZE);
		model.addAttribute("pageTitle", "健康社区");
		
		return "pages/filter/news/bbs-detail";
		
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
