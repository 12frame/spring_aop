package com.ll.bean;

import java.io.Serializable;

public class Account implements Serializable {

	private static final long serialVersionUID = 8053062610510563594L;
	
	private String accountid;
	private double balance;

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
