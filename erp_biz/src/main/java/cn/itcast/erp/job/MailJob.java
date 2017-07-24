package cn.itcast.erp.job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.entity.Storealert;
import cn.itcast.erp.utils.MailUtil;

public class MailJob {
	private IStoredetailBiz storedetailBiz;
	private MailUtil mailUtil;//邮箱工具
	private String to;//收件人
	private String subject;//邮件标题
	private String text;//邮件内容
	
	private static final Logger log=LoggerFactory.getLogger(MailJob.class);
	
	public void doJob(){
		List<Storealert> storeAlertList = storedetailBiz.getStoreAlertList();
		log.info("预警商品各类"+storeAlertList.size());
		if(null!=storeAlertList&&storeAlertList.size()>0){
			DateFormat dFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			try {
				mailUtil.sendMail(to, 
						subject.replace("[time]", dFormat.format(new Date())),
						text.replace("[count]",storeAlertList.size()+""));
				log.info("发送预警成功！email："+to);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("发送失败");
			}
		}
	}
	
	
	
	
	
	
	
	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setText(String text) {
		this.text = text;
	}







	public void setStoredetailBiz(IStoredetailBiz storedetailBiz) {
		this.storedetailBiz = storedetailBiz;
	}

}
