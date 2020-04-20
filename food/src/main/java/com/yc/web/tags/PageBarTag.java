package com.yc.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class PageBarTag extends TagSupport {

	private String action;
	private Integer pages;
	private Integer pagesize;
	private Integer total;
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out=super.pageContext.getOut();
		
		int totalpages=total%pagesize==0?total/pagesize:total/pagesize+1;
		int prepage=pages;
		if( pages>1){
			prepage=pages-1;
		}
		int nextpage=pages;
		if( nextpage<totalpages){
			nextpage=pages+1;
		}
		
		StringBuffer sb=new StringBuffer();
		sb.append("第"+pages+"页");
		sb.append("/共"+totalpages+"页   记录总数"+total+"条/每页"+pagesize+"条");
		sb.append("<a href=\""+  action+"&pages=1&pagesize="+pagesize+"\">第一页</a>");
		sb.append("<a href=\""+  action+"&pages="+prepage+"&pagesize="+pagesize+"\">上一页</a>");
		sb.append("<a href=\""+  action+"&pages="+nextpage+"&pagesize="+pagesize+"\">下一页</a>");
		sb.append("<a href=\""+  action+"&pages="+totalpages+"&pagesize="+pagesize+"\">最后一页</a>");
		 
		try {
			out.println(   sb.toString() );
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return super.EVAL_BODY_INCLUDE;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
