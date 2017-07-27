package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.IStoreBiz;
import cn.redsun.bos.dao.IStoreDao;
import cn.redsun.bos.entity.Store;
/**
 * 仓库业务逻辑类
 * @author Administrator
 *
 */
public class StoreBiz extends BaseBiz<Store> implements IStoreBiz {

	private IStoreDao storeDao;
	
	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
		super.setBaseDao(this.storeDao);
	}
	
}
