package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.IMenuBiz;
import cn.redsun.bos.dao.IMenuDao;
import cn.redsun.bos.entity.Menu;
/**
 * 菜单业务逻辑类
 * @author Administrator
 *
 */
public class MenuBiz extends BaseBiz<Menu> implements IMenuBiz {

	private IMenuDao menuDao;
	
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
		super.setBaseDao(this.menuDao);
	}
	
}
