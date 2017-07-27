package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.IReturnorderdetailBiz;
import cn.redsun.bos.dao.IReturnorderdetailDao;
import cn.redsun.bos.entity.Returnorderdetail;
/**
 * 退货订单明细业务逻辑类
 * @author Administrator
 *
 */
public class ReturnorderdetailBiz extends BaseBiz<Returnorderdetail> implements IReturnorderdetailBiz {

	private IReturnorderdetailDao returnorderdetailDao;
	
	public void setReturnorderdetailDao(IReturnorderdetailDao returnorderdetailDao) {
		this.returnorderdetailDao = returnorderdetailDao;
		super.setBaseDao(this.returnorderdetailDao);
	}
	
}
