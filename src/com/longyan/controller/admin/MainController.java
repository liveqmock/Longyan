package com.longyan.controller.admin;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longyan.entity.Employee;
import com.longyan.util.IPUtil;
import com.longyan.util.SessionUtil;

/**
 * 后台主界面
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class MainController {

	/**
	 * 跳转到登录页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/main", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String main(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String dim = ""; //声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		String ip = getIpAddr(request); // 获取登录ID地址
		IPUtil ipUtil = new IPUtil();
		String addr = ipUtil.getAddresses("ip=" + ip, "utf-8");
		String time = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
		
		//员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 会员管理。
		Integer right = employee.getRight_level(); 
		String permission = right == 0 ? "管理员" : "普通员工";

		model.addAttribute("username", employee.getName());
		model.addAttribute("telephone", employee.getTelephone());
		model.addAttribute("sex", employee.getSex());
		model.addAttribute("province", employee.getProvince());
		model.addAttribute("email", employee.getEmail());
		model.addAttribute("permission", permission);
		model.addAttribute("ip", ip);
		model.addAttribute("right", right);
		model.addAttribute("addr", addr);
		model.addAttribute("time", time);
		model.addAttribute("dim", dim);
		
		System.out.println("到达主页面");
		return "admin/filter/main";
	}

	/**
	 * 获取用户真实IP地址
	 * 
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static void main(String[] args) {
		System.out.println((new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
	}

}
