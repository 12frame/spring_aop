package com.yc.biz;

import com.yc.bean.Resuser;

public interface ResuserBiz {
	
	/**
	 * 将user对象存到数据库，成功，返回true, 失败，返回false
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public Resuser reg(  Resuser user  ) throws Exception;
	
	
	public Resuser login(  Resuser user  ) throws Exception;
	
	
	public Resuser isUserNameExist(   String username)throws Exception;
	
	public Resuser changePwd(Resuser user) throws Exception;
}
