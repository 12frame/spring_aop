package com.yc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.yc.bean.CityInfo;
import com.yc.bean.IpInfo;
import com.yc.biz.CityInfoBiz;

/**
 * 读取 webxml上的服务 常量设置分三块: 1. 网站地址 2. 服务地址 3. 服务中的各方法地址 在使用时，要注意查看一下endpoint示例，
 * 重点是还需要什么参数
 * http://ws.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx/
 * getCountryCityByIp?theIpAddress=59.51.114.211
 */
public class WebXmlUtils {
	// webxml最新网址
	public static String SERVICES_HOST = "http://ws.webxml.com.cn/WebServices/";

	// IP地址所在地查询服务地址
	public static String IP_ADDRESS = SERVICES_HOST + "IpAddressSearchWebService.asmx/";
	///////////////////////////// IP地址查询各方法地址
	public static String IPADDRESSSEARCH_URL = IP_ADDRESS + "getCountryCityByIp?theIpAddress=";

	// 天气预报地址服务地址
	public static String WEATHER_ADDRESS = SERVICES_HOST + "WeatherWS.asmx/";
	// 天气查询地址
	public static String WEATHER_QUERY_URL = WEATHER_ADDRESS + "getWeather?theUserID=&theCityCode=";

	////////////////////////////// 天气预报各方法地址
	// 查询天气国家
	public static String WEATHER_COUNTRY_URL = WEATHER_ADDRESS + "getRegionCountry";
	// 获得中国省份、直辖市、地区和与之对应的ID
	public static String WEATHER_PRVO_URL = WEATHER_ADDRESS + "getRegionProvince";
	// 查询城市
	public static String WEATHER_CITY_URL = WEATHER_ADDRESS + "getSupportCityString?theUserID=&theRegionCode=";

	/**
	 * 根据ip获取省市
	 * 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static IpInfo getCountryCityByIp(String ip) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(IPADDRESSSEARCH_URL + ip);
		IpInfo ipInfo = new IpInfo();
		ipInfo.ip = ip;
		NodeList nodeList = doc.getElementsByTagName("string");
		String result = nodeList.item(1).getChildNodes().item(0).getNodeValue();
		String[] strs = result.split(" ");
		if (strs.length > 1) {
			ipInfo.provinceAndCity = strs[0];
			ipInfo.company = strs[1];
		}
		return ipInfo;
	}

	// 获取指定城市的天气
	public static List<String> getWeather(int cityCode) {
		List<String> weatherList = new ArrayList<String>(); // list存结果
		Document document = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.parse(WEATHER_QUERY_URL + cityCode);
			NodeList nl = document.getElementsByTagName("string");
			int len = nl.getLength();
			for (int i = 0; i < len; i++) {
				Node n = nl.item(i);
				String weather = n.getFirstChild().getNodeValue();
				weatherList.add(weather);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weatherList;
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		String ip = NetUtil.getV4IP();
		IpInfo info = getCountryCityByIp(ip);
		System.out.println(info);
		//
		// //根据城市名找编号... excel找.
		// CityInfo cityinfo=CityInfoBiz.getCityInfo( info.provinceAndCity );
		// System.out.println( "城市信息:\n"+ cityinfo);

//		List<String> list = getWeather(206);
//		for (String s : list) {
//			System.out.println("==" + s);
//		}
		// IoUtil.writeObject(info, "info.dat");
		// info=(IpInfo) IoUtil.readObject("info.dat");
		// System.out.println( info );
	}

	/**
	 * 获取省份
	 * 
	 * @return
	 */
	public static List<String> getPrvoCode(String url) {
		List<String> provList = new ArrayList<String>();

		// 创建一个文档构建工厂对象，解析器的对象（DOM）
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// 通过工厂生产DocumentBuilder对象
		// 通过builder创建一个树，document->文档树
		Document doc = null;

		// 指定由此代码生成的解析器将提供对 XML 名称空间的支持。
		factory.setNamespaceAware(true);

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			InputStream inputStream = NetUtil.getSoapInputStream(url, SERVICES_HOST);

			// 将给定 InputStream 的内容解析为一个 XML 文档，并且返回一个新的 DOM Document 对象。
			doc = builder.parse(inputStream);

			NodeList nl = doc.getElementsByTagName("string");
			int len = nl.getLength();
			for (int i = 0; i < len; i++) {
				Node n = nl.item(i);
				String value = n.getFirstChild().getNodeValue();
				provList.add(value);
			}
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return provList;
	}

	public static List<String> getCityCode(String code) {
		List<String> cityList = new ArrayList<String>();

		// 创建一个文档构建工厂对象，解析器的对象（DOM）
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// 通过工厂生产DocumentBuilder对象
		// 通过builder创建一个树，document->文档树
		Document doc = null;

		// 指定由此代码生成的解析器将提供对 XML 名称空间的支持。
		factory.setNamespaceAware(true);

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			InputStream inputStream = NetUtil.getSoapInputStream(WEATHER_CITY_URL + code, SERVICES_HOST);

			// 将给定 InputStream 的内容解析为一个 XML 文档，并且返回一个新的 DOM Document 对象。
			doc = builder.parse(inputStream);

			NodeList nl = doc.getElementsByTagName("string");
			int len = nl.getLength();
			for (int i = 0; i < len; i++) {
				Node n = nl.item(i);
				String value = n.getFirstChild().getNodeValue();
				cityList.add(value);
			}
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityList;
	}

}