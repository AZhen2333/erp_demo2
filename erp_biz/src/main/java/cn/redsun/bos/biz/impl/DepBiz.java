package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.IDepBiz;
import cn.redsun.bos.dao.IDepDao;
import cn.redsun.bos.entity.Dep;
/**
 * 部门业务逻辑类
 * @author Administrator
 *
 */
public class DepBiz extends BaseBiz<Dep> implements IDepBiz {

	private IDepDao depDao;
	
	public void setDepDao(IDepDao depDao) {
		this.depDao = depDao;
		super.setBaseDao(this.depDao);
	}
	
}
