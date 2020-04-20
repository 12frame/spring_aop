package com.yc.web.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.yc.bean.CityInfo;
import com.yc.bean.IpInfo;
import com.yc.bean.JsonModel;
import com.yc.biz.CityInfoBiz;
import com.yc.utils.NetUtil;
import com.yc.utils.WebXmlUtils;

import redis.clients.jedis.Jedis;

@WebServlet("/weather.action")
public class WeatherServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	class Result{
		String addressInfo;
		String weatherInfo;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		JsonModel jm=new JsonModel();
		jm.setCode(1);
		try {
			Jedis jedis = new Jedis();
			//TODO：如果这个网站取不到ip地址，方案：1.换另一个网站 2.用java访问
			String ip = NetUtil.getV4IP();
			IpInfo info = WebXmlUtils.getCountryCityByIp(ip);
			String addressInfo = "weather_"+info.ip + "_" + info.provinceAndCity;
			Result r=new Result();
			r.addressInfo=addressInfo;
			Set<String> keys = jedis.keys("weather_"+info.ip + "_*");
			if (!keys.isEmpty() && keys.size() > 0) {
				String[] strs= new String[keys.size()];
				int i=0;
				for (String key : keys){
					strs[i]= key;
					i++;
				}
				r.addressInfo= strs[0];
				r.weatherInfo=jedis.get(strs[0]);
				//定时器，每天定时删除天气预报
			} else {

				CityInfo cityinfo = CityInfoBiz.getCityInfo(info.provinceAndCity);
				//System.out.println("城市信息:\n" + cityinfo);
				List<String> list = WebXmlUtils.getWeather(cityinfo.getTid());
//				for (String s : list) {
//					System.out.println("==" + s);
//				}
				if (list != null && list.size() > 1) {
					String weatherinfo = list.get(4);
					r.weatherInfo=weatherinfo;
					jedis.set(addressInfo, list.get(4));
				}
			}
			jm.setObj( r  );
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.writeJson(response, jm);
	}

}
