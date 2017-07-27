package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.IStoredetailBiz;
import cn.redsun.bos.dao.IStoredetailDao;
import cn.redsun.bos.entity.Storedetail;
/**
 * 仓库库存业务逻辑类
 * @author Administrator
 *
 */
public class StoredetailBiz extends BaseBiz<Storedetail> implements IStoredetailBiz {

	private IStoredetailDao storedetailDao;
	
	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
		super.setBaseDao(this.storedetailDao);
	}
	
}
