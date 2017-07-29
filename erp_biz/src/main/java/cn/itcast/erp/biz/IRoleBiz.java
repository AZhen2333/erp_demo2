package cn.itcast.erp.biz;
import java.util.List;

import cn.itcast.erp.entity.Role;
/**
 * 角色业务逻辑层接口
 * @author Administrator
 *
 */
import cn.itcast.erp.entity.Tree;
public interface IRoleBiz extends IBaseBiz<Role>{
	/*
	 * 通过角色id获取树形菜单
	 * */
	List<Tree> readRoleMenu(Long uuid);

	void updateRoleMenu(long id, String checkedIds);

}

