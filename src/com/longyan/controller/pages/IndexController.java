package com.longyan.controller.pages;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * index
 * @author tracyqiu
 *
 */
@Controller
public class IndexController {

	/**
	 * 跳转到登录页面
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/", method={RequestMethod.GET, RequestMethod.POST})
	public String login(Model model,
			HttpServletResponse response) throws IOException {
		
		System.out.println("进入登录页面");
		return "pages/index";
	}
}
