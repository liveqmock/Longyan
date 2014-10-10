package com.longyan.controller.admin;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台登录
 * @author tracyqiu
 *
 */
@Controller
public class LoginController {

	@RequestMapping("/admin/login")
	public String login(Model model,
			String startDate,
			String endDate,
			Integer statBy,
			Integer index,
			String wmCityId,
			String wmCityName,
			HttpServletResponse response) throws IOException {
		
		System.out.println("come here");
		return "/view/admin/login";
	}
	
}
