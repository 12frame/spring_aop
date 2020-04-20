package com.yc.bean;

import java.io.Serializable;

public class IpInfo implements Serializable {
	private static final long serialVersionUID = 2136315954901772435L;
	public String ip;
	public String provinceAndCity;
	public String company;

	@Override
	public String toString() {
		return "IpInfo [ip=" + ip + ", provinceAndCity=" + provinceAndCity + ", company=" + company + "]";
	}

}
