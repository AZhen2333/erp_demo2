package cn.itcast.erp.biz.impl;

import java.util.Date;
import java.util.List;

import com.redsun.bos.ws.impl.IWaybillWs;

import cn.itcast.erp.biz.IOrderdetailBiz;
import cn.itcast.erp.biz.exception.ErpException;
import cn.itcast.erp.dao.IOrderdetailDao;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.dao.IStoreoperDao;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.entity.Storeoper;
import cn.itcast.erp.entity.Supplier;

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
	private IWaybillWs waybillWs;
	private ISupplierDao supplierDao;
	/*
	 * 入库
	 */
	@SuppressWarnings("static-access")
	@Override
	public void doInStore(Long uuid, Long empuuid, Long storeuuid) {
		/* * 更新订单明细 */
		Orderdetail orderdetail = orderdetailDao.get(uuid);
		// 检查是否入库
		if (!orderdetail.STATE_NOT_IN.equals(orderdetail.getState())) {
			throw new ErpException("已经入库了");
		}
		// 更新入库状态
		orderdetail.setState(orderdetail.STATE_IN);
		// 封装仓库管理员
		orderdetail.setEnder(empuuid);
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
		storeoperLog.setEmpuuid(empuuid);
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
			orders.setEnder(empuuid);// 仓库管理员
			orders.setState(Orders.STATE_END);// 入库状态
		}

	}

	/*
	 * 出库
	 */
	public void doOutStore(Long uuid, Long empuuid, Long storeuuid) {
		/* 更新库存详单 */
		// 查询出库单
		Orderdetail orderdetail = orderdetailDao.get(uuid);
		// 判断是否已出库
		if (Orderdetail.STATE_OUT.equals(orderdetail.getState())) {
			throw new ErpException("已经出库了");
		}
		// 更新状态
		orderdetail.setState(Orderdetail.STATE_OUT);
		// 更新库管员
		orderdetail.setEnder(empuuid);
		// 更新仓库编号
		orderdetail.setStoreuuid(storeuuid);
		// 更新出库日期
		orderdetail.setEndtime(new Date());

		/* 更新库存 */
		Storedetail storedetail = new Storedetail();
		storedetail.setStoreuuid(storeuuid);
		storedetail.setGoodsuuid(orderdetail.getGoodsuuid());
		List<Storedetail> storedetailsList = storedetailDao.getList(storedetail, null, null);
		if (storedetailsList.size() > 0) {
			storedetail = storedetailsList.get(0);
			if (storedetail.getNum() < orderdetail.getNum()) {
				throw new ErpException("库存不足");
			}
			storedetail.setNum(storedetail.getNum() - orderdetail.getNum());
		}

		/* 更新仓库操作记录 */
		Storeoper storeoperLog = new Storeoper();
		storeoperLog.setEmpuuid(empuuid);
		storeoperLog.setGoodsuuid(orderdetail.getGoodsuuid());
		storeoperLog.setNum(orderdetail.getNum());
		storeoperLog.setOpertime(orderdetail.getEndtime());
		storeoperLog.setStoreuuid(storeuuid);
		storeoperLog.setType(Storeoper.TYPE_OUT);
		storeoperDao.add(storeoperLog);

		/* 更新订单 */
		Orders orders = orderdetail.getOrders();
		// 查看库存是否已更新完
		Orderdetail queryOrder = new Orderdetail();
		queryOrder.setOrders(orders);
		queryOrder.setState(Orderdetail.STATE_NOT_OUT);
		long count = storedetailDao.getCount(storedetail, null, null);
		if (count == 0) {
			orders.setEndtime(new Date());
			orders.setEnder(empuuid);
			orders.setState(Orders.STATE_END);
			//获取客户信息
			Supplier supplier = supplierDao.get(orders.getSupplieruuid());
			//调用红日系统在线预约下单
			Long addWaybill = waybillWs.addWaybill(1l, supplier.getAddress(), supplier.getName(), supplier.getTele(), "零食");
			//设置订单编号
			orders.setWaybillsn(addWaybill);
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

	public void setWaybillWs(IWaybillWs waybillWs) {
		this.waybillWs = waybillWs;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

}
