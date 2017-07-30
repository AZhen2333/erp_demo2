package cn.itcast.erp.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Menu;

public class ErpRealm extends AuthorizingRealm {
	private IEmpBiz empBiz;
	private IMenuBiz menuBiz;

	/*
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行了授权");
		// 创建授权信息
		SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
		// 获取登录用户
		Emp loginEmp = (Emp) principals.getPrimaryPrincipal();
		/*
		 * //用户权限 sai.addStringPermission("商品");
		 * sai.addStringPermission("采购订单审核");
		 */
		// 获取用户的菜单权限
		List<Menu> menus = menuBiz.getMenuByEmpuuid(loginEmp.getUuid());
		//根据权限关键字授权
		for (Menu menu : menus) {
			sai.addStringPermission(menu.getMenuname());
		}
		return sai;
	}

	/*
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取令牌
		UsernamePasswordToken upt = (UsernamePasswordToken) token;
		Emp emp = empBiz.findByUsernameAndPwd(upt.getUsername(), new String(upt.getPassword()));
		// 返回认证消息
		if (null != emp) {
			return new SimpleAuthenticationInfo(emp, upt.getPassword(), getName());
		}
		return null;

	}

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}

	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
	}

}
