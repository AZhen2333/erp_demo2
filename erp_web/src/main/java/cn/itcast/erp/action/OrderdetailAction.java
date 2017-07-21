package cn.itcast.erp.action;
import cn.itcast.erp.biz.IOrderdetailBiz;
import cn.itcast.erp.biz.exception.ErpException;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Orderdetail;

/**
 * 订单明细Action 
 * @author Administrator
 *
 */
public class OrderdetailAction extends BaseAction<Orderdetail> {

	private IOrderdetailBiz orderdetailBiz;

	public void setOrderdetailBiz(IOrderdetailBiz orderdetailBiz) {
		this.orderdetailBiz = orderdetailBiz;
		super.setBaseBiz(this.orderdetailBiz);
	}
	
	/*属性驱动 */
	private Long storeuuid;

	/*入库*/
	public void doInStore(){
		Emp loginUser = getLoginUser();
		if(null==loginUser){
			ajaxReturn(false, "请先登录");
			return;
		}
		try {
			orderdetailBiz.doInStore(getId(), loginUser.getUuid(), storeuuid);
			ajaxReturn(true, "入库成功");
		}catch (ErpException e) {
			ajaxReturn(false, e.getMessage());
		} catch (Exception e) {
			ajaxReturn(false, "入库失败");
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	public Long getStoreuuid() {
		return storeuuid;
	}

	public void setStoreuuid(Long storeuuid) {
		this.storeuuid = storeuuid;
	}
	
	

}
