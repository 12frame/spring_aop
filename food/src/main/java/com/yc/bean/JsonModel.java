package com.yc.bean;

import java.io.Serializable;
import java.util.List;

public class JsonModel<T> implements Serializable {

	private static final long serialVersionUID = 9106902882602433353L;

	private Integer code;
	private T obj;
	private String msg;
	
	
	/**总记录数*/
	private Integer total; 
	 /** 当前为第几页*/
	private Integer pages;
	/**每页xx条 */
	private Integer pageSize; 
	/** 记录集合*/
	private List<T> rows; 
	
	

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
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

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
