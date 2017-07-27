package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.ISupplierBiz;
import cn.redsun.bos.dao.ISupplierDao;
import cn.redsun.bos.entity.Supplier;
/**
 * 供应商业务逻辑类
 * @author Administrator
 *
 */
public class SupplierBiz extends BaseBiz<Supplier> implements ISupplierBiz {

	private ISupplierDao supplierDao;
	
	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
		super.setBaseDao(this.supplierDao);
	}
	
}
