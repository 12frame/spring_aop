<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>





<TABLE cellSpacing=1 cellPadding=1 width="100%" align=center bgColor=#c0c0c0 border=0>
	<TBODY>
		<TR bgColor=#dadada>
			<TD width="100%" align="right">
				<a href="resfood.action?op=showCart">
					<img src="images/lcart_cn.gif" border=0>
				</a>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE cellSpacing=2 cellPadding=1 width="100%" align=center border=0>
	<TBODY>

	  <c:forEach items="${jsonModel.rows}" var="resfood" varStatus="vs">
	        <c:if test="${vs.index%2==0 }">
	        	<TR>
	        </c:if>
	        
	        
	        
	        <TD>
				<TABLE height="100%" cellSpacing=1 cellPadding=2 border=0>
					<TBODY>
						<TR>
							<TD vAlign=top width=90 height=90>
								<A href=# target=_blank>
									<IMG height=80 alt=点击图片查看内容 src="../pics/${resfood.fphoto }" width=80 border=0>
								</A>
							</TD>
							<TD vAlign=top>
								<TABLE cellSpacing=1 cellPadding=0 width="100%" align=center border=0>
									<TBODY>
										<TR>
											<TD>
												<A href=# target=_blank>
													<STRONG>${resfood.fname }</STRONG>
												</A>
											</TD>
										</TR>
										<TR>
											<TD height=21>
												<FONT color='grey'><strike>原价：人民币${resfood.normprice }元</strike></FONT><br />
												<FONT color=#ff0000>跳楼价：人民币${resfood.realprice }元</FONT><BR>
											</TD>
										</TR>
									</TBODY>
								</TABLE>
							</TD>
						</TR>
						<TR>
							<TD height=28>编号: ${resfood.fid }</TD>
							<TD>
								<TABLE cellSpacing=1 cellPadding=0 width=145 border=0>
									<TBODY>
										<TR>
											<TD align="middle" width="70">
												<a href="resfood.action?op=addCart&fid=${resfood.fid }">
													<img src="images/buy_cn.gif" border="0" longdesc="shoppingCart.html">
												</a>
											</TD>
											<TD align=middle width=70>
												<A href="resfood.action?op=details&fid=${resfood.fid}" target=_blank>
													<IMG src="images/detail_cn.gif" border=0>
												</A>
											</TD>
										</TR>
									</TBODY>
								</TABLE>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
			</TD>
	        
	        
	        <c:if test="${vs.index%2==1 }">
	        	</TR>
	        </c:if>
	  
	  </c:forEach>
	
		
	</TBODY>
</TABLE>

<div >

<yc:pageBar  action="resfood.action?op=findAllFoods"  pages="${jsonModel.pages }" pagesize="${jsonModel.pageSize }" total="${jsonModel.total }"  />



</div>


</TD>
</TR>
</TBODY>
</TABLE>
<%@ include file="bottom.jsp"%>
