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

import com.longyan.entity.Customer;
import com.longyan.entity.Employee;
import com.longyan.entity.Order;
import com.longyan.service.CustomerService;
import com.longyan.service.OrderService;
import com.longyan.service.PermissionService;
import com.longyan.util.DateUtil;
import com.longyan.util.SessionUtil;

/**
 * 订单管理页面
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class OrderController {
	@Resource
	private PermissionService permissionService; 
	
	@Resource
	private OrderService orderService; 
	
	@Resource
	private CustomerService customerService; 
	
	/**
	 * 跳转到订单页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/order", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String order(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String dim = "order"; //声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		//员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 会员管理。
		Integer right = employee.getRight_level(); 

		model.addAttribute("username", employee.getName());
		model.addAttribute("right", right);
		model.addAttribute("dim", dim);
		model.addAttribute("permission", permissionService.getPermissionByEmployeeId(employee.getId()) == null ? 
				"" : permissionService.getPermissionByEmployeeId(employee.getId()).getColumn_ids());
		
		System.out.println("到达主页面");
		return "admin/filter/order";
	}
	
	/**
	 * 跳转到订单详情页
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/order-detail", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String orderDetail(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String dim = "order"; //声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		//员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 会员管理。
		Integer right = employee.getRight_level(); 
		int order_id = Integer.parseInt(request.getParameter("order_id"));
		Order order = orderService.getOrderById(order_id);
		Customer customer = customerService.getCustomerById(order.getCustomer_id());

		model.addAttribute("username", employee.getName());
		model.addAttribute("right", right);
		model.addAttribute("dim", dim);
		model.addAttribute("customer_name", customer.getRealname());
		model.addAttribute("customer_phone", customer.getTelephone());
		model.addAttribute("customer_add", customer.getAddress());
		model.addAttribute("code", order.getCode());
		model.addAttribute("goods_name", order.getGoods_name());
		model.addAttribute("goods_price", order.getGoods_price());
		model.addAttribute("price", order.getPrice());
		model.addAttribute("count", order.getCount());
		model.addAttribute("discount", order.getDiscount());
		model.addAttribute("goods_info", order.getGoods_info());
		model.addAttribute("remark", order.getRemark());
		model.addAttribute("employee_name", order.getEmployee_name());
		model.addAttribute("ctime", DateUtil.getFormateDate(order.getCtime()));
		model.addAttribute("permission", permissionService.getPermissionByEmployeeId(employee.getId()) == null ? 
				"" : permissionService.getPermissionByEmployeeId(employee.getId()).getColumn_ids());
		
		System.out.println("到达主页面");
		return "admin/filter/order-detail";
	}
	
	/**
	 * 取得所有订单
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/get-all-order")
	public @ResponseBody String getAllOrder(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		List<Order> orders = orderService.getAllOrders();
		if(orders.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Order order:orders){
			jsonObject = new JSONObject();
			Customer customer = customerService.getCustomerById(order.getCustomer_id());
			
			jsonObject.put("id", order.getId());
			jsonObject.put("customer_id", order.getCustomer_id());
			jsonObject.put("customer_name", customer.getRealname());
			jsonObject.put("code", order.getCode());
			jsonObject.put("goods_name", order.getGoods_name());
			jsonObject.put("goods_price", order.getGoods_price());
			jsonObject.put("price", order.getPrice());
			jsonObject.put("count", order.getCount());
			jsonObject.put("discount", order.getDiscount());
			jsonObject.put("goods_info", order.getGoods_info());
			jsonObject.put("remark", order.getRemark());
			jsonObject.put("employee_name", order.getEmployee_name());
			jsonObject.put("employee_id", order.getEmployee_id());
			jsonObject.put("ctime", DateUtil.getFormateDate(order.getCtime()));
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
	
	/**
	 * 新增订单
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/add-order")
	public @ResponseBody String addReply(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Employee employee = (Employee) SessionUtil.getSession(response, request);//登录员工
		int customer_id = Integer.parseInt(request.getParameter("customer_id"));
		String order_code = DateUtil.getCodeTime(new Date());
		String goods_name = request.getParameter("goods_name");
		float goods_price = Float.parseFloat(request.getParameter("goods_price"));
		float price = Float.parseFloat(request.getParameter("price"));
		int count = Integer.parseInt(request.getParameter("count"));
		float discount = Float.parseFloat(request.getParameter("discount"));
		String goods_info = request.getParameter("goods_info");
		String remark = request.getParameter("remark");
		
		Order order = new Order();
		order.setCode(order_code);
		order.setCount(count);
		order.setCtime(new Date());
		order.setCustomer_id(customer_id);
		order.setDiscount(discount);
		order.setEmployee_id(employee.getId());
		order.setEmployee_name(employee.getName());
		order.setGoods_info(goods_info);
		order.setGoods_name(goods_name);
		order.setGoods_price(goods_price);
		order.setPrice(price);
		order.setRemark(remark);
		
		String code = orderService.addOrder(order);
		String err_msg = "";
		
		switch (Integer.parseInt(code)) {
			case 1001:
				err_msg = "add success";
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
	 * 删除订单
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/del-order")
	public @ResponseBody String delOrder(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String ids = request.getParameter("orderIds");
		
		String code = orderService.delMoreOrder(ids);
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
	@RequestMapping("/admin/filter/get-order-by-date")
	public @ResponseBody String geOrderByDate(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Date startDate = DateUtil.StrToDate(request.getParameter("start_date"));
		Date endDate = DateUtil.StrToDate(request.getParameter("end_date"));
		
		List<Order> orders =  orderService.getOrdersByDate(startDate, endDate);
		
		if(orders.size() == 0){
			return "[]";
		}
		
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = null;
		for(Order order:orders){
			jsonObject = new JSONObject();
			Customer customer = customerService.getCustomerById(order.getCustomer_id());
			
			jsonObject.put("id", order.getId());
			jsonObject.put("customer_id", order.getCustomer_id());
			jsonObject.put("customer_name", customer.getRealname());
			jsonObject.put("code", order.getCode());
			jsonObject.put("goods_name", order.getGoods_name());
			jsonObject.put("goods_price", order.getGoods_price());
			jsonObject.put("price", order.getPrice());
			jsonObject.put("count", order.getCount());
			jsonObject.put("discount", order.getDiscount());
			jsonObject.put("goods_info", order.getGoods_info());
			jsonObject.put("remark", order.getRemark());
			jsonObject.put("employee_name", order.getEmployee_name());
			jsonObject.put("employee_id", order.getEmployee_id());
			jsonObject.put("ctime", DateUtil.getFormateDate(order.getCtime()));
			
			jsonArray.add(jsonObject);
		}
		
		return jsonArray.toString();
	}
}
