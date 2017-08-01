package cn.itcast.erp.biz.impl;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.erp.biz.IRoleBiz;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Menu;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
import redis.clients.jedis.Jedis;

/**
 * 角色业务逻辑类
 * 
 * @author Administrator
 *
 */
public class RoleBiz extends BaseBiz<Role> implements IRoleBiz {

	private IRoleDao roleDao;
	private IMenuDao menuDao;
	private Jedis jedis;

	/*
	 * 通过角色id获取树形结构
	 */
	@Override
	public List<Tree> readRoleMenu(Long uuid) {
		// 获取指定的角色
		Role role = roleDao.get(uuid);
		// 获取角色的menu
		List<Menu> roleMenus = role.getMenus();
		// 获取所有的菜单
		Menu root = menuDao.get("0");// 主菜单（系统菜单）
		// 获取一级菜单
		List<Menu> menu_1 = root.getMenus();
		// 创建树形结构菜单
		List<Tree> treeList = new ArrayList<Tree>();
		Tree tree = null;
		// 遍历一级的菜单
		for (Menu m1 : menu_1) {
			tree = createTree(m1);
			if (null != m1.getMenus() && m1.getMenus().size() > 0) {
				for (Menu m2 : m1.getMenus()) {
					Tree tree2 = createTree(m2);
					if (roleMenus.contains(m2)) {
						tree2.setChecked(true);
					}
					tree.getChildren().add(tree2);
				}
			}
			treeList.add(tree);
		}
		return treeList;
	}
	
	/*
	 * 更新角色权限
	 * */
	@Override
	public void updateRoleMenu(long uuid,String checkedIds){
		//获取指定角色，进入持久化
		Role role = roleDao.get(uuid);
		//清空原菜单
		role.setMenus(new ArrayList<Menu>());
		//
		String[] ids = checkedIds.split(",");
		for (String id : ids) {
			//根据id查询menu，添加到menus中
			role.getMenus().add(menuDao.get(id));
		}
		for(Emp emp:role.getEmpList()){
			jedis.del("menus_"+emp.getUuid());
		}
	}

	public Tree createTree(Menu m) {
		Tree tree = new Tree();
		tree.setId(m.getMenuid());
		tree.setText(m.getMenuname());
		tree.setChildren(new ArrayList<Tree>());
		return tree;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
		super.setBaseDao(this.roleDao);
	}

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

}
