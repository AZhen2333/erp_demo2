package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.IOrdersBiz;
import cn.redsun.bos.dao.IOrdersDao;
import cn.redsun.bos.entity.Orders;
/**
 * 订单业务逻辑类
 * @author Administrator
 *
 */
public class OrdersBiz extends BaseBiz<Orders> implements IOrdersBiz {

	private IOrdersDao ordersDao;
	
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
		super.setBaseDao(this.ordersDao);
	}
	
}
