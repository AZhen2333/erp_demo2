package cn.itcast.erp.action;

import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IStoredetailBiz;
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

	// 库存预警
	public void getStoreAlert() {
		List<Storealert> storeAlertList = storedetailBiz.getStoreAlertList();
		write(JSON.toJSONString(storeAlertList));
	}

}
