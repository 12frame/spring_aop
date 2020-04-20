<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<script>
	function showDetail(roid) {
		$.ajax({
			url : "resuser/resorder.action",
			data : "op=showOrderDetail&roid=" + roid,
			dataType : "JSON",
			success : function(data) {
				if (data.code == 1) {
					$(".orderitem").hide();
					var str = "";
					if (data.obj) {
						for (var i = 0; i < data.obj.length; i++) {
							var resorderitem = data.obj[i];
							str += resorderitem.fid + "&nbsp;&nbsp;"
									+ resorderitem.dealprice + "&nbsp;&nbsp;"
									+ resorderitem.num + "<br />";
						}
					}
					$("#orderitem_" + roid).html(str);
					$("#orderitem_" + roid).show();
				}
			}
		});
	}
</script>
<c:forEach items="${resorderJsonModel.rows }" var="resorder">
<hr />
	<div>
		<div>
			<span>${resorder.ordertime }</span>
			&nbsp;&nbsp;
			<span>
				${resorder.statusStr }
			</span>
			&nbsp;&nbsp;
			<span>
				<a href="javascript:showDetail(${resorder.roid })">查看详情</a>
			</span>
		</div>
		
		
		<div class="orderitem" id="orderitem_${resorder.roid }"></div>
		
	</div>
</c:forEach>
<div>
    <hr />
	<yc:pageBar pages="${resorderJsonModel.pages }" pagesize="${resorderJsonModel.pageSize }" total="${resorderJsonModel.total }" action="resuser/resorder.action?op=showOrders"></yc:pageBar>
</div>
<%@ include file="bottom.jsp"%>













