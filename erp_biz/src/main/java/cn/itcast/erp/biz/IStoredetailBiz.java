package cn.itcast.erp.biz;
import java.util.List;

import javax.mail.MessagingException;

import cn.itcast.erp.entity.Storealert;
import cn.itcast.erp.entity.Storedetail;
/**
 * 仓库库存业务逻辑层接口
 * @author Administrator
 *
 */
public interface IStoredetailBiz extends IBaseBiz<Storedetail>{
	//库存预警列表
	List<Storealert> getStoreAlertList();
	//发送预警邮箱
	public void sendStoreAlterMail() throws MessagingException;
	
}

