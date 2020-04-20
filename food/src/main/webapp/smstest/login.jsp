<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
</head>
<script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
<script type="text/javascript">
function get_mobile_code(){
	$.ajax({
		type:"POST",
		url:"sms.jsp",
		data:{mobile:jQuery.trim($('#mobile').val())},
		dataType:"xml",
		success:function(msg){
			
			
		}
	});
	RemainTime()
}
var iTime = 30;
var Account;
function RemainTime(){
	document.getElementById('zphone').disabled = true;
	var iSecond,sSecond="",sTime="";
	if (iTime >= 0){
		iSecond = parseInt(iTime%60);
		iMinute = parseInt(iTime/60)
		if (iSecond >= 0){
			if(iMinute>0){
				sSecond = iMinute + "分" + iSecond + "秒";
			}else{
				sSecond = iSecond + "秒";
			}
		}
		sTime=sSecond;
		if(iTime==0){
			clearTimeout(Account);
			sTime='获取手机验证码';
			iTime = 30;
			document.getElementById('zphone').disabled = false;
		}else{
			Account = setTimeout("RemainTime()",1000);
			iTime=iTime-1;
		}
	}else{
		sTime='';
	}
	document.getElementById('zphone').value = sTime;
}
</script>
<body>

<center>用户登录</center>
	<form action="doLogin.jsp" method="post" name="formUser">
		用户名：<input type="text" name="uname" value="aa"/><br />
		密码：<input type="text" name="upass" value="aa"/><br />
		手机号码：<input type="text" id="mobile" name="mobile" value="18473481898" />
				<input id="zphone" type="button" value=" 获取手机验证码  " onClick="get_mobile_code();">
		<br />
		验证码：<input type="text" id="smscode" name="smscode" /><br />
		<input type="submit" value="登录"/>
	</form>

</body>
</html>