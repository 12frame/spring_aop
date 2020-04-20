package com.yc.biz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.yc.bean.CityInfo;

//城市信息的业务
public class CityInfoBiz {

	public static Map<String, List<CityInfo>> cityInfoMap = new HashMap<String, List<CityInfo>>();

	// 静态块
	static {
		try {
			cityInfoMap = getAllCityInfo();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据市名或地区名取该市的详情 湖南省衡阳市 湖南省衡阳市 香港特别行政区香港
	 * 
	 * @param addressInfo
	 * @return
	 */
	public static CityInfo getCityInfo(String addressInfo) {
		// 1.取出省，市信息
		String provinceName = null;
		String cityName = null;
		Pattern p = Pattern.compile("(.+)省(.+)市");
		Matcher m = p.matcher(addressInfo);
		if (m.find()) {
			provinceName = m.group(1); // 取第一个满足要求的值
			cityName = m.group(2);
			return findCityInfoByCityName(provinceName, cityName);
		}
		// TODO:解析...
//		p = Pattern.compile("(.+)区(.+)");
//		m = p.matcher(addressInfo);
//		if( m.find()){
//			provinceName = m.group(1); // 取第一个满足要求的值
//			cityName = m.group(2);
//			return findCityInfoByCityName(provinceName, cityName);
//		}
		return null;
	}

	public static void main(String[] args) {
		 System.out.println(cityInfoMap);
		// System.out.println( findCityInfoByCityName("湖南","衡阳") );
		//System.out.println(getCityInfo("湖南省衡阳市"));
		//System.out.println(getCityInfo("广西壮族自治区南宁市"));
	}

	/**
	 * 取所有的城市的信息,包装成 省 -> List<CityInfo> excel 解析...
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static Map<String, List<CityInfo>> getAllCityInfo() throws FileNotFoundException, IOException {
		List<CityInfo> list = getAllCity();
		for (CityInfo ci : list) {
			List<CityInfo> l = new ArrayList<CityInfo>();
			if (cityInfoMap.containsKey(ci.getZone())) {
				l = cityInfoMap.get(ci.getZone());
			} else {
				l = new ArrayList<CityInfo>();
			}
			if (ci.getArea().equals(ci.getCountryOrAreaName())) {
				l.add(ci);
			}
			cityInfoMap.put(ci.getZone(), l);
		}
		return cityInfoMap;
	}

	private static List<CityInfo> getAllCity() throws FileNotFoundException, IOException {
		// 项目的路径 \bin\AbountCity.xls
		//String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "AboutCity.xls";
		//TODO: 你可以活的
		//String path="C:\\tomcat\\apache-tomcat-7.0.47\\webapps\\yc3637web1\\WEB-INF\\classes\\AboutCity.xls";
		//POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(path));
		POIFSFileSystem fs = new POIFSFileSystem( CityInfoBiz.class.getClassLoader().getResourceAsStream("AboutCity.xls")  );
		// 得到Excel工作簿对象
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		// 得到Excel工作表对象
		HSSFSheet sheet = wb.getSheet("About_City_View");

		// 获取有效的行数
		int rowcount = sheet.getLastRowNum();
		List<CityInfo> list = new ArrayList<CityInfo>();
		for (int i = 1; i < rowcount; i++) {
			CityInfo ci = new CityInfo();
			// 得到Excel工作表的行
			HSSFRow row = sheet.getRow(i);
			// 单元格数
			int cellNum = row.getLastCellNum();
			for (int j = 0; j < cellNum; j++) {
				// 得到Excel工作表指定行的单元格
				HSSFCell cell = row.getCell((short) j);
				if (cell != null) {
					if (j == 0) {
						ci.setTid((int) cell.getNumericCellValue());
					} else if (j == 1 && cell.getStringCellValue() != null) {
						ci.setZone(cell.getStringCellValue());
					} else if (j == 2 && cell.getStringCellValue() != null) {
						ci.setArea(cell.getStringCellValue());
					} else if (j == 3 && cell.getStringCellValue() != null) {
						ci.setAreaEn(cell.getStringCellValue());
					} else if (j == 4 && cell.getStringCellValue() != null) {
						ci.setCountryOrAreaName(cell.getStringCellValue());
					} else if (j == 5 && cell.getStringCellValue() != null) {
						ci.setCountryName(cell.getStringCellValue());
					} else if (j == 6 && cell.getStringCellValue() != null) {
						ci.setCountryNameEn(cell.getStringCellValue());
					} else if (j == 7 && cell.getStringCellValue() != null) {
						ci.setAboutCity(cell.getStringCellValue());
					} else if (j == 8 && cell.getStringCellValue() != null) {
						ci.setClimateBackground(cell.getStringCellValue());
					}
				}
			}
			list.add(ci);
		}
		return list;
	}

	/*
	 * 根据省名查这个省所有的市, 从cityInfoMap中查
	 */
	public static List<CityInfo> findCityListByProName(String provinceName) {
		return cityInfoMap.get(provinceName);
	}

	/**
	 * 根据市名得到市的信息 从cityInfoMap中查
	 */
	public static CityInfo findCityInfoByCityName(String provinceName, String cityName) {
		List<CityInfo> list = findCityListByProName(provinceName);
		if (list != null) {
			for (CityInfo ci : list) {
				if (ci.getCountryOrAreaName().equals(cityName)) {
					return ci;
				}
				if (ci.getZone().equals(cityName)) {
					return ci;
				}
			}
		}
		return null;
	}

}
