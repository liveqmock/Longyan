package com.longyan.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.longyan.entity.Employee;
import com.longyan.entity.Permission;
import com.longyan.service.PermissionService;
import com.longyan.util.SessionUtil;

public class LoginRightFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L;
	
	private static BeanFactory factory = new ClassPathXmlApplicationContext("applicationContext-*.xml");
	PermissionService permissionService = (PermissionService)factory.getBean("permissionService");
	
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String url = request.getRequestURL().toString(); //获取path
		String path = "";
		if(url.indexOf("?") > -1){
			path = url.substring(0, url.indexOf("?")).substring(url.substring(url.indexOf("?")).lastIndexOf("/") + 1);
		}else {
			path = url.substring(url.lastIndexOf("/") + 1);
		}
		
		Employee user = (Employee)SessionUtil.getSession(response, request);// 登录人
		
		if (user == null) {  //未登录
			response.sendRedirect(request.getContextPath() + "/admin/login");
		}else {
			//员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 会员管理。
			Integer right = user.getRight_level(); 
			if(right == 0){   //管理员直接通过
				arg2.doFilter(arg0, arg1);
			}else {  //其他角色进行权限验证
				if(authorized(user, right, path)){
					arg2.doFilter(arg0, arg1);
				}else {
					response.sendRedirect(request.getContextPath() + "/admin/unauthorized");
				}
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
	
	/**
	 * 权限验证
	 * @param right
	 * @param path
	 * @return
	 */
	public boolean authorized(Employee employee, Integer right, String path){
		boolean ret = false;
		
		switch(right){
			case 1:
				if(filteColumn(employee, path) || path.indexOf("main") > -1 || path.indexOf("upload") > -1){
					ret = true;
				}else {
					ret = false;
				}
				break;
			case 2:
				if(filteColumn(employee, path) || path.indexOf("order") > -1 || path.indexOf("main") > -1 || path.indexOf("upload") > -1){
					ret = true;
				}else {
					ret = false;
				}
				break;
			case 3:
				if(filteColumn(employee, path) || path.indexOf("order") > -1 || path.indexOf("customer") > -1 || path.indexOf("main") > -1 || path.indexOf("upload") > -1){
					ret = true;
				}else {
					ret = false;
				}
				break;
		}
		return ret;
	}
	
	/**
	 * 系统设置过滤
	 * @param employee
	 * @param path
	 * @return
	 */
	public boolean filteColumn(Employee employee, String path){
		Permission permission = permissionService.getPermissionByEmployeeId(employee.getId());
		if(permission == null) return false;
		
		String columnStr = permission.getColumn_ids();
		String columns[] = columnStr.split("##");
		
		if("".equals(columnStr)){
			return false;
		}else if(columns.length > 0){
			for(int i = 0, len = columns.length; i < len; i++){
				if(path.indexOf(columns[i]) > -1){
					return true;
				}
			}
		}else {
			return false;
		}
		return false;
	}

}