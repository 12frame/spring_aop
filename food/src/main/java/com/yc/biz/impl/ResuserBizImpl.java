package com.yc.biz.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.yc.bean.Resuser;
import com.yc.biz.ResuserBiz;
import com.yc.dao.DBHelper;
import com.yc.utils.Encrypt;

public class ResuserBizImpl implements ResuserBiz {
	private DBHelper db = new DBHelper();
	
	@Override
	public Resuser changePwd(Resuser user) throws Exception {
		String sql = "update resuser set pwd=? where userid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(Encrypt.md5AndSha(user.getPwd()));
		params.add(user.getUserid());
		db.update(sql, params);
		return user;
	}

	@Override
	public Resuser reg(Resuser user) throws Exception {
		if (user.getUsername() == null) {
			throw new Exception("註冊的用戶名不能為空");
		}
		if (user.getPwd() == null) {
			throw new Exception("註冊的密碼不能為空");
		}
		 if( ! user.getPwd().equals(user.getRepwd())){
			 throw new Exception("兩次輸入的密碼不相同");
		 }
		 if( null==user.getEmail()   ||    "".equals(user.getEmail())){
			 throw new Exception("电子邮箱不能为空");
		 }

		String sql = "insert into resuser(username,pwd,email) values( ?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(user.getUsername());
		params.add(Encrypt.md5AndSha(user.getPwd()));
		params.add(user.getEmail());
		db.update(sql, params);

		
		return user;
	}

	@Override
	public Resuser login(Resuser user) throws Exception {
		String sql = "select * from resuser where username=? and pwd=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(user.getUsername());
		params.add(Encrypt.md5AndSha(user.getPwd()));   //加密密码
		List<Resuser> list = db.select(sql, params, Resuser.class);
		if (list == null || list.size() <= 0) {
			return null;
		}
		return list.get(0);
	}

	

	@Override
	public Resuser isUserNameExist(String username) throws Exception {
		String sql = "select * from resuser where username=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(username);
		List<Resuser> list = db.select(sql, params, Resuser.class);
		if (list == null || list.size() <= 0) {
			return null;
		}
		return list.get(0);
	}

}
