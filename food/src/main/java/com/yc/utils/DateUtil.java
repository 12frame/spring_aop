package com.yc.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期操作工具类
 */
public class DateUtil {

	private static DateFormat df=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	/**
	 * 将  str 转为   Date对象.
	 * @param str
	 * @param format   : 要求的格式，可以为空.
	 * @return
	 * @throws ParseException
	 */
	public static Date parseStringToDate(   String str, String format) throws ParseException{
		if(  format!=null  ){
			df=new SimpleDateFormat(format);
		}
		Date d=df.parse(   str );
		return d;
	}
	/**
	 * 将  Date 转为  str
	 * @param d
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String parseDateToString(   Date d, String format) throws ParseException{
		if(  format!=null  ){
			df=new SimpleDateFormat(format);
		}
		String str= df.format(   d );
		return str;
	}
}
