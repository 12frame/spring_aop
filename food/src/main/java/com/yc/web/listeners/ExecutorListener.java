package com.yc.web.listeners;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ExecutorListener implements ServletContextListener {

	private ExecutorService executor; //线程池

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().log("创建连接池");
		executor= Executors.newFixedThreadPool(30);
		sce.getServletContext().setAttribute("executor", executor);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sce.getServletContext().log("关闭连接池");
		executor.shutdown();
	}
	
	
}
