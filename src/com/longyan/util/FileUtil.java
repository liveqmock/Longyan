package com.longyan.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

/**
 * 模板文件的读取
 * @author tracyqiu
 *
 */
public class FileUtil {
	/**
	 * 读取模板文件内容
	 * 
	 * @param path
	 * @return
	 */
	public static String readTemplate(String path) {
		String content = "";
		try {
			String encoding = "GBK";
			File file = new File(path);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
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
	 * 
	 * @param content
	 */
	public static String writeTemplate(HttpServletRequest request, String content,
			String filename, String type) {
		String rootFile = request.getSession().getServletContext().getRealPath("")
				+ "\\templates\\" + type;

		FileOutputStream out = null;
		File file;
		try {
			file = new File(rootFile);
			if (!file.exists()) {
				file.mkdirs();
			}
			File fileDat = new File(rootFile + "\\" + filename + ".ftl");
			out = new FileOutputStream(fileDat);
			byte[] contentInBytes = content.getBytes();
			out.write(contentInBytes);
			out.flush();
			out.close();
			System.out.println("创建文件" + rootFile + "\\" + filename + "成功~---------------~");
			
			return rootFile + "\\" + filename + ".ftl";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
