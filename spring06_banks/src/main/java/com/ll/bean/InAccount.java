package com.ll.bean;

import java.io.Serializable;

public class InAccount implements Serializable{

	private static final long serialVersionUID = -6573735686659801624L;
	
	private String accountid;
	private double inbalance;

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}

	public double getInbalance() {
		return inbalance;
	}

	public void setInbalance(double inbalance) {
		this.inbalance = inbalance;
	}
}
