package com.tojohnonly.email.controller;

import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
public class SendMailController {
	// 在spring中配置的邮件发送的bean
	@Autowired
	private JavaMailSender javaMailSender;

	@RequestMapping("send")
	public Object sendEmail() {
		// 创建邮件对象
		MimeMessage mMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mMessageHelper;
		Properties prop = new Properties();
		String from;
		try {
			// 从配置文件中拿到发件人邮箱地址
			prop.load(this.getClass().getResourceAsStream("/mail.properties"));
			from = prop.get("mail.smtp.username") + "";
			mMessageHelper = new MimeMessageHelper(mMessage, true);
			// 发件人邮箱
			mMessageHelper.setFrom(from);
			// 收件人邮箱
			mMessageHelper.setTo("1562960377@qq.com");
			// 邮件的主题
			mMessageHelper.setSubject("Spring的邮件发送");
			// 邮件的文本内容 , true 表示以 html 格式发送 , false 表示普通文本文本
			mMessageHelper.setText("这是使用spring的邮件功能发送的一封邮件", false);
			// 用 html 格式发送
			// mMessageHelper.setText("<h2>这是使用spring的邮件功能发送的一封邮件</h2>", true);
			// 发送邮件
			javaMailSender.send(mMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "发送成功";
	}

}
