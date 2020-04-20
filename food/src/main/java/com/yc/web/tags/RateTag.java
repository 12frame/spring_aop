package com.yc.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class RateTag extends TagSupport {
	
	private Long start;
	private Long end;
	@Override
	public int doEndTag() throws JspException {
		end=System.currentTimeMillis();
		JspWriter out=super.pageContext.getOut();
		try {
			
			out.println(  "程序耗时"+(end-start)+"毫秒<br/>" );
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
	@Override
	public int doStartTag() throws JspException {
		start=System.currentTimeMillis();
		return super.EVAL_BODY_INCLUDE;
	}
	
	
	
	
}
