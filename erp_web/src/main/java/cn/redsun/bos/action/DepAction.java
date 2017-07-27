package cn.redsun.bos.action;
import cn.redsun.bos.biz.IDepBiz;
import cn.redsun.bos.entity.Dep;

/**
 * 部门Action 
 * @author Administrator
 *
 */
public class DepAction extends BaseAction<Dep> {

	private IDepBiz depBiz;

	public void setDepBiz(IDepBiz depBiz) {
		this.depBiz = depBiz;
		super.setBaseBiz(this.depBiz);
	}

}
