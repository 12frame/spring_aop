package com.yc.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * 适配器 <br />
 * 它是抽象的,实现或继承一个类或接口，对这个接口或类中的方法做通用的实现
 */
public abstract class BaseServlet extends HttpServlet {
	// 利用protected让子类能访问到 op
	protected String op;
	protected ServletContext application;
	protected HttpSession session;

	// 这样每个子类无须再写这个 doGet()
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override // 利用servlet的生命周期中的service方法一定会首先调用这个特点，完成通用功能的抽能
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		application = req.getSession().getServletContext();
		session = req.getSession();
		req.setCharacterEncoding("utf-8"); // 编码集的设定必须在request取第一次参数前
		op = req.getParameter("op"); // 利用隐藏域完成操作分发
		super.service(req, resp);
	}
	// User
	public void writeJson(HttpServletResponse response, Object obj) throws IOException {
		Gson gson = new Gson();
		String jsonString = gson.toJson(obj);
		writeJson( response, jsonString);
	}
	//     List<T>     TypeToken();
	public void writeJson(HttpServletResponse response, String jsonString) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jsonString);
		out.flush();
		out.close();
	}

}
