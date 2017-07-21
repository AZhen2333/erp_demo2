package cn.itcast.erp.biz.impl;

import java.util.Date;
import java.util.List;

import cn.itcast.erp.biz.IOrderdetailBiz;
import cn.itcast.erp.biz.exception.ErpException;
import cn.itcast.erp.dao.IOrderdetailDao;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.dao.IStoreoperDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.entity.Storeoper;

/**
 * 订单明细业务逻辑类
 * 
 * @author Administrator
 *
 */
public class OrderdetailBiz extends BaseBiz<Orderdetail> implements IOrderdetailBiz {

	private IOrderdetailDao orderdetailDao;
	private IStoredetailDao storedetailDao;
	private IStoreoperDao storeoperDao;

	/*
	 * 入库
	 */
	@Override
	public void doInStore(Long uuid, Long empuid, Long storeuuid) {
		/* * 更新订单明细 */
		Orderdetail orderdetail = orderdetailDao.get(uuid);
		// 检查是否入库
		if (!orderdetail.STATE_NOT_IN.equals(orderdetail.getState())) {
			throw new ErpException("已经入库了");
		}
		// 更该入库状态
		orderdetail.setState(orderdetail.STATE_IN);
		// 封装仓库管理员
		orderdetail.setEnder(empuid);
		// 更新仓库编号
		orderdetail.setStoreuuid(storeuuid);
		// 入库日期
		orderdetail.setEndtime(new Date());

		/* * 更新库存 */
		// 构建查询条件
		Storedetail storedetail = new Storedetail();
		storedetail.setStoreuuid(storeuuid);
		storedetail.setGoodsuuid(orderdetail.getGoodsuuid());
		List<Storedetail> storedetailList = storedetailDao.getList(storedetail, null, null);
		// 判断库存
		if (storedetailList.size() > 0) {// 有库存
			storedetail = storedetailList.get(0);
			storedetail.setNum(storedetail.getNum() + orderdetail.getNum());
		} else {
			storedetail.setNum(orderdetail.getNum());
			storedetailDao.add(storedetail);
		}

		/* * 更新仓库日志 */
		Storeoper storeoperLog = new Storeoper();
		storeoperLog.setEmpuuid(empuid);
		storeoperLog.setGoodsuuid(orderdetail.getGoodsuuid());
		storeoperLog.setNum(orderdetail.getNum());
		storeoperLog.setOpertime(orderdetail.getEndtime());
		storeoperLog.setStoreuuid(storeuuid);
		storeoperLog.setType(Storeoper.TYPE_IN);
		storeoperDao.add(storeoperLog);

		/* * 更新订单 */
		Orders orders = orderdetail.getOrders();
		// 构造查询条件
		Orderdetail queryOrderdetail = new Orderdetail();
		queryOrderdetail.setOrders(orders);
		queryOrderdetail.setState(Orderdetail.STATE_NOT_IN);
		long count = orderdetailDao.getCount(queryOrderdetail, null, null);
		if (count == 0) {// 订单内的每条记录都已经入库后，才更新订单
			orders.setEndtime(orderdetail.getEndtime());// 入库时间
			orders.setEnder(orderdetail.getEnder());// 仓库管理员
			orders.setState(Orders.STATE_END);// 入库状态
		}

	}

	public void setOrderdetailDao(IOrderdetailDao orderdetailDao) {
		this.orderdetailDao = orderdetailDao;
		super.setBaseDao(this.orderdetailDao);
	}

	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}

	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
	}

}
