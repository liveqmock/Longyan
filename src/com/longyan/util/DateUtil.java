package com.longyan.util;

import java.text.ParseException;
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
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormateDate(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date_str = df.format(date);

		return date_str;
	}
	
	/**
	 * 根据时间生成code
	 * @param date
	 * @return
	 */
	public static String getCodeTime(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String date_str = df.format(date);

		return date_str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate2(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getCodeTime(new Date()));
	}
}
