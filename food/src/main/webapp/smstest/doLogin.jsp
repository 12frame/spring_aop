<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String code=session.getAttribute("mobile_code")+"";
String smscode=request.getParameter("smscode")+"";
if(smscode.equals(code)){
	out.println("<script>alert('登录成功！！！')</script>");
}else{
	out.println("<script>alert('验证码错误！登录失败！！！')</script>");

}
%>