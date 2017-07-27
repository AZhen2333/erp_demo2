package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.IStoreoperBiz;
import cn.redsun.bos.dao.IStoreoperDao;
import cn.redsun.bos.entity.Storeoper;
/**
 * 仓库操作记录业务逻辑类
 * @author Administrator
 *
 */
public class StoreoperBiz extends BaseBiz<Storeoper> implements IStoreoperBiz {

	private IStoreoperDao storeoperDao;
	
	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
		super.setBaseDao(this.storeoperDao);
	}
	
}
