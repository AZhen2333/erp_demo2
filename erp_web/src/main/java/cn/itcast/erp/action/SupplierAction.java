package cn.itcast.erp.action;

import cn.itcast.erp.biz.ISupplierBiz;
import cn.itcast.erp.entity.Supplier;
import oracle.net.aso.n;

/**
 * 供应商Action
 * 
 * @author Administrator
 *
 */
public class SupplierAction extends BaseAction<Supplier> {

	private ISupplierBiz supplierBiz;

	private String p;

	// 自动补全
	public void list() {
		if(null==getT1()){
			setT1(new Supplier());
		}
		getT1().setName(p);
		super.list();
	}

	public void setSupplierBiz(ISupplierBiz supplierBiz) {
		this.supplierBiz = supplierBiz;
		super.setBaseBiz(this.supplierBiz);
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

}
