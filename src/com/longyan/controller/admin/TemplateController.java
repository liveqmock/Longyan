package com.longyan.controller.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.longyan.entity.Template;
import com.longyan.entity.Employee;
import com.longyan.service.TemplateService;
import com.longyan.service.PermissionService;
import com.longyan.util.SessionUtil;

/**
 * 模板管理页面
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class TemplateController {
	@Resource
	private PermissionService permissionService;

	@Resource
	private TemplateService templateService;

	/**
	 * 跳转到登录页面
	 * 
	 * @param model
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/filter/template", method = {RequestMethod.GET, RequestMethod.POST })
	public String template(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String dim = request.getParameter("dim"); // 声明访问页面
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		// 员工权限控制right_level值说明：0管理员权限，能看所有栏目；1 有系统设置权限； 2 系统设置+订单系统； 3 系统设置+订单系统 + 会员管理。
		Integer right = employee.getRight_level();
		Integer id = Integer.parseInt(request.getParameter("id"));
		Template template = null;

		if (dim.equals("column")) { // 编辑栏目模板
			template = templateService.getTemplateByColumnId(id);
		} else if (dim.equals("content")) { // 编辑内容模板
			template = templateService.getTemplateByContentId(id);
		}

		if(template == null){
			model.addAttribute("column_id", -1);
			model.addAttribute("content_id", -1);
			model.addAttribute("filename", "");
			model.addAttribute("path", "");
			model.addAttribute("template_content", "");
		}else{
			model.addAttribute("column_id", template.getColumn_id());
			model.addAttribute("content_id", template.getContent_id());
			model.addAttribute("filename", template.getFilename());
			model.addAttribute("path", template.getPath());
			model.addAttribute("template_content", readTemplate(template.getPath()));
		}
		model.addAttribute("username", employee.getName());
		model.addAttribute("right", right);
		model.addAttribute("dim", dim);
		model.addAttribute("permission", permissionService.getPermissionByEmployeeId(employee.getId()) == null ? ""
				: permissionService.getPermissionByEmployeeId(employee.getId()).getColumn_ids());

		return "admin/filter/template";
	}
	
	/**
	 * 修改模板信息
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/admin/filter/update-template")
	public @ResponseBody String updateTemplate(Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Employee employee = (Employee) SessionUtil.getSession(response, request);// 登录人
		String dim = request.getParameter("dim");
		Integer id = Integer.parseInt(request.getParameter("id"));
		Template template = null;
		Integer column_id = -1;
		Integer content_id = -1;
		if (dim.equals("column")) { // 编辑栏目模板
			template = templateService.getTemplateByColumnId(id);
			column_id = id;
		} else if (dim.equals("content")) { // 编辑内容模板
			template = templateService.getTemplateByContentId(id);
			content_id = id;
		}
		String filename = request.getParameter("filename");
		String template_content = request.getParameter("template_content");
		String create_user = employee.getName();
		String path = writeTemplate(request, template_content, filename, dim);
		String codeStr = "";
		
		if(template == null){
			template = new Template();
			template.setColumn_id(column_id);
			template.setContent_id(content_id);
			template.setFilename(filename);
			template.setPath(path);
			template.setCreate_user(create_user);
			codeStr = templateService.addTemplate(template);
		}else {
			template.setColumn_id(column_id);
			template.setContent_id(content_id);
			template.setFilename(filename);
			template.setPath(path);
			template.setCreate_user(create_user);
			codeStr = templateService.modifyTemplate(template);
		}
		String err_msg = "";
		
		switch (Integer.parseInt(codeStr)) {
			case 1001:
				err_msg = "add success";
				break;
			case 1002:
				err_msg = "the template already exist!";
				break;
			case 1003:
				err_msg = "unkown error!";
				break;
			case 2001:
				err_msg = "update success";
				break;
			case 2002:
				err_msg = "the template does not exist!";
				break;
			case 2003:
				err_msg = "unkown error!";
				break;
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", codeStr);
		jsonObject.put("msg", err_msg);
		
		return jsonObject.toString();
	}

	/**
	 * 读取模板文件内容
	 * 
	 * @param path
	 * @return
	 */
	private String readTemplate(String path) {
		String content = "";
		try {
			String encoding = "UTF-8";
			File file = new File(path);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				
				while ((lineTxt = bufferedReader.readLine()) != null) {
					content += lineTxt;
				}
				read.close();
				return content;
			} else {
				System.out.println("找不到指定的文件");
				return "";
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 写文件
	 * @param content
	 */
	private String writeTemplate(HttpServletRequest request, String content, String filename, String type){
		String path = request.getSession().getServletContext().getRealPath("") + "/templates/" + type + "/" + filename + ".ftl";
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("文件" + path + "创建成功---------");
			return path;

		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}
