<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="100%">
	<tr valign="top">
		<td>
			<table width="98%" border="0" cellspacing="1" cellpadding="2" align="center">
				<tr valign="bottom">
					<td height="30">
						<font color="#000000">您的购物车中有以下商品</font>
					</td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="2" cellpadding="0" align="center">
				<tr bgcolor=#808000>
					<td height="1" bgcolor="#999999"></td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="2" cellpadding="0" align="center">
				<tr>
					<td height="5"></td>
				</tr>
			</table>
			<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
				<tr>
					<td>
						<table width="100%" border="0" align="CENTER" cellpadding="2" cellspacing="1" bgcolor="#c0c0c0">
							<tr bgcolor="#dadada">
								<td height="22" width="50">
									<div align="CENTER">
										<font color="#000000">编号</font>
									</div>
								</td>
								<td width="610" height="22">
									<div align="CENTER">
										<font color="#000000">商品名称</font>
									</div>
								</td>
								<td height="22" width="104">
									<div align="CENTER">
										<font color="#000000">单价</font>
									</div>
								</td>
								<td height="22" width="100">
									<div align="CENTER">
										<font color="#000000">数量</font>
									</div>
								</td>
								<td width="116" height="22">
									<div align="CENTER">
										<font color="#000000">金额</font>
									</div>
								</td>
								<td width="116" height="22">
									<div align="CENTER">
										<font color="#000000">操作</font>
									</div>
								</td>
							</tr>
							<c:forEach items="${sessionCart }" var="cartItem">
								<tr bgcolor="#ffffff">
									<td width="50" align="center" height="22">
										<font color="#000000">${cartItem.key }</font>
									</td>
									<td align="center" height="22">
										<font color="#000000">${cartItem.value.resfood.fname }</font>
										<input type="hidden" name="fid" value="${cartItem.value.resfood.fid }">
									</td>
									<td width="104" align="center" height="22">
										<font color="#000000">￥${cartItem.value.resfood.realprice }</font>
									</td>
									<td width="100" class="hh" align="center" height="22">${cartItem.value.count }</td>
									<td width="116" class="bb" align="center" height="22">
										<font color="#000000">￥${cartItem.value.smallCount }</font>
									</td>
									<td width="116" class="bb" align="center" height="22">
										<a href="resfood.action?op=delCartItem&fid=${cartItem.key }">删除</a>
										<a href="resfood.action?op=changeCount&fid=${cartItem.key }&number=1">+</a>
										<a href="resfood.action?op=changeCount&fid=${cartItem.key }&number=-1">-</a>
									</td>
									
								</tr>
							</c:forEach>
							<tr bgcolor="#dadada">
								<td width="50" height="22" align="center">
									<font color="#000000">合计</font>
								</td>
								<td height="22" align="center">
									<font color="#000000">-</font>
								</td>
								<td width="104" height="22" align="center">
									<font color="#000000">-</font>
								</td>
								<td width="100" class="hh" height="22" align="center">
									<font color="#000000">-</font>
								</td>
								<td width="116" class="bb" align="center" height="22">
									<font color="#000000">￥${sessionTotal } </font>
								</td>
							</tr>
						</table>
						<br>
						<table width="300" border="0" cellspacing="1" cellpadding="4" align="CENTER" bgcolor="#c0c0c0">
							<tr bgcolor="#dadada">
								<td height="10" align="center">
									<a href="resfood.action?op=clearCart">
										<font color="#000000">清空购物车</font>
									</a>
								</td>
								<td height="10" align="center" style="cursor: hand" onClick="javascript:location.href='resfood.action?op=findAllFoods'">
									<font color="#000000">继续购物</font>
								</td>
								<td height="10" align="center" style="cursor: hand" >
									<a href="resuser/resorder.action?op=toFillForm"><font color="#000000">生成订单</font></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<%@ include file="bottom.jsp"%>