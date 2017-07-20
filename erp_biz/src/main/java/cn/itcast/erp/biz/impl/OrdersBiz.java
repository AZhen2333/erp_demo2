package cn.itcast.erp.biz.impl;

import java.util.Date;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
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
	
	
	@Override
	public void add(Orders orders) {
		//生成日期
		orders.setCreatetime(new Date());
		//订单状态
		orders.setState(orders.STATE_CREATE);
		//订单类型
		orders.setType(orders.TYPE_IN);
		//合计金额
		double totalMoney=0;
		for (Orderdetail orderdetail : orders.getOrderdetails()) {
			totalMoney+=orderdetail.getMoney();
			orderdetail.setOrders(orders);
		}
		orders.setTotalmoney(totalMoney);
		
		ordersDao.add(orders);
	}
}
