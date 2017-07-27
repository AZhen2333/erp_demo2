package cn.redsun.bos.action;
import cn.redsun.bos.biz.IRoleBiz;
import cn.redsun.bos.entity.Role;

/**
 * 角色Action 
 * @author Administrator
 *
 */
public class RoleAction extends BaseAction<Role> {

	private IRoleBiz roleBiz;

	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
		super.setBaseBiz(this.roleBiz);
	}

}
