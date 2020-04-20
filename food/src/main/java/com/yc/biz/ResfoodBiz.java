package com.yc.biz;

import java.util.List;
import java.util.Set;

import com.yc.bean.JsonModel;
import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;

public interface ResfoodBiz {

	public List<Resfood> findResfood(Resfood resfood) throws Exception;

	public Integer findResfoodCount(Resfood resfood) throws Exception;

	public JsonModel<Resfood> findResfoodByPage(Resfood resfood) throws Exception;

	/**
	 * 根据ip从redis中取出最近访问的五条记录<br />
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public Set<String> getRedis(String ip) throws Exception;
	/**
	 * 存浏览记录到redis中. <br />
	 * @param ip
	 * @param fid
	 * @param resfood
	 * @throws Exception
	 */
	public void setRedis(String ip, String fid, Resfood resfood) throws Exception;
	
}
