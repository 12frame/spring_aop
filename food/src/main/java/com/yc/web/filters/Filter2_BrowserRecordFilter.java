package com.yc.web.filters;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yc.bean.JsonModel;
import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBiz;
import com.yc.biz.impl.ResfoodBizImpl;

import static com.yc.utils.YcConstants.LOGINUSER;
import static com.yc.utils.YcConstants.JSONMODEL;
import static com.yc.utils.YcConstants.LOGINPAGE;

@WebFilter("/resfood.action")
public class Filter2_BrowserRecordFilter implements Filter {
	
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//
		HttpServletRequest req=(HttpServletRequest)request;
		String op=req.getParameter("op");
		//增强
		if(  "addCart".equals(op) ||   "details".equals(op)){
			String fid=req.getParameter("fid");  //菜的编号
			String ip=   req.getRemoteAddr();   //取出ip
			HttpSession session=req.getSession();
			JsonModel jsonModel = (JsonModel) session.getAttribute(JSONMODEL);
			Resfood resfood=null;
			if (jsonModel != null && jsonModel.getRows() != null) {
				List<Resfood> list = jsonModel.getRows();
				for (Resfood rf : list) {
					if (rf.getFid() == Integer.parseInt(fid)) {
						resfood=rf;
						break;
					}
				}
			}
			ResfoodBiz resfoodBiz=new ResfoodBizImpl();
			try {
				resfoodBiz.setRedis(ip, fid, resfood);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
