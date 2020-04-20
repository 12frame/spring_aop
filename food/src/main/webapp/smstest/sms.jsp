<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%><%@ page import="java.net.*"%><%@ page import="java.util.*"%><%@ page import="java.lang.*"%>

<%@ page import="com.yc.utils.*,com.yc.dao.*"%>
<%
	int mobile_code = (int) ((Math.random() * 9 + 1) * 100000); //获取随机数

	String mobile = request.getParameter("mobile");
	String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

	String address = MyProperties.getInstance().getProperty("sms_server");
	String sms_apiid = MyProperties.getInstance().getProperty("sms_apiid");
	String sms_apikey = MyProperties.getInstance().getProperty("sms_apikey");
	//parameter中的参数名是网站约定的,不能变...
	Map<String, String> parameter = new HashMap<String, String>();
	parameter.put("account", sms_apiid);
	parameter.put("password", sms_apikey);
	parameter.put("mobile", mobile);
	parameter.put("content", content);
	String result = NetUtil.getNetResponse(address, parameter).toString();

	out.println(result);
	session.setAttribute("mobile_code", mobile_code);
%>