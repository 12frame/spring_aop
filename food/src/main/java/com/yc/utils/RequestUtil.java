package com.yc.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest的帮助类.<br />
 * 功能: 将request中的参数取出，包装进一个对象中返回.
 * @author 张影
 * @param <T>
 */
public class RequestUtil<T> {
	
	/**
	 * 从request中取出参数，这些参数名与  cls这个反射对象中属性名是一样的，利用这一点采用反射机制，将 request中的参数值取出，
	 * 存到   一个T对象
	 * @param request：要从中取参数的请求
	 * @param cls  :   要包装的对象的反射
	 * @return 
	 * @throws UnsupportedEncodingException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static <T> T parseRequest(   HttpServletRequest request, Class<T> cls) throws UnsupportedEncodingException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//1. 解决编码
		request.setCharacterEncoding("utf-8");
		//2. 先实例化一个T
		T t=cls.newInstance();  //   new Person();
		//3. 取出request中所有的参数名      , 循环参数名
		Map<String,String[]> map=request.getParameterMap();
		// 取出  cls中所有的set方法
		List<Method> setMethods=getAllSetMethods( cls);
		for(   Map.Entry<String, String[]> entry: map.entrySet()){
			//	       4. 拼接方法名      "set"+参数名   ->  方法名
			String key=entry.getKey();
			String setMethodName="set"+key;
			String[] values=entry.getValue();
			String va="";
			if( values!=null&&values.length>0){
				va=values[0];
			}
			//              5. 取出  cls中所有的   set方法.
			//                       6. 从request中取出各参数的值，激活  cls 中的set方法.
			if(   va==null|| "".equals(va)){
				continue;
			}
			for(   Method m: setMethods){
				if(   setMethodName.equalsIgnoreCase(  m.getName() )){
					//判断m这个set方法中的参数的类型
					Class parameterType=m.getParameterTypes()[0];
					if(  "int".equals( parameterType.getName() )  ||   "java.lang.Integer".equals(parameterType.getName()) ){
						int v=Integer.parseInt(   va );
						m.invoke(t, v); 
					}else if(   "float".equals( parameterType.getName() )  ||   "java.lang.Float".equals(parameterType.getName()) ){
						float v=Float.parseFloat(   va );
						m.invoke(t, v);
					}else if(  "double".equals( parameterType.getName() )  ||   "java.lang.Double".equals(parameterType.getName())){
						double v=Double.parseDouble(   va );
						m.invoke(t, v);
					}else {
						m.invoke(t, va);
					}
					break;
				}
			}
		}
		//返回T
		return t;
	}

	private static List<Method> getAllSetMethods(Class cls) {
		List<Method> setMethods=new ArrayList<Method>();
		Method [] ms=cls.getMethods();
		for( Method m: ms){
			if( m.getName().startsWith("set")){
				setMethods.add(    m );
			}
		}
		return setMethods;
	}
}
