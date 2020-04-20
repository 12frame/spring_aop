package com.yc.utils;

//工具类：转换金额,将数字型的金额转换为中文大写
public class MoneyUtil {

	private static final String unit = "万仟佰拾亿仟佰拾万仟佰拾元角分";// 单位
	private static final String digit = "零壹贰叁肆伍陆柒捌玖";// 数字 digit.chartAt(3)
	private static final double MAX_VALUE = 9999999999999.99D;// final常量 最大值

	public static void main(String[] args) {
		System.out.println(MoneyUtil.change(100.3));
	}

	public static String change(double v) {
		// 1.参数的有效性<0 >MAX_VALUE
		if (v < 0 || v > MAX_VALUE) {
			return "参数非法!";
		}

		// 2.是0，则直接输出
		long l = Math.round(v * 100);// 进行四舍五入;如123.45就变成12345
		if (l == 0) {
			return "零元整";
		}// 3.将double转为字符串+""

		String strValue = l + "";
		// 4.真正的逻辑部分 - >没有小数要加整
		// 定义两个变量：
		// 变量1控制数字
		int i = 0;
		// 变量2控制单位
		int j = unit.length() - strValue.length();
		String result = "";// 结果存这个
		boolean isZero = false;
		for (; i < strValue.length(); i++, j++) {
			// 取出每一个字符 1 2 3 4 5 字符串用字符串""比较用equals
			char ch = strValue.charAt(i);

			if (ch == '0') {// ''是字符，字符比较用==,字符串""用equals比较
				isZero = true;
				if (unit.charAt(j) == '亿' || unit.charAt(j) == ('万')
						|| unit.charAt(j) == ('元')) {// eg:10.3
					// 只要是以上三位,则数值不要,单位要
					result = result + unit.charAt(j);
				}
			} else {
				// 当前位不是0,但它的前位是0，则要 eg:103
				if (isZero == true) {
					result = result + "零";
					isZero = false;
				}
				// 拼接的规则:向数字+单位
				result = result + digit.charAt(ch - '0') + unit.charAt(j);// charAt()里面取的是字符
			}
		}
		if (result.endsWith("元")) {
			result += "整";
		}
		return result;
	}
}