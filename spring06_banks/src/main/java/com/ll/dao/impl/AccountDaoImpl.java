package com.ll.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ll.bean.Account;
import com.yc.dao.AccountDao;

@Repository
public class AccountDaoImpl implements AccountDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void addAccount(Account account) {
		this.jdbcTemplate.update("insert into account values( ?,? )", account.getAccountid(), account.getBalance());
	}

	@Override
	public void updateAccount(Account account) {
		this.jdbcTemplate.update("update account set balance=? where accountid=?",account.getBalance(), account.getAccountid());
	}

	@Override
	public Account findAccount(String accountid) {
		Account actor = this.jdbcTemplate.queryForObject(
		        "select * from account where accountid = ?",
		        (resultSet, rowNum) -> {
		        	Account a = new Account();
		        	a.setAccountid(    resultSet.getString(1));
		        	a.setBalance(   resultSet.getDouble(2) );
		            return a;
		        },
		        accountid);
		return actor;
	}

}
