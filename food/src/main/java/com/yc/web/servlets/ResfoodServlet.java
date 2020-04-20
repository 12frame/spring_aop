package com.yc.web.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.bean.JsonModel;
import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBiz;
import com.yc.biz.impl.ResfoodBizImpl;
import com.yc.utils.YcConstants;
import com.yc.web.model.CartItem;

import static com.yc.utils.YcConstants.RESFOODLIST;
import static com.yc.utils.YcConstants.SESSIONCART;
import static com.yc.utils.YcConstants.SESSIONTOTAL;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.yc.utils.YcConstants.ERROR_404;
import static com.yc.utils.YcConstants.ERROR_500;

import static com.yc.utils.YcConstants.JSONMODEL;

@WebServlet("/resfood.action")
public class ResfoodServlet extends BaseServlet {
	private ResfoodBiz resfoodBiz = new ResfoodBizImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		try {
			if ("findAllFoods".equals(op)) {
				findAllFoodsOp(req, resp);
			} else if ("findAllSelectedFoods".equals(op)) {
				findAllSelectedFoodsOp(req, resp);
			} else if ("details".equals(op)) {
				detailsOp(req, resp);
			} else if ("addCart".equals(op)) {
				addCartOp(req, resp);
			} else if ("clearCart".equals(op)) {
				clearCartOp(req, resp);
			} else if ("delCartItem".equals(op)) {
				delCartItem(req, resp);
			} else if("showCart".equals(op)){
				showCartOp( req,resp);
			}else if ("changeCount".equals(op)) {
				changeCountOp(req, resp);
			} else if( "redis".equals(op)){
				redisOp( req,resp);
			}else {
				resp.sendRedirect(ERROR_404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendRedirect(ERROR_500);
		}
	}
	
	private void redisOp(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String ip=req.getRemoteAddr();//
		super.writeJson(resp,resfoodBiz.getRedis(ip));
	}

	private void showCartOp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/shopCart.jsp").forward(req, resp);;
	}

	private void changeCountOp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int fid = Integer.parseInt(req.getParameter("fid"));
		int number = Integer.parseInt(req.getParameter("number"));
		Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute(SESSIONCART);
		CartItem ci = cart.get(fid);
		ci.setCount(ci.getCount() + number);
		cart.put(fid, ci);
		if(  ci.getCount()<=0){
			cart.remove(fid);
		}
		
		session.setAttribute(SESSIONCART, cart);
		// 统计总价
		double total = 0.0;
		for (Map.Entry<Integer, CartItem> entry : cart.entrySet()) {
			total += entry.getValue().getCount() * entry.getValue().getResfood().getRealprice();
		}
		session.setAttribute(SESSIONTOTAL, total);
		// session.setAttribute( "sessionCart" , cart);

