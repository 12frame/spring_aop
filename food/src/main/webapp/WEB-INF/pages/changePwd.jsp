<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ include file="header.jsp" %>


<form method="POST" name="changePwd" onSubmit="return checkUserInfo()"
	action="resuser.action">
	<input type="hidden" name="op" value="changePwd"/>
	<input type="hidden"  name="userid" value="${resuser.userid}"/>
	<table width="100%" border="0">
		<tr>
			<td width="15%">&nbsp;</td>
			<td width="12%">&nbsp;</td>
			<td width="29%">&nbsp;</td>
			<td width="44%">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td valign="middle" align="center">新密码:</td>
			<td valign="top"><input type="password" name="pwd"
				size="20" class="input"
			></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td>&nbsp;</td>
			<td valign="middle" align="center">确认密码：</td>
			<td valign="top"><input type="password" name="repwd"
				size="20" class="input"
			></td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td valign="middle" align="center">验证码：</td>
			<td valign="top">
				<input type="text" name="validacode" size="20" class="input">
			</td>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="2" align="center"><input type="submit"
				name="Submit" value="确认修改"
			></td>
			<td>&nbsp;</td>
		</tr>
		
		<tr>
			<td height="33" cospan="4">${msg}</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>
</form>
<%@ include file="bottom.jsp" %>
