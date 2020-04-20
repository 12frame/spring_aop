package com.yc.utils;

public class YcConstants {
	/**
	 * 404页面地址
	 */
	public final static String ERROR_404="common/404.html";
	/**
	 * 500页面地址
	 */
	public final static String ERROR_500="common/500.html";
	
	
	/**
	 * 首页中显示菜品列表的model
	 */
	public final static String JSONMODEL="jsonModel";
	
	/**
	 * session中的购物车
	 */
	public final static String SESSIONCART="sessionCart";
	
	/**
	 * session中的购物车商品总价钱
	 */
	public final static String SESSIONTOTAL="sessionTotal";
	
	/**
	 * session中存登录用户信息
	 */
	public final static String LOGINUSER="resuser";
	
	
	/**
	 * 登录页面地址
	 */
	public final static String LOGINPAGE="/WEB-INF/pages/login.jsp";
	
	
	
	
	
	
	
	
	
	
	
	
	
	public final static String RESFOODLIST="resfoodlist";
	
	
	//购物车的session的键名
	public final static String CART="cart";
	
	//查看单个商品详情的地址
	public final static String FINDFOOD="findFood";
	public final static String ORDERFOOD="order";
	//cookie中浏览过的商品编号
	public final static String BROWSEDFOOD="browsedfood";
	
	
	//上一次访问的页面地址
	public final static String LASTVISITEDADDR="lastvisitedaddr";
	
	//首页地址
	public final static String HOMEPAGE="index.jsp";
	
	//下订成功后的页面 -> 真实的项目，这里是支付的页面...
	public final static String ORDERSUCCESSADDR="index.jsp";
}
