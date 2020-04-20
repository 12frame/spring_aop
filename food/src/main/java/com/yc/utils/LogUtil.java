package com.yc.utils;

import org.apache.log4j.Logger;

/**
 * 日志工具类
 *
 */
public class LogUtil {
	
	/**
	 * 日志记录器:   带级别的方法   debug()   info()   warn()  error()  fatal()
	 */
	public static Logger logger=Logger.getLogger(  LogUtil.class     );
	
	
	public static void error( Exception e ){
		if(  e!=null ){
			logger.error(  e.getMessage() );
			StackTraceElement[] stes=e.getStackTrace();
			for(  StackTraceElement ste: stes){
				logger.error(  "\t\t"+ ste.toString());
			}
		}
	}
	
	public static void debug( Exception e ){
		if(  e!=null ){
			logger.debug(  e.getMessage() );
			StackTraceElement[] stes=e.getStackTrace();
			for(  StackTraceElement ste: stes){
				logger.debug(  "\t\t"+ ste.toString());
			}
		}
	}
	
	public static void warn( Exception e ){
		if(  e!=null ){
			logger.warn(  e.getMessage() );
			StackTraceElement[] stes=e.getStackTrace();
			for(  StackTraceElement ste: stes){
				logger.warn(  "\t\t"+ ste.toString());
			}
		}
	}
	
	public static void info( Exception e ){
		if(  e!=null ){
			logger.info(  e.getMessage() );
			StackTraceElement[] stes=e.getStackTrace();
			for(  StackTraceElement ste: stes){
				logger.info(  "\t\t"+ ste.toString());
			}
		}
	}
	
	
	

}
