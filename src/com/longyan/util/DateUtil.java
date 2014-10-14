package com.longyan.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author tracyqiu
 * 
 */
public class DateUtil {
	
	/**
	 * 取得格式化过后的时间
	 * @param date
	 * @return
	 */
	public static String getFormateDate(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		String date_str = df.format(date);
		
		return date_str;
	}
}
