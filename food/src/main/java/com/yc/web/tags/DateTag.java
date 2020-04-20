package com.yc.web.tags;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class DateTag extends TagSupport {
	private String format="yyyy年MM月dd日 HH:mm:ss";

	@Override
	public int doStartTag() throws JspException {
		Date d=new Date();
		DateFormat df=new SimpleDateFormat(   format );
		JspWriter out=super.pageContext.getOut();
		try {
			
			out.println(    df.format( d ) );
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.SKIP_BODY;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	

}
