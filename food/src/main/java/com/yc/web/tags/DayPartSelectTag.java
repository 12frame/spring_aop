package com.yc.web.tags;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class DayPartSelectTag extends TagSupport {
	private String id;
	private String className;
	private String name;
	private String clickEvent;

	@Override
	public int doStartTag() throws JspException {
		Calendar c=Calendar.getInstance();
		int hour=c.get(Calendar.HOUR_OF_DAY);
		
		JspWriter out=super.pageContext.getOut();
		StringBuffer sb=new StringBuffer();
		sb.append("<select ");
		if( id!=null&&!"".equals(id)){
			sb.append(" id=\""+id+"\" ");
		}
		if( className!=null&& !"".equals(className)){
			sb.append(" class=\""+className+"\" ");
		}
		if(  name!=null&&!"".equals(name)){
			sb.append(" name=\""+name+"\" ");
		}
		if( clickEvent!=null&& !"".equals(clickEvent)){
			sb.append(" onClick=\""+clickEvent+"\" ");
		}
		sb.append(">");
		
		sb.append("<option value=\"上午\" ");
		if( hour<12){
			sb.append(" selected ");
		}
		sb.append(" >上午</option>");
		
		sb.append("<option value=\"下午\" ");
		if( hour>=12&&hour<18){
			sb.append(" selected ");
		}
		sb.append(" >下午</option>");
		
		sb.append("<option value=\"晚上\" ");
		if( hour>=18&&hour<24){
			sb.append(" selected ");
		}
		sb.append(" >晚上</option>");
		
		sb.append("</select>");
		
		try {
			out.println(   sb.toString() );
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return super.EVAL_BODY_INCLUDE;
	}
	
	

	public void setId(String id) {
		this.id = id;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setClickEvent(String clickEvent) {
		this.clickEvent = clickEvent;
	}

	
	
	
}
