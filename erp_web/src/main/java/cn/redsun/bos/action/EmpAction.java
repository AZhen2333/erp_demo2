package cn.redsun.bos.action;
import cn.redsun.bos.biz.IEmpBiz;
import cn.redsun.bos.entity.Emp;

/**
 * 员工Action 
 * @author Administrator
 *
 */
public class EmpAction extends BaseAction<Emp> {

	private IEmpBiz empBiz;

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
		super.setBaseBiz(this.empBiz);
	}

}
