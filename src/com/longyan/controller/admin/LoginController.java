package com.longyan.controller.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 后台登录
 * @author tracyqiu
 *
 */
@Controller
public class LoginController {

	@RequestMapping(value="/admin/login", method={RequestMethod.GET, RequestMethod.POST})
	public String login(Model model,
			HttpServletResponse response) throws IOException {
		
		System.out.println("come here");
		return "admin/login";
	}
	
}
