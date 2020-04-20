package com.yc.biz.impl;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;
import com.yc.bean.Resuser;
import com.yc.biz.ResroderBiz;
import com.yc.dao.DBHelper;
import com.yc.web.model.CartItem;
import com.yc.bean.JsonModel;

public class ResorderBizImpl implements ResroderBiz {
	private DBHelper db = new DBHelper();

	@Override
	public List<Resorder> findResorder(Resorder resorder) throws Exception {
		String sql = "select  * from resorder  where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if (resorder != null) {
			if (resorder.getUserid() != null && !"".equals(resorder.getUserid())) {
				sql += " and userid=? ";
				params.add(resorder.getUserid());
			}
			if (resorder.getStatus() != null && !"".equals(resorder.getStatus())) {
				sql += " and status =? ";
				params.add(resorder.getStatus());
			}
		}
		if (resorder != null && resorder.getOrderBy() != null) {
			sql += " order by " + resorder.getOrderBy() + " " + resorder.getOrder() + " ";
		}
		if (resorder != null && resorder.getPages() != null) {
			int start = (resorder.getPages() - 1) * resorder.getPageSize();
			sql += " limit " + start + "," + resorder.getPageSize();
		}
		return db.select(sql, params, Resorder.class);
	}

	@Override
	public Integer findCount(Resorder resorder) throws FileNotFoundException, SQLException {
		String sql = "select count(*) from resorder  where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if (resorder != null) {
			if (resorder.getUserid() != null && !"".equals(resorder.getUserid())) {
				sql += " and userid=? ";
				params.add(resorder.getUserid());
			}
			if (resorder.getStatus() != null && !"".equals(resorder.getStatus())) {
				sql += " and status =? ";
				params.add(resorder.getStatus());
			}
		}
		int count = db.selectCount(sql, params).intValue();
		return count;
	}

	@Override
	public JsonModel<Resorder> find(Resorder resorder) throws FileNotFoundException, SQLException, Exception {
		int total = findCount(resorder);
		List<Resorder> list = findResorder(resorder);

		JsonModel<Resorder> jsonModel = new JsonModel<Resorder>();
		if (resorder != null && resorder.getPages() != null) {
			jsonModel.setPages(resorder.getPages());
			jsonModel.setPageSize(resorder.getPageSize());
		}
		jsonModel.setRows(list);
		jsonModel.setTotal(total); // 这个setTotal方法必须在最后运行...
		return jsonModel;
	}

	@Override
	public void makeOrder(Resorder resorder, Map<Integer, CartItem> cart, Resuser resuser)
			throws FileNotFoundException, SQLException {

		String sql = "insert into resorder(userid,address,tel,ordertime,deliverytime,ps,status) values(?,?,?,now(),date_add(now(), interval 1 hour),?,0)";

		Connection con = db.getCon();
		try {
			// 事务处理
			con.setAutoCommit(false); // 关闭隐式事务( 一条sql语句自动提交一次)
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, resuser.getUserid() + "");
			pstmt.setString(2, resorder.getAddress());
			pstmt.setString(3, resorder.getTel());
			pstmt.setString(4, resorder.getPs());
			pstmt.executeUpdate();

			sql = "select max( roid ) as roid from resorder ";
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			int roid = 0;
			if (rs.next()) {
				roid = rs.getInt(1);
			}

			for (Map.Entry<Integer, CartItem> entry : cart.entrySet()) {
				int fid = entry.getKey();
				CartItem ci = entry.getValue();
				Resfood resfood = ci.getResfood();
				int count = ci.getCount();
				Double smallCount = ci.getSmallCount();
				sql = "insert into resorderitem(roid,fid,dealprice,num) values(?,?,?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, roid + "");
				pstmt.setString(2, fid + "");
				pstmt.setString(3, resfood.getRealprice() + "");
				pstmt.setString(4, count + "");
				pstmt.executeUpdate();
			}

			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			throw e;
		} finally {
			if (con != null) {
				con.setAutoCommit(true);
				con.close();
			}
		}

	}

	@Override
	public List<Resorderitem> findOrderItem(Resorder resorder) throws Exception {
		String sql = "select * from resorderitem where roid=" + resorder.getRoid();
		List<Resorderitem> list = db.select(sql, null, Resorderitem.class);
		return list;
	}

}
