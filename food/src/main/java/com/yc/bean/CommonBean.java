package com.yc.bean;

import java.io.Serializable;

public class CommonBean implements Serializable {

	private static final long serialVersionUID = -1220734587096028186L;

	private Integer pages;
	private Integer pageSize;
	private String orderBy;     //   select * from xxx oder by 列名      desc/asc;
	private String order;
	
	

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "CommonBean [pages=" + pages + ", pageSize=" + pageSize + "]";
	}

	
}
