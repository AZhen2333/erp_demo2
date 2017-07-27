package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.IRoleBiz;
import cn.redsun.bos.dao.IRoleDao;
import cn.redsun.bos.entity.Role;
/**
 * 角色业务逻辑类
 * @author Administrator
 *
 */
public class RoleBiz extends BaseBiz<Role> implements IRoleBiz {

	private IRoleDao roleDao;
	
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
		super.setBaseDao(this.roleDao);
	}
	
}
