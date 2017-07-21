package cn.itcast.erp.biz.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.biz.exception.ErpException;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;

/**
 * 订单业务逻辑类
 * 
 * @author Administrator
 *
 */
public class OrdersBiz extends BaseBiz<Orders> implements IOrdersBiz {

	/*
	 * 注入bean
	 */
	private IOrdersDao ordersDao;
	private IEmpDao empDao;
	private ISupplierDao supplierDao;

	@Override // 添加采购订单
	public void add(Orders orders) {
		// 生成日期
		orders.setCreatetime(new Date());
		// 订单状态
		orders.setState(orders.STATE_CREATE);
		// 订单类型
//		orders.setType(orders.TYPE_IN);
		// 合计金额
		double totalMoney = 0;
		for (Orderdetail orderdetail : orders.getOrderdetails()) {
			totalMoney += orderdetail.getMoney();
			orderdetail.setOrders(orders);
		}
		orders.setTotalmoney(totalMoney);

		ordersDao.add(orders);
	}

	public List<Orders> getListByPage(Orders t1, Orders t2, Object param, int firstResult, int maxResults) {
		// 通过父类，获取orders分页
		List<Orders> list = super.getListByPage(t1, t2, param, firstResult, maxResults);
		// emp的名字缓存
		Map<Long, String> empNameMap = new HashMap<Long, String>();
		// supplier的名字缓存
		Map<Long, String> supplierNameMap = new HashMap<Long, String>();
		// 遍历，封装 下单员名称 审核员名称 采购员名称 库管员名称 供应商或客户名称
		for (Orders orders : list) {
			orders.setCreaterName(getEmpName(orders.getCreater(), empNameMap));
			orders.setCheckerName(getEmpName(orders.getChecker(), empNameMap));
			orders.setStarterName(getEmpName(orders.getStarter(), empNameMap));
			orders.setEnderName(getEmpName(orders.getEnder(), empNameMap));
			orders.setSupplierName(getSupplierName(orders.getSupplieruuid(), supplierNameMap));
		}
		return list;
	}

	/*
	 * 通过uuid获取emp的名字
	 */
	private String getEmpName(Long uuid, Map<Long, String> empNameMap) {
		if (null == uuid) {
			return null;
		}
		String empName = empNameMap.get(uuid);
		if (null == empName) {
			empName = empDao.get(uuid).getName();
			empNameMap.put(uuid, empName);
		}
		return empName;
	}

	/*
	 * 通过uuid获取supplier的名字
	 */
	private String getSupplierName(Long uuid, Map<Long, String> supplierNameMap) {
		if (null == uuid) {
			return null;
		}
		String supplierName = supplierNameMap.get(uuid);
		if (null == supplierName) {
			supplierName = supplierDao.get(uuid).getName();
			supplierNameMap.put(uuid, supplierName);
		}
		return supplierName;
	}

	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
		super.setBaseDao(this.ordersDao);
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	/*
	 * 采购订单审核
	 */
	@SuppressWarnings("static-access")
	@Override
	public void doCheck(Long uuid, Long empuuid) {
		// 订单信息
		Orders orders = ordersDao.get(uuid);
		// 是否审核
		if (!orders.getState().equals(orders.STATE_CREATE)) {
			throw new ErpException("已审核");
		}
		// 封装审核员、审核时间、审核状态
		orders.setChecker(empuuid);
		orders.setChecktime(new Date());
		orders.setState(orders.STATE_CHECK);

	}

	/*
	 * 采购订单确认
	 */
	@Override
	public void doStart(Long uuid, Long empuuid) {
		// 订单信息
		Orders orders = ordersDao.get(uuid);
		// 是否审核
		if (!orders.getState().equals(orders.STATE_CHECK)) {
			throw new ErpException("已确定过了");
		}
		// 封装审核员、审核时间、审核状态
		orders.setStarter(empuuid);
		orders.setStarttime(new Date());
		orders.setState(orders.STATE_START);

	}
}
