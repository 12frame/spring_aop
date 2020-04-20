package com.yc.web.servlets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.bean.JsonModel;
import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;
import com.yc.bean.Resuser;
import com.yc.biz.ResroderBiz;
import com.yc.biz.impl.ResorderBizImpl;
import com.yc.utils.RequestUtil;
import com.yc.web.model.CartItem;

import static com.yc.utils.YcConstants.SESSIONCART;
import static com.yc.utils.YcConstants.LOGINUSER;
import static com.yc.utils.YcConstants.SESSIONTOTAL;

@WebServlet("/resuser/resorder.action")
public class ResorderServlet extends BaseServlet {

	private static final long serialVersionUID = -977974500768761840L;
	private ResroderBiz resorderBiz=new ResorderBizImpl();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if ("toFillForm".equals(op)) {
				toFillFormOp(request, response);
			}else if("makeOrder".equals(op)){
				makeOrderOp( request, response);
			}else if("showOrders".equals(op)){
				showOrdersOp( request, response);
			}else if( "showOrderDetail".equals(op)){
				showOrderDetailOp( request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showOrderDetailOp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String roid=request.getParameter("roid");
		Resorder resorder=new Resorder();
		resorder.setRoid(Integer.parseInt(roid));
		
		List<Resorderitem> listresorderitem=resorderBiz.findOrderItem(  resorder );
		JsonModel jsonModel=new JsonModel();
		jsonModel.setCode(1);
		jsonModel.setObj(listresorderitem);
		super.writeJson(response, jsonModel);
		
	}

	private void showOrdersOp(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception {
		//当前用户
		Resuser resuser=(Resuser) session.getAttribute( LOGINUSER  );
		
		Resorder resorder=new Resorder();
		resorder.setUserid(resuser.getUserid());
		resorder.setOrderBy( "ordertime"    );
		resorder.setOrder("desc");
		int pages=1;
		int pageSize=5;
		if( request.getParameter("pages")!=null){
			pages=Integer.parseInt( request.getParameter("pages"));
		}
		if( request.getParameter("pageSize")!=null){
			pageSize=Integer.parseInt( request.getParameter("pageSize"));
		}
		resorder.setPages(pages);
		resorder.setPageSize(pageSize);
		JsonModel<Resorder> jsonModel=resorderBiz.find(resorder);
		request.setAttribute("resorderJsonModel", jsonModel);
		request.getRequestDispatcher("/WEB-INF/pages/showOrders.jsp").forward(request, response);
	}

	private void makeOrderOp(HttpServletRequest request, HttpServletResponse response) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ServletException, IOException {
		Map<Integer, CartItem> shopCart=(Map<Integer, CartItem>) session.getAttribute(SESSIONCART);
		Resuser resuser=(Resuser) session.getAttribute( LOGINUSER  );
		Resorder resorder=RequestUtil.parseRequest(request, Resorder.class);
		
		try {
			resorderBiz.makeOrder(resorder, shopCart, resuser);
			
			session.removeAttribute( SESSIONCART  );
			session.setAttribute(SESSIONTOTAL, 0.0);
			request.getRequestDispatcher("/WEB-INF/pages/seeYou.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "make order failed,please contact the administrator...");
			request.getRequestDispatcher("/WEB-INF/pages/checkOut.jsp").forward(request, response);
		}
		
		
	}

	private void toFillFormOp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(   session.getAttribute(SESSIONCART)==null || ((Map)session.getAttribute(SESSIONCART)).size()<=0){
			request.setAttribute("msg", "cart should not be empty");
			request.getRequestDispatcher("/WEB-INF/pages/shopCart.jsp").forward(request, response);
		}else{
			request.getRequestDispatcher("/WEB-INF/pages/checkOut.jsp").forward(request, response);
		}
	}

}
