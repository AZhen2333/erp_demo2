package cn.itcast.erp.action;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.biz.exception.ErpException;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;

/**
 * 订单Action
 * 
 * @author Administrator
 *
 */
public class OrdersAction extends BaseAction<Orders> {

	private IOrdersBiz ordersBiz;

	private static final Logger log = LoggerFactory.getLogger(OrdersAction.class);

	public void setOrdersBiz(IOrdersBiz ordersBiz) {
		this.ordersBiz = ordersBiz;
		super.setBaseBiz(this.ordersBiz);
	}

	private String json;

	@Override
	public void add() {
		Emp loginUser = getLoginUser();
		log.info("loginuser:" + (loginUser == null ? "" : loginUser.getUuid()));
		log.debug("orderdatails:" + json);
		if (null == loginUser) {
			ajaxReturn(false, "请先登录");
		}
		try {
			// 获取订单
			Orders orders = getT();
			// 设置下单员（登录用户）
			orders.setCreater(getLoginUser().getUuid());
			// 设置订单明细
			List<Orderdetail> orderdetails = JSON.parseArray(json, Orderdetail.class);
			orders.setOrderdetails(orderdetails);
			ordersBiz.add(orders);
			ajaxReturn(true, "新增订单成功");
		} catch (Exception e) {
			ajaxReturn(false, "新增订单失败");
			log.info("新增失败原因：", e);
		}
	}

	/*
	 * 订单状态审核
	 */
	public void doCheck() {
		Emp loginUser = getLoginUser();
		if (null == loginUser) {
			ajaxReturn(false, "请先登录");
			return;
		}
		try {
			ordersBiz.doCheck(getId(), loginUser.getUuid());
			ajaxReturn(true, "审核成功");
		} catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			ajaxReturn(false, "审核失败");
			e.printStackTrace();
		}

	}

	/*
	 * 订单状态确认
	 */
	public void doStart() {
		Emp loginUser = getLoginUser();
		if (null == loginUser) {
			ajaxReturn(false, "请先登录");
			return;
		}
		try {
			ordersBiz.doStart(getId(), loginUser.getUuid());
			ajaxReturn(true, "确认成功");
		} catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			ajaxReturn(false, "确认失败");
			e.printStackTrace();
		}
		
	}

	
	/*
	 * 只显示我的订单
	 * */
	public void myListByPage(){
		if(null==getT1()){
			setT1(new Orders());
		}
		Emp loginUser = getLoginUser();
		getT1().setChecker(loginUser.getUuid());
		super.listByPage();
	}
	
	
	public void setJson(String json) {
		this.json = json;
	}

}
