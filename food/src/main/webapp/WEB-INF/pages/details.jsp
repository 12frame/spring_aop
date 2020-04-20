<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<link href="magiczoom/magiczoom.css" rel="stylesheet" type="text/css" media="screen" />
<script src="magiczoom/magiczoom.js" type="text/javascript"></script>
<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td width="90" height="90" valign="top">
			<img src="../pics/${resfood.fphoto }" border="0" width="80" height="80">
		</td>
		<td valign="top">
			<table width="98%" border="0" cellspacing="1" cellpadding="0" align="center">
				<tr>
					<td>
						<strong>${resfood.fname }</strong>
					</td>
				</tr>
				<tr>
					<td height="21">
						原价：<strike>人民币${resfood.normprice }元</strike><br> <font color="#ff0000">现价：人民币${resfood.realprice }元</font><br>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="30">编号: ${resfood.fid }</td>
		<td>
			<table width="145" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<td align="center" width="70">
						<a href="resfood.action?op=addCart&fid=${resfood.fid }">
							<img src="images/buy_cn.gif" border=0>
						</a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<P align=center>
	<STRONG><FONT size=4>详细资料</FONT></STRONG>
</P>
<HR align=center width="100%" color=#000000 noShade SIZE=1>
<P align=center>
	<a href="${resfood.fphoto }" title="${resfood.fname }" rel="zoom-fade:true; smoothing-speed:17" class="MagicZoom">
		<img src="../pics/${resfood.fphoto }" width="150px" height="150px" />
	</a>
</P>
<P align=center>${resfood.detail }</P>
<br>
</td>
</tr>
</table>
<%@ include file="bottom.jsp"%>
