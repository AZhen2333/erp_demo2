package cn.itcast.erp.action;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IRoleBiz;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;

/**
 * 角色Action 
 * @author Administrator
 *
 */
public class RoleAction extends BaseAction<Role> {

	private IRoleBiz roleBiz;
	private String checkedIds;//获取所有id组成的字符串
	
	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
		super.setBaseBiz(this.roleBiz);
	}

	/*
	 * 获取角色菜单
	 * */
	public void readRoleMenu(){
		List<Tree> treeList = roleBiz.readRoleMenu(getId());
		write(JSON.toJSONString(treeList));
	}
	
	/*
	 * 更新角色菜单
	 * */
	public void updateRoleMenu(){
		try {
			roleBiz.updateRoleMenu(getId(),checkedIds);
			ajaxReturn(true, "更新成功");
		} catch (Exception e) {
			ajaxReturn(false, "更新失败");
			e.printStackTrace();
		}
	}

	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}
}
