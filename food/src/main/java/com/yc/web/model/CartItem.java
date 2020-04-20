package com.yc.web.model;

import java.io.Serializable;

import com.yc.bean.Resfood;

public class CartItem implements Serializable {
	private static final long serialVersionUID = -7267043439881456032L;

	private Resfood resfood;
	private Integer count;

	public Double getSmallCount() {
		return resfood.getRealprice() * count;
	}

	public Resfood getResfood() {
		return resfood;
	}

	public void setResfood(Resfood resfood) {
		this.resfood = resfood;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
