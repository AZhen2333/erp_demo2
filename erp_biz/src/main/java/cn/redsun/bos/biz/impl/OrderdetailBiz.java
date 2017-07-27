package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.IOrderdetailBiz;
import cn.redsun.bos.dao.IOrderdetailDao;
import cn.redsun.bos.entity.Orderdetail;
/**
 * 订单明细业务逻辑类
 * @author Administrator
 *
 */
public class OrderdetailBiz extends BaseBiz<Orderdetail> implements IOrderdetailBiz {

	private IOrderdetailDao orderdetailDao;
	
	public void setOrderdetailDao(IOrderdetailDao orderdetailDao) {
		this.orderdetailDao = orderdetailDao;
		super.setBaseDao(this.orderdetailDao);
	}
	
}