		req.getRequestDispatcher("/WEB-INF/pages/shopCart.jsp").forward(req, resp);

	}

	private void delCartItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int fid = Integer.parseInt(req.getParameter("fid"));
		Map<Integer, CartItem> cart = new HashMap<Integer, CartItem>();
		if (session.getAttribute(SESSIONCART) != null) {
			cart = (Map<Integer, CartItem>) session.getAttribute(SESSIONCART);
		}
		if (cart.containsKey(fid)) {
			cart.remove(fid);
		}
		session.setAttribute(SESSIONCART, cart);
		// 统计总价
		double total = 0.0;
		for (Map.Entry<Integer, CartItem> entry : cart.entrySet()) {
			total += entry.getValue().getCount() * entry.getValue().getResfood().getRealprice();
		}
		session.setAttribute(SESSIONTOTAL, total);
		// session.setAttribute( "sessionCart" , cart);

		req.getRequestDispatcher("/WEB-INF/pages/shopCart.jsp").forward(req, resp);

	}

	private void clearCartOp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		session.removeAttribute(SESSIONCART);
		session.setAttribute(SESSIONTOTAL, 0.0);
		req.getRequestDispatcher("/WEB-INF/pages/shopCart.jsp").forward(req, resp);
	}

	private void addCartOp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 取 fid
		int fid = Integer.parseInt(req.getParameter("fid"));
		Resfood resfood = null;
		// 2. 取出 JsonModel jsonModel=(JsonModel)
		// session.getAttribute(JSONMODEL);
		JsonModel jsonModel = (JsonModel) session.getAttribute(JSONMODEL);
		if (jsonModel != null && jsonModel.getRows() != null) {
			List<Resfood> list = jsonModel.getRows();
			for (Resfood rf : list) {
				if (rf.getFid() == fid) {
					resfood = rf;
					break;
				}
			}
		}
		// 3.查出这个菜
		// 4. 从session取购物车
		Map<Integer, CartItem> cart = new HashMap<Integer, CartItem>();
		if (session.getAttribute(SESSIONCART) != null) {
			cart = (Map<Integer, CartItem>) session.getAttribute(SESSIONCART);
		}
		// 5. 判断购物车是否有这个fid
		CartItem ci = null;
		if (!cart.containsKey(fid)) {
			ci = new CartItem();
			ci.setResfood(resfood);
			ci.setCount(1);
		} else {
			ci = cart.get(fid);
			ci.setCount(ci.getCount() + 1);
		}
		cart.put(fid, ci);
		// 6. 没有, 则创建一个新的CartItem
		// 有,则取出这个cartItem,将数量加一

		// 7. 将这个购物车又存到session中
		session.setAttribute(SESSIONCART, cart);

		// 统计总价
		double total = 0.0;
		for (Map.Entry<Integer, CartItem> entry : cart.entrySet()) {
			total += entry.getValue().getCount() * entry.getValue().getResfood().getRealprice();
		}
		session.setAttribute(SESSIONTOTAL, total);
		// session.setAttribute( "sessionCart" , cart);

		req.getRequestDispatcher("/WEB-INF/pages/shopCart.jsp").forward(req, resp);
	}

	private void detailsOp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fid = req.getParameter("fid");
		JsonModel jsonModel = (JsonModel) session.getAttribute(JSONMODEL);
		if (jsonModel != null && jsonModel.getRows() != null) {
			List<Resfood> list = jsonModel.getRows();
			for (Resfood rf : list) {
				if (rf.getFid() == Integer.parseInt(fid)) {
					req.setAttribute("resfood", rf);
					break;
				}
			}
		}
		req.getRequestDispatcher("/WEB-INF/pages/details.jsp").forward(req, resp);
	}

	private void findAllSelectedFoodsOp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 从application中取出所有的菜
		List<Resfood> list = (List<Resfood>) application.getAttribute(YcConstants.RESFOODLIST);
		List<Resfood> result = new ArrayList<Resfood>();
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			HashSet<String> hs = new HashSet<String>();
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals(YcConstants.BROWSEDFOOD)) {
					String cookiesid = cookie.getValue();
					String[] fids = cookiesid.split(",");
					for (String s : fids) {
						hs.add(s);
					}
				}
			}
			for (String t : hs) {
				for (Resfood rf : list) {
					if (rf.getFid() == Integer.parseInt(t)) {
						result.add(rf);
					}
				}
			}

		}
		JsonModel jm = new JsonModel();
		jm.setCode(1);
		jm.setObj(result);
		writeJson(resp, jm);
	}

	/**
	 * 分页查找菜品
	 * 
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void findAllFoodsOp(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		int pages = 1;
		int pageSize = 10;
		if (req.getParameter("pages") != null) {
			pages = Integer.parseInt(req.getParameter("pages"));
		}
		if (req.getParameter("pageSize") != null) {
			pageSize = Integer.parseInt(req.getParameter("pageSize"));
		}
		Resfood resfood = new Resfood();
		resfood.setPages(pages);
		resfood.setPageSize(pageSize);
		JsonModel jsonModel = resfoodBiz.findResfoodByPage(resfood);
		req.setAttribute(JSONMODEL, jsonModel);

		session.setAttribute(JSONMODEL, jsonModel);

		req.getRequestDispatcher("/WEB-INF/pages/show.jsp").forward(req, resp);
	}

}
