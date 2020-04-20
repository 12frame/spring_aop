package com.yc.utils;

public class ImageUtils {
	public static String genImg(int count) {
		String str = count + "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			sb.append("<img src='images/" + ch + ".gif' />");
		}
		return sb.toString();
	}
}
