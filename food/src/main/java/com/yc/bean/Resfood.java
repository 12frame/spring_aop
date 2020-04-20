package com.yc.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class Resfood extends CommonBean implements Serializable {

	private static final long serialVersionUID = -1457731969423077569L;

	private int fid;
	private String fname;
	private double normprice;
	private double realprice;
	private String detail;
	private String fphoto;
	
	
	

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public double getNormprice() {
		return normprice;
	}

	public void setNormprice(double normprice) {
		this.normprice = normprice;
	}

	public double getRealprice() {
		return realprice;
	}

	public void setRealprice(double realprice) {
		this.realprice = realprice;
	}

	public String getDetail() {
		try {
			this.detail=java.net.URLDecoder.decode( this.detail,"utf-8" );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getFphoto() {
		return fphoto;
	}

	public void setFphoto(String fphoto) {
		this.fphoto = fphoto;
	}

	@Override
	public String toString() {
		return "Resfood [fid=" + fid + ", fname=" + fname + ", normprice=" + normprice + ", realprice=" + realprice
				+ ", detail=" + detail + ", fphoto=" + fphoto + "]";
	}

}
