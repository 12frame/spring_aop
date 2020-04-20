package com.yc.biz;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;
import com.yc.bean.Resuser;
import com.yc.web.model.CartItem;
import com.yc.bean.JsonModel;

public interface ResroderBiz {
	/**
	 * 查询订单的方法: 
	 * 条件  resorder:
	 *      1. 根据下单人的编号   userid 
	 *      2. 默认排序： 根据  ordertime   降序排序
	 *      3. 查全部
	 *      4. 分页
	 * @throws Exception 
	 */
	public List<Resorder> findResorder(   Resorder resorder) throws Exception;
	
	public Integer   findCount( Resorder resorder) throws FileNotFoundException, SQLException;
	
	public JsonModel<Resorder> find(   Resorder resorder) throws FileNotFoundException, SQLException, Exception;
	
	
	/**
	 * 处理订单
	 * @param resorder   订单(   地址,电话，附言)
	 * @param cart   (所有的订单项)
	 * @param resuser   ( 用户  ->  id)
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 */
	public void makeOrder(   Resorder resorder, Map<Integer,CartItem> cart, Resuser resuser) throws FileNotFoundException, SQLException;
	
	
	/**
	 * 根据订单编号查询订单项
	 * @param resorder
	 * @return
	 * @throws Exception
	 */
	public List<Resorderitem> findOrderItem(  Resorder resorder)throws Exception;
}
