package cn.itcast.erp.action;

import java.util.List;

import javax.mail.MessagingException;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.biz.exception.ErpException;
import cn.itcast.erp.entity.Storealert;
import cn.itcast.erp.entity.Storedetail;

/**
 * 仓库库存Action
 * 
 * @author Administrator
 *
 */
public class StoredetailAction extends BaseAction<Storedetail> {

	private IStoredetailBiz storedetailBiz;

	public void setStoredetailBiz(IStoredetailBiz storedetailBiz) {
		this.storedetailBiz = storedetailBiz;
		super.setBaseBiz(this.storedetailBiz);
	}

	/*
	 * 库存预警
	 * */ 
	public void getStoreAlert() {
		List<Storealert> storeAlertList = storedetailBiz.getStoreAlertList();
		write(JSON.toJSONString(storeAlertList));
	}
	
	/*
	 * 发送预警邮件
	 * */
	public void sendStorealterMail(){
		try {
			//调用预警业务，发送邮件
			storedetailBiz.sendStoreAlterMail();
			ajaxReturn(true, "发送预警邮件成功");
		}catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			ajaxReturn(false, "发送预警邮件失败");
			e.printStackTrace();
		}
	}

}
