package com.yc.utils;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMailUtil {

	/**
	 * 
	 * @param smtp
	 *            服务器地址
	 * @param from
	 *            发送方
	 * @param to
	 *            接收方
	 * @param user
	 *            用户名
	 * @param password
	 *            邮箱密码
	 * @param subject
	 *            标题
	 * @param body
	 *            内容
	 * @param filename
	 *            附件文件名
	 */
	public static void sendMail(String smtp, String from, String to, String user, String password, String subject,
			String body) {
		try {
			// 获取服务器属性
			Properties props = new Properties();
			// 设置SMTP服务器
			props.put("mail.transport.protocol", "smtp"); // 设置邮件协议
			props.put("mail.smtp.auth", "true"); // 邮件登录要验证
			// props.put("mail.smtp.socketFactory.class",
			// "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.port", 465); // smtp服务的端口
			// props.put("mail.smtp.socketFactory.port", 465);

			props.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
			props.put("mail.debug", "true");


			// 由邮箱会话创建一个消息对象 , 一个会话就是一次与 smtp服务器的连接
			Session session = Session.getInstance(props);

			MimeMessage message = new MimeMessage(session); // 创建一个消息对象,这个消息对象用来发送信息
			InternetAddress fromAddress = new InternetAddress(from); // 创建一个发送方地址对象,用于存发送方地址
			message.setFrom(fromAddress);

			Address toAddress = new InternetAddress(to); // 创建一个接收方地址对象
			message.addRecipient(Message.RecipientType.TO, toAddress);

			message.setSubject(subject); // 设置标题
			message.setText(body); // 设置邮件内容

			// 发送带链接的文件
			message.setContent(body, "text/html;charset=UTF-8");
			
			// 创建发送对象
			Transport transport = session.getTransport("smtp");
			// 联接smtp服务器
			transport.connect(smtp, user, password);
			// 发送...
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			System.out.println("发送成功");
			transport.close();
		} catch (Exception m) {
			m.printStackTrace();
		}
	}
}
