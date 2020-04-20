package com.yc.web.servlets;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ll.messages.MailInfo;
import com.ll.messages.multi.HandlerProducer;
import com.yc.bean.JsonModel;
import com.yc.bean.Resuser;
import com.yc.biz.ResuserBiz;
import com.yc.biz.impl.ResuserBizImpl;
import com.yc.dao.MyProperties;
import com.yc.utils.RequestUtil;

import static com.yc.utils.YcConstants.LOGINPAGE;
import static com.yc.utils.YcConstants.LOGINUSER;

@WebServlet(urlPatterns = { "/resuser.action", "/resuser/resuser.action" })
public class ResuserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private ResuserBiz resuserBiz = new ResuserBizImpl();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if ("login".equals(op)) {
				login(request, response);
			} else if ("logout".equals(op)) {
				logout(request, response);
			} else if ("reg".equals(op)) {
				reg(request, response);
			} else if ("changePwd".equals(op)) {
				changePwd(request, response);
			} else if ("toChangePwd".equals(op)) {
				toChangePwd(request, response);
			} else if ("isResuserLogin".equals(op)) {
				isResuserLogin(request, response);
			} else if ("toLogin".equals(op)) {
				toLoginOp(request, response);
			} else if ("toReg".equals(op)) {
				toRegOp(request, response);
			} else if ("isUsernameExist".equals(op)) {
				isUsernameExistOp(request, response);
			} else if ("sendMail".equals(op)) {
				sendMailOp(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendMailOp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Resuser user = (Resuser) session.getAttribute(LOGINUSER);
		StringBuilder builder = new StringBuilder();
		StringBuffer url = new StringBuffer();
		String contextPath = request.getContextPath(); // /yc3637foods
		InetAddress addr = InetAddress.getLocalHost();
		String rUrl = addr.getHostAddress();// 获得服务器地址 192.168.20.159
		url.append("http://" + rUrl); // http://服务器地址:
		url.append(":" + request.getServerPort());
		url.append(contextPath);
		url.append("/resuser.action?op=toChangePwd"); // http://192.168.20.159:8080/yc3637foods/resuser/resuser.action?op=toChangePwd
		Integer validacode = (int) ((Math.random() * 9 + 1) * 1000); // 验证码随机数
		request.getSession().setAttribute("validacode", validacode);
		  // 邮件正文  
        builder.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" </head><body>");  
        builder.append("请点击下方链接修改您的密码!(此链接有效期为2分钟)");  
        builder.append("<br/><br/>");  
        builder.append("<a href=\"" + url + "\">");  
        builder.append(url);  
        builder.append("</a>");  
        builder.append("<br/><span>验证码为："+validacode+"</span>");
        builder.append("<br/><span>发生纠纷就爱上了就发生了放假啦就发生了弗萨勒夫骄傲十六分将发生</span>");
        builder.append("</body></html>"); 
        String smtp=MyProperties.getInstance().getProperty("smtp");
		String sender=MyProperties.getInstance().getProperty("sender"); //发送方
		String mail=user.getEmail();    //接收方
		String uname=MyProperties.getInstance().getProperty("uname");  //发送方名字
		String pwd=MyProperties.getInstance().getProperty("pwd");     //密码
		String title="修改密码";
        
//		//1.直接发邮件
//		SendMailUtil.sendMail(smtp, sender, mail, uname, pwd, title, builder.toString());
//		//2.利用一个producer来发消息到kafka
//		MailInfo mailInfo= new MailInfo();
//		mailInfo.setTo(mail);
//		mailInfo.setSubject(title);
//		mailInfo.setBody(builder.toString());
//		EmailKafkaProducer.producer(mailInfo);
		//3.利用executor线程池，当要发送一个消息时，包装一个任务，交给线程池，线程池随机选择一个线程一个执行任务
		MailInfo mailInfo= new MailInfo();
		mailInfo.setTo(mail);
		mailInfo.setSubject(title);
		mailInfo.setBody(builder.toString());
		//1.利用监听器（application 创建），创建  executor对象， 存 application
		ExecutorService executor= (ExecutorService) super.application.getAttribute("executor");
		//2.任务类
		executor.submit(new HandlerProducer("mails", 3, mailInfo));
		
		
		
		//发送邮件时的时间
		long time=System.currentTimeMillis();
		request.getSession().setAttribute("time", time);
		request.getRequestDispatcher("/WEB-INF/pages/mail.jsp").forward(request, response);
	}

	private void isUsernameExistOp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		Resuser user = resuserBiz.isUserNameExist(username);
		JsonModel jm = new JsonModel();
		if (user == null) {
			jm.setCode(1);
		} else {
			jm.setCode(0);
		}
		super.writeJson(response, jm);
	}

	private void toRegOp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/reg.jsp").forward(request, response);
	}

	/**
	 * 到登录页面
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void toLoginOp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
	}

	/**
	 * 判断用户是否已经登录
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void isResuserLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JsonModel jm = new JsonModel();
		if (session.getAttribute(LOGINUSER) != null) {
			jm.setCode(1);
			jm.setObj(session.getAttribute(LOGINUSER));
		} else {
			jm.setCode(0);
		}
		super.writeJson(response, jm);
	}

	private void toChangePwd(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/changePwd.jsp").forward(request, response);
	}

	private void changePwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Resuser user = RequestUtil.parseRequest(request, Resuser.class);
		Integer validacode =(Integer) request.getSession().getAttribute("validacode");
		Integer validacode2 =Integer.parseInt(request.getParameter("validacode"));//从页面传过来，但resuser对象中没有这个列
		if (validacode.equals(validacode2) == false) {
			request.setAttribute("msg", "validacode  failed");// 存出错的信息
			request.getRequestDispatcher("/WEB-INF/pages/changePwd.jsp").forward(request, response);
		} else {
			user =resuserBiz.changePwd(user);
			if (user != null) {
				request.setAttribute("msg", "修改密码成功");
				request.getSession().removeAttribute(LOGINUSER);
				request.getRequestDispatcher(LOGINPAGE).forward(request, response);
			} else {
				request.setAttribute("msg","changePwd failed");
				request.getRequestDispatcher("/WEB-INF/pages/changePwd.jsp").forward(request, response);
			}
		}

	}

	

	

	private void reg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Resuser user = RequestUtil.parseRequest(request, Resuser.class); // username,
																			// pwd,
																			// repwd
		// 判断验证码
		// 从session中取出标准验证码
		HttpSession session = request.getSession();
		String rand = (String) session.getAttribute("rand");

		String valcode = request.getParameter("valcode"); // 从页面传过来，但
															// resuer对象中没有这个列
		if (rand.equals(valcode) == false) {
			request.setAttribute("msg", "valide code failed");
			request.getRequestDispatcher("/WEB-INF/pages/reg.jsp").forward(request, response);
		} else {
			user = resuserBiz.reg(user);
			if (user != null) {
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			} else {
				request.setAttribute("msg", "username or password is wrong ,reg failed");
				request.getRequestDispatcher("/WEB-INF/pages/reg.jsp").forward(request, response);
			}
		}
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Resuser user = RequestUtil.parseRequest(request, Resuser.class);
		// 判断验证码
		// 从session中取出标准验证码
		HttpSession session = request.getSession();
		String rand = (String) session.getAttribute("rand");

		String valcode = request.getParameter("valcode");
		if (rand.equals(valcode) == false) {
			request.setAttribute("msg", "valide code failed"); // 存出错的信息
			request.getRequestDispatcher(LOGINPAGE).forward(request, response);
		} else {
			user = resuserBiz.login(user);
			if (user != null) {
				session.setAttribute(LOGINUSER, user); // 用于权限
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			} else {
				request.setAttribute("msg", "username or password is wrong ,login failed");
				request.getRequestDispatcher(LOGINPAGE).forward(request, response);
			}
		}
	}

	/**
	 * 用户退出
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		session.removeAttribute(LOGINUSER);
		resp.sendRedirect("index.jsp");
	}

}
