package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.IEmpBiz;
import cn.redsun.bos.dao.IEmpDao;
import cn.redsun.bos.entity.Emp;
/**
 * 员工业务逻辑类
 * @author Administrator
 *
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {

	private IEmpDao empDao;
	
	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
		super.setBaseDao(this.empDao);
	}
	
}
