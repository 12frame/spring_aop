//重新获取验证码
function changeVilidateCode(obj) {
	var timenow = new Date().getTime();
	obj.src = "image.jsp?d=" + timenow; // 为了让服务器认为客户端的每次请求验证码都是全新的，所以加入了时间cuo
}