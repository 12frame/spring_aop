package com.yc.web.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static com.yc.utils.YcConstants.LOGINUSER;
import static com.yc.utils.YcConstants.LOGINPAGE;

/**
 * 访问  /resuser/   路径下的任何资源，都要进行权限检测..
 *
 */
@WebFilter("/resuser/*")
public class Filter1_ResuserRightFilter implements Filter {
	
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//1.取出session
		//2. 从session中取出   登录的用户   cust，判断cust是否存在，如果不存在，则表明还没有登录
		//3.           没有登录，则到登录页面
		//4.   登录了，则向后
		HttpServletRequest req=(HttpServletRequest)request;
		HttpSession session=req.getSession();
		if( session.getAttribute(  LOGINUSER   )==null){
			request.getRequestDispatcher( LOGINPAGE  ).forward(req, response);
		}else{
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
