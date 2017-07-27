package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.IReturnordersBiz;
import cn.redsun.bos.dao.IReturnordersDao;
import cn.redsun.bos.entity.Returnorders;
/**
 * 退货订单业务逻辑类
 * @author Administrator
 *
 */
public class ReturnordersBiz extends BaseBiz<Returnorders> implements IReturnordersBiz {

	private IReturnordersDao returnordersDao;
	
	public void setReturnordersDao(IReturnordersDao returnordersDao) {
		this.returnordersDao = returnordersDao;
		super.setBaseDao(this.returnordersDao);
	}
	
}
