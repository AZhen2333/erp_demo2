package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.IInventoryBiz;
import cn.redsun.bos.dao.IInventoryDao;
import cn.redsun.bos.entity.Inventory;
/**
 * 盘盈盘亏业务逻辑类
 * @author Administrator
 *
 */
public class InventoryBiz extends BaseBiz<Inventory> implements IInventoryBiz {

	private IInventoryDao inventoryDao;
	
	public void setInventoryDao(IInventoryDao inventoryDao) {
		this.inventoryDao = inventoryDao;
		super.setBaseDao(this.inventoryDao);
	}
	
}
