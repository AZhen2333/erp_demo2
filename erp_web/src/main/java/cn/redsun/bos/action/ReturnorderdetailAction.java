package cn.redsun.bos.action;
import cn.redsun.bos.biz.IReturnorderdetailBiz;
import cn.redsun.bos.entity.Returnorderdetail;

/**
 * 退货订单明细Action 
 * @author Administrator
 *
 */
public class ReturnorderdetailAction extends BaseAction<Returnorderdetail> {

	private IReturnorderdetailBiz returnorderdetailBiz;

	public void setReturnorderdetailBiz(IReturnorderdetailBiz returnorderdetailBiz) {
		this.returnorderdetailBiz = returnorderdetailBiz;
		super.setBaseBiz(this.returnorderdetailBiz);
	}

}
