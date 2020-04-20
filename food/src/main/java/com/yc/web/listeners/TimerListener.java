package com.yc.web.listeners;

import java.util.Calendar;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
@WebListener
public class TimerListener implements ServletContextListener {
	
	private Timer timer= null;

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		timer= new Timer(true);
		sce.getServletContext().log("定时器已启动");
		Calendar c= Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 1);;//1:45 今晚的1:45跑一次
		c.set(Calendar.MINUTE, 45);
		timer.schedule(new ClaerWeatherInRedis(), c.getTime(), 24*69*60*3600);
		sce.getServletContext().log("已添加删除天气记录定时器任务");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		timer.cancel();
		sce.getServletContext().log("定时器销毁");
	}

	
}

class ClaerWeatherInRedis extends TimerTask{
	
	private Logger logger= Logger.getLogger(ClaerWeatherInRedis.class);

	@Override
	public void run() {
		Jedis jedis= new Jedis();
		Set<String> keys= jedis.keys("weather_*");
		if (keys.size() <= 0){
			logger.info("暂时没有要删除的redis天气记录");
			return ;
		}
		//将set转为字符串数组
		String [] strs= new String[keys.size()];
		int i= 0;
		for (String  key:keys){
			strs[i]= key;
			i++;
		}
		
//		Long result= jedis.del(strs);
//		logger.info("删除："+result+"条redis天气记录");
		jedis.close();
	}
	
}
