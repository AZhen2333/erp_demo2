package cn.itcast.erp.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


/* 发送邮件工具
 * @author Tay
 *
 */
public class MailUtil {
	private JavaMailSender sender;//邮件发送类
	private String from;//发件人
	
	public void sendMail(String to,String subject,String text) throws MessagingException{
		//创建邮件
		MimeMessage mimeMessage=sender.createMimeMessage();
		//邮件包装工具
		MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,"utf-8");
		//发件人
		helper.setFrom(from);
		//收件人
		helper.setTo(to);
		//邮件标题
		helper.setSubject(subject);
		//邮件正文
		helper.setText(text);
		//发送邮件
		sender.send(mimeMessage);
	}
	public void setSender(JavaMailSender sender) {
		this.sender = sender;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	
}
