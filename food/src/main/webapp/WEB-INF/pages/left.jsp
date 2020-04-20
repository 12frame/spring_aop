<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		$
				.ajax({
					url : "resuser.action?op=isResuserLogin",
					type : "POST",
					dataType : "JSON",
					success : function(data) {
						var str = "";
						if (data.code == 1) {
							str += "<ul>";
							str += "<li>欢迎您:" + data.obj.username + "</li>";
							str += "<li><a href='resuser.action?op=logout'>安全退出</a></li>";
							str += "<li><a href='resuser/resuser.action?op=sendMail'>修改密码</a></li>";
							str += "<li><a href='resuser/resorder.action?op=showOrders'>查看历史订单</a></li>";
							str += "</ul>";

						} else {
							str = "<a href='resuser.action?op=toLogin'>登录</a><a href='resuser.action?op=toReg'>注册</a>";
						}

						$("#loginStatus").html(str);
					}
				});

		$
				.ajax({
					type : "POST",
					url : "resfood.action?op=redis",
					dataType : "JSON",
					success : function(data) {
						var str = "";
						if (data) {
							for (var i = 0; i < data.length; i++) {
								var arr = data[i].split("_");
								str += '<a href="resfood.action?op=details&fid='
										+ arr[0]
										+ '"><img src="'+arr[2]+'" width="30px" height="30px" >'
										+ arr[1] + '   </a><br />';
							}
							$("#recentBrowser").html(str);
						}

					}
				});
		
		$
		.ajax({
			type : "POST",
			url : "weather.action",
			dataType : "JSON",
			success : function(data) {
				if( data.code==1){
					$("#addressinfo").html(data.obj.addressInfo);
					$("#weatherDiv").html( data.obj.weatherInfo);
				}

			}
		});

	});
</script>
<TABLE cellSpacing=0 cellPadding=0 width=776 align=center border=0>
	<TBODY>
		<TR vAlign=top>
			<TD width=181 background="images/002.gif">
				<TABLE cellSpacing=0 cellPadding=0 width=181 border=0>
					<TBODY>
						<TR>
							<TD>
								<IMG height=234 src="images/left_top.jpg" width=181>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
				<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
					<TBODY>
						<TR>
							<TD align=middle height=30>
								<FONT color=#000000> <SCRIPT language=javascript src="images/DateTime2.js"></SCRIPT> <SCRIPT language=javascript>
									tick('cn');
								</SCRIPT>
								</FONT>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
				<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
					<TBODY>
						<TR>
							<TD>&nbsp;</TD>
						</TR>
						<TR>
							<TD  height=4>
								 <span id="addressinfo"></span>
								 <hr />
								<div id="loginStatus"></div>
							</TD>
						</TR>
						<TR>
							<TD align=middle>
								<TABLE cellSpacing=0 cellPadding=0 width="94%" border=0>
									<TBODY>
										<TR>
											<TD>
											    <hr />
												历史浏览记录
												<div id="recentBrowser"></div>
											</TD>
										</TR>
									</TBODY>
								</TABLE>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
				<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
					<TBODY>
						<TR>
							<TD>
								<hr />
								
								<div id="weatherDiv"></div>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</TD>
			<TD>
				<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
					<TBODY>
						<TR>
							<TD>
								<IMG height=72 src="images/001.jpg" width=595>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
				<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
					<TBODY>
						<TR>
							<TD width="90%">
								<div class='cnt'>欢迎您使用我学我会网上订餐系统，祝您用餐愉快！</div>
								&nbsp;&nbsp;
							</TD>
						</TR>
					</TBODY>
				</TABLE>
				<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
					<TBODY>
						<TR>
							<TD align=right background="images/004.gif" height=19></TD>
						</TR>
					</TBODY>
				</TABLE>
				<TABLE cellSpacing=0 cellPadding=0 width="96%" align=center border=0>
					<TBODY>
						<TR>
							<TD>
								<TABLE cellSpacing=1 cellPadding=1 width="100%" align=center bgColor=#c0c0c0 border=0>
									<TBODY>
										<TR bgColor=#dadada>
											<TD width="100%" align="center">我学我会网上点餐系统用户请直接登录</TD>
										</TR>
									</TBODY>
								</TABLE>
								<BR>
							</TD>
						</TR>
					</TBODY>
				</TABLE>