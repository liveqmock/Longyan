package com.longyan.controller.admin;

import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 处理图片上传
 * 
 * @author tracyqiu
 * 
 */
@Controller
public class FileUploadController {

	@RequestMapping("/admin/filter/upload")
	public @ResponseBody String processImageUpload(@RequestParam() MultipartFile file, 
			HttpServletRequest request) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("");
		
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			FileOutputStream fos = new FileOutputStream(path + "/upload/" + file.getOriginalFilename()); // 上传到写死的上传路径
			fos.write(bytes); // 写入文件
		}
		System.out.println("name: " + file.getOriginalFilename() + "  size: "
				+ file.getSize()); // 打印文件大小和文件名称
		return "/Longyan/upload/" + file.getOriginalFilename(); 
	}
}
