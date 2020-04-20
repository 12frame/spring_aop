package com.yc.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.yc.bean.JsonModel;
import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;
import com.yc.biz.ResfoodBiz;
import com.yc.dao.DBHelper;

import redis.clients.jedis.Jedis;

public class ResfoodBizImpl implements ResfoodBiz {
	private DBHelper db = new DBHelper();

	@Override
	public List<Resfood> findResfood(Resfood resfood) throws Exception {
		String sql = " select * from resfood where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if (resfood != null) {
			// TODO: 以后加入查询的条件
		}
		if (resfood != null && resfood.getOrderBy() != null && resfood.getOrder() != null) {
			sql += " order by " + resfood.getOrderBy() + " " + resfood.getOrder(); // order
																					// by
																					// realprice
																					// desc
		}
		if (resfood != null && resfood.getPages() != null && resfood.getPageSize() != null) {
			int pages = resfood.getPages();
			int pageSize = resfood.getPageSize();
			int start = (pages - 1) * pageSize;
			sql += " limit ?,? ";
			params.add(start);
			params.add(pageSize);
		}
		return db.select(sql, params, Resfood.class);
	}

	@Override
	public Integer findResfoodCount(Resfood resfood) throws Exception {
		String sql = "select count(*) as num from resfood  where 1=1 ";
		List<Object> params = new ArrayList<Object>();
		if (resfood != null) {
			// TODO: 以后加入查询的条件
		}
		return db.selectCount(sql, params).intValue();
	}

	@Override
	public JsonModel<Resfood> findResfoodByPage(Resfood resfood) throws Exception {
		int total = findResfoodCount(resfood);
		List<Resfood> allResfoodList = findResfood(resfood);
		JsonModel<Resfood> jsonModel=new JsonModel<Resfood>();
		if(  resfood!=null){
			jsonModel.setPages(   resfood.getPages() );
			jsonModel.setPageSize(resfood.getPageSize());
		}
		jsonModel.setRows(allResfoodList);
		jsonModel.setTotal(total); // 这个setTotal方法必须在最后运行...
		return jsonModel;
	}
	
	public Set<String>  getRedis(String ip) throws Exception{
		Jedis jedis=new Jedis();
		Set<String> set=jedis.zrevrange(ip, 0,4);  //从大到小的排序
		return set;
	}

	@Override
	public void setRedis(String ip, String fid, Resfood resfood) throws Exception {
		Jedis jedis=new Jedis();
		double score=(double)new Date().getTime();
		//redis值的类型： string ,list, set, soated set, map
		// sorted set  排序set（排序，不重复）
		//    
		jedis.zadd(ip, score, fid+"_"+resfood.getFname()+"_images/"+resfood.getFphoto());
	}



}
