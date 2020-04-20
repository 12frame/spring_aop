package com.yc.bean;

import java.io.Serializable;

public class CityInfo implements Serializable {
	private static final long serialVersionUID = -5104799500485628580L;
	private Integer tid;
	private String zone = "";
	private String area = "";
	private String areaEn = "";
	private String countryOrAreaName = "";
	private String countryName = "";
	private String countryNameEn = "";
	private String aboutCity = "";
	private String climateBackground = "";

	public String getAreaEn() {
		return areaEn;
	}

	public void setAreaEn(String areaEn) {
		this.areaEn = areaEn;
	}

	public String getCountryOrAreaName() {
		return countryOrAreaName;
	}

	public void setCountryOrAreaName(String countryOrAreaName) {
		this.countryOrAreaName = countryOrAreaName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryNameEn() {
		return countryNameEn;
	}

	public void setCountryNameEn(String countryNameEn) {
		this.countryNameEn = countryNameEn;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAboutCity() {
		return aboutCity;
	}

	public void setAboutCity(String aboutCity) {
		this.aboutCity = aboutCity;
	}

	public String getClimateBackground() {
		return climateBackground;
	}

	public void setClimateBackground(String climateBackground) {
		this.climateBackground = climateBackground;
	}

	@Override
	public String toString() {
		return "CityInfo [tid=" + tid + ", zone=" + zone + ", area=" + area + ", countryOrAreaName=" + countryOrAreaName
				+ ", countryName=" + countryName + "]\n";
	}

}
