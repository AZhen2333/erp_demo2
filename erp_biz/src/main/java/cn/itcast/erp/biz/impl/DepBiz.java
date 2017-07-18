package cn.itcast.erp.biz.impl;

import cn.itcast.erp.biz.IDepBiz;
import cn.itcast.erp.biz.exception.ErpException;
import cn.itcast.erp.dao.IDepDao;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.entity.Dep;
import cn.itcast.erp.entity.Emp;

/**
 * 部门业务逻辑类
 * 
 * @author Administrator
 *
 */
public class DepBiz extends BaseBiz<Dep> implements IDepBiz {

	private IDepDao depDao;

	public void setDepDao(IDepDao depDao) {
		this.depDao = depDao;
		super.setBaseDao(this.depDao);
	}

	private IEmpDao empDao;
	
	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}
	

	public void delete(Long uuid) {
		Dep dep = new Dep();
		Emp emp = new Emp();
		dep.setUuid(uuid);
		emp.setDep(dep);
		long count = empDao.getCount(emp, null, null);
		if (count > 0) {
			throw new ErpException("有员工不能删除");
		} 
			super.delete(uuid);
		

	}

	
}
