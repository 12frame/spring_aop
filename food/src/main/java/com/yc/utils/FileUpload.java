package com.yc.utils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * 文件上传帮助类 <br />
 * 组件: jspsmartupload <br />
 * 功能 : 可以进行多文件上传,可以配置上传类型，拒绝类型，大小.<br />
 * 可设置的参数:    saveDir:保存文件夹名   <br />
 * @author 张影
 */
public class FileUpload {
	private long singleSize = 10 * 1024 * 1024;
	private String allowedFilesList = "gif,jpg,jpeg,png";
	private String deniedFilesList = "jsp,asp,php,aspx,html,htm,exe,bat,sh";
	private long totalFileSize = 5 * singleSize;

	/** 保存文件夹名 */
	private String saveDir = "pic";

	

	public Map<String, String> uploadFiles(PageContext pageContext, HttpServletRequest request)
			throws SmartUploadException, IOException, ServletException, SQLException {
		Map<String, String> map = new HashMap<String, String>();

		SmartUpload su = new SmartUpload();
		su.initialize(pageContext); // 初始化
		su.setCharset("utf-8");

		// 定义允许上传文件类型
		su.setAllowedFilesList(allowedFilesList);
		// 不允许上传文件类型
		su.setDeniedFilesList(deniedFilesList);
		// 单个文件最大限制
		su.setMaxFileSize(singleSize);
		// 所有上传文件的总容量限制
		su.setTotalMaxFileSize(totalFileSize);

		su.upload();

		// 取参数 Request是smartupload的request -> HttpServletRequest
		Request re = su.getRequest();
		Enumeration<String> enu = re.getParameterNames();
		while (enu.hasMoreElements()) {
			String pn = enu.nextElement();
			map.put(pn, re.getParameter(pn));
		}

		// 取出上传的文件列表
		Files files = su.getFiles();
		int count = files.getCount();
		if (files != null && count > 0) {
			for (int i = 0; i < count; i++) {
				// 取tomcat路径
				Calendar c = Calendar.getInstance();
				String tomcatdir = request.getRealPath("/"); // xxx/xxx/webapps/yc3637web1
				File tomcatFile = new File(tomcatdir);
				File webapppath = tomcatFile.getParentFile(); // xxx/xxx/webapps

				// xxx/xxx/webapps/pic/2017/7

				File picpath = new File(webapppath, saveDir + File.separator + c.get(Calendar.YEAR) + File.separator
						+ (c.get(Calendar.MONTH) + 1) + File.separator);
				// 访问路径名
				String weburl = "../"+saveDir+"/" + c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/";
				// 判断目录是否存在，不在则创建
				if (picpath.exists() == false) {
					picpath.mkdirs();
				}

				// 只取列表中的第一个文件 , 写全路径，防止系统以为是 java.io.File类
				com.jspsmart.upload.File file = files.getFile(i);

				String filePrefixName = genNewFilePrefixName();
				// 取出原文件的后缀名
				String extName = file.getFileExt();
				// 拼接新的文件名
				String fileName = filePrefixName + "." + extName;

				// xxx/xxx/webapps/pic/2017/7/xxx.jpgyyyjpg
				weburl += fileName;
				// 物理路径: xxx/xxx/webapps/pic/2017/7/20170720222222.png
				String destFilePathName = picpath + "/" + fileName;
				// 存
				file.saveAs(destFilePathName, SmartUpload.SAVE_PHYSICAL);

				String fieldName = file.getFieldName(); // pic1
				String oldFileName = file.getFileName();

				map.put(fieldName + "_weburl", weburl);
				map.put(fieldName + "_destFilePathName", destFilePathName);
				map.put(fieldName + "_fileName", oldFileName);
			}

		}
		return map;
	}

	/**
	 * 生成新文件名 <br />
	 * 
	 * @return
	 */
	private String genNewFilePrefixName() {
		// 生成新的文件名
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("SSyyyymmddHHmmss");
		String filePrefixName = sdf.format(d); // 文件的前缀名
		return filePrefixName;
	}
	
	public String getSaveDir() {
		return saveDir;
	}

	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}

	public long getSingleSize() {
		return singleSize;
	}

	public void setSingleSize(long singleSize) {
		this.singleSize = singleSize;
	}

	public String getAllowedFilesList() {
		return allowedFilesList;
	}

	public void setAllowedFilesList(String allowedFilesList) {
		this.allowedFilesList = allowedFilesList;
	}

	public String getDeniedFilesList() {
		return deniedFilesList;
	}

	public void setDeniedFilesList(String deniedFilesList) {
		this.deniedFilesList = deniedFilesList;
	}

	public long getTotalFileSize() {
		return totalFileSize;
	}

	public void setTotalFileSize(long totalFileSize) {
		this.totalFileSize = totalFileSize;
	}

}
