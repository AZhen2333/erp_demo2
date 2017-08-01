package cn.itcast.erp.biz.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.entity.Menu;
import redis.clients.jedis.Jedis;

/**
 * 菜单业务逻辑类
 * 
 * @author Administrator
 *
 */
public class MenuBiz extends BaseBiz<Menu> implements IMenuBiz {

	private IMenuDao menuDao;
	private Jedis jedis;//注入jedis

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
		super.setBaseDao(this.menuDao);
	}

	/*
	 * 根据empuuid查询菜单
	 */
	@Override
	public Menu readMenuByEmpuuid(Long empuuid) {
		// 获取员工下菜单
		List<Menu> empMenu = getMenuByEmpuuid(empuuid);
		// 所有菜单
		Menu menus = menuDao.get("0");
		// 克隆所有菜单，子菜单为空,用于接收结果
		Menu cloneMenu = cloneMenu(menus);
		// emp一级菜单
		Menu empMenu_1 = null;
		// emp二级菜单
		Menu empMenu_2 = null;
		// 遍历所有一级菜单
		for (Menu menu_1 : menus.getMenus()) {
			// 克隆一级菜单，emp
			empMenu_1 = cloneMenu(menu_1);
			// 遍历二级菜单
			for (Menu menu_2 : menu_1.getMenus()) {
				//如果二级菜单在emp菜单内
				if (empMenu.contains(menu_2)) {
					// 克隆二级菜单，emp
					empMenu_2 = cloneMenu(menu_2);
					//放入emp一级菜单
					empMenu_1.getMenus().add(empMenu_2);
				}
			}
			if (empMenu_1.getMenus().size() > 0) {
				cloneMenu.getMenus().add(empMenu_1);
			}
		}
		return cloneMenu;
	}
	
	/*
	 * 获取用户菜单
	 * */
	@Override
	public List<Menu> getMenuByEmpuuid(Long empuuid) {
		//从jedis缓存中获取菜单
		String menusJedis= jedis.get("menus_"+empuuid);
		List<Menu> menus=null;
		//如果空，数据库获取菜单，转json格式后放入jedis
		if(null==menusJedis){
			menus=menuDao.getMenuByEmpuuid(empuuid);
			jedis.set("menus_"+empuuid,JSON.toJSONString(menus));
		}else{
			//非空，反转成集合
			menus = JSON.parseArray(menusJedis,Menu.class);
		}
		return menus;
	}
	

	/*
	 * 克隆
	 */
	public Menu cloneMenu(Menu m) {
		Menu menu = new Menu();
		menu.setIcon(m.getIcon());
		menu.setMenuid(m.getMenuid());
		menu.setMenuname(m.getMenuname());
		menu.setUrl(m.getUrl());
		menu.setMenus(new ArrayList<Menu>());
		return menu;
	}

	

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}
}
