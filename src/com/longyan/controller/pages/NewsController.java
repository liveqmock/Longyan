package com.longyan.controller.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.longyan.entity.Bbs;
import com.longyan.entity.Column;
import com.longyan.entity.Contact;
import com.longyan.entity.Content;
import com.longyan.entity.Customer;
import com.longyan.entity.FriendLinks;
import com.longyan.entity.Template;
import com.longyan.service.BbsService;
import com.longyan.service.ColumnService;
import com.longyan.service.ContactService;
import com.longyan.service.ContentService;
import com.longyan.service.CustomerService;
import com.longyan.service.FriendLinksService;
import com.longyan.service.TemplateService;
import com.longyan.util.CookieUtil;
import com.longyan.util.DateUtil;
import com.longyan.util.FileUtil;

/**
 * 新闻中心
 * @author tracyqiu
 *
 */
@Controller
public class NewsController {
	private final int SITE_ID = 2;  //新闻中心站点ID默认为2
	private final int PAGE_SIZE = 20;  
	
	@Autowired
	private ColumnService columnService;
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private FriendLinksService friendLinksService;
	
	@Autowired
	private BbsService bbsService;
	
	/**
	 * 新闻中心首页
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/pages/news", method={RequestMethod.GET, RequestMethod.POST})
	public String login(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		initModel(request, model, "index");
		model.addAttribute("pageCode", "news");
		model.addAttribute("pageTitle", "社区首页");
		model.addAttribute("dim", "index");
		System.out.println("进入社区首页");
		return "pages/filter/news/index";
	}
	
	/**
	 * 新闻中心其他栏目页面
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/pages/news/{columnCode}", method={RequestMethod.GET, RequestMethod.POST})
	public String column(
			Model model, 
			@PathVariable("columnCode") String columnCode, 
			Integer pager_offset,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		initModel(request, model, columnCode);
		model.addAttribute("pageCode", "news");
		model.addAttribute("pageTitle", "健康社区");
		model.addAttribute("dim", columnCode);
		System.out.println("进入详细栏目页面");
		
		if(pager_offset == null){
			pager_offset = 1;
		}
		
		Column column = columnService.getColumnByCode(columnCode);
		if(column != null){
			model.addAttribute("column", column);
			
			if("".equals(column.getCode())){ //表示进入一个未知的栏目
				return "pages/filter/default";
			}else {
				if(columnCode.equals("health") || columnCode.equals("activity")){   //进入健康养生和活动专区，表示进入论坛页面
					int start = (pager_offset - 1) * PAGE_SIZE;
					int type = getBbsType(columnCode);
					List<Bbs> bbsList = bbsService.getPassedAndTimeoutByType(type, start, PAGE_SIZE);
					int bbsCount = bbsService.getBbsCountByType(type);
					List<Object> mapList = new ArrayList<Object>();
					
					for(Bbs bbs : bbsList) {
						Map map = new HashMap();
						Customer customer = customerService.getCustomerById(bbs.getCutomer_id());
						map.put("customer", customer);
						map.put("bbs", bbs);
						mapList.add(map);
					}
					
					model.addAttribute("dataList", mapList);
					model.addAttribute("request", request);
					model.addAttribute("totalCount", bbsCount);
					model.addAttribute("pageSize", PAGE_SIZE);
					
					return "pages/filter/news/" + columnCode;
				}else if(columnCode.equals("mybbs")){  //进入到我的帖子
					int start = (pager_offset - 1) * PAGE_SIZE;
					JSONArray jsonArray = CookieUtil.isLogin(request, customerService);
					JSONObject loginJson = (JSONObject) jsonArray.get(0);
					JSONObject customerJson = (JSONObject) jsonArray.get(1);
					
					if(!Boolean.parseBoolean(loginJson.get("isLogin").toString())){
						return "pages/jump";
					}
					
					Customer customer = customerService.getCustomerById((Integer)customerJson.get("customer_id"));
					
					List<Bbs> bbsList = bbsService.getBbsByCustomerId(customer.getId(), start, PAGE_SIZE);
					int bbsCount = bbsService.getBbsCountByCustomerId(customer.getId());
					
					model.addAttribute("bbsList", bbsList);
					model.addAttribute("customer", customer);
					model.addAttribute("request", request);
					model.addAttribute("totalCount", bbsCount);
					model.addAttribute("pageSize", PAGE_SIZE);
					return "pages/filter/news/" + columnCode;
				}else {
					Template template = templateService.getTemplateByColumnId(column.getId());   //取得对应的模板
					if(template != null){ //该栏目下没有二级内容
						String template_content = FileUtil.readTemplate(template.getPath());
						model.addAttribute("template_content", template_content);
						
						return "pages/filter/template";
					}else {
						int start = (pager_offset - 1) * PAGE_SIZE;
						List<Content> contents = contentService.getContentsByColumnId(column.getId());
						List<Content> ret_contents = contentService.getContentsByColumnId(column.getId(), start, PAGE_SIZE);
						if(contents.size() > 0){  //存在二级内容
							model.addAttribute("contents", ret_contents);
							model.addAttribute("style", column.getStyle());
							model.addAttribute("request", request);
							model.addAttribute("totalCount", contents.size());
							model.addAttribute("pageSize", PAGE_SIZE);
							
							return "pages/filter/content";
						}else {  //不存在二级内容
							return "pages/filter/default";
						}
					}
				}
			}
		}else {
			return "pages/filter/default";
		}
	}
	
	/**
	 * 新闻中心其他栏目页面点击二级内容页面
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/pages/news/{columnCode}/content/{contentId}", method={RequestMethod.GET, RequestMethod.POST})
	public String content(
			Model model, 
			@PathVariable("columnCode") String columnCode, 
			@PathVariable("contentId") int contentId, 
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		initModel(request, model, columnCode);
		model.addAttribute("pageCode", "news");
		model.addAttribute("pageTitle", "新闻中心");
		model.addAttribute("dim", columnCode);
		System.out.println("进入模板页面");
		
		Column column = columnService.getColumnByCode(columnCode);
		if(column != null){
			model.addAttribute("column", column);
			
			if("".equals(column.getCode())){ //表示进入一个未知的栏目
				return "pages/filter/default";
			}else {
				Template template = templateService.getTemplateByContentId(contentId);   //取得对应的模板
				if(template != null){ 
					String template_content = FileUtil.readTemplate(template.getPath());
					model.addAttribute("template_content", template_content);
					
					return "pages/filter/template";
				}else {
					return "pages/filter/default";
				}
			}
		}else {
			return "pages/filter/default";
		}
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
	
	/**
	 * 取得帖子类型code
	 * @param type
	 * @return
	 */
	private int getBbsType(String type){
		int ret = 1;
		
		if(type.equals("health")){
			ret = 1;
		}else if(type.equals("activity")){
			ret = 2;
		}
		
		return ret;
	}
}
