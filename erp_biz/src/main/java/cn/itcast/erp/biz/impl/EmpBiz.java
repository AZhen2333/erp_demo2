package cn.itcast.erp.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.biz.exception.ErpException;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;

/**
 * 员工业务逻辑类
 * 
 * @author Administrator
 *
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {

	private IEmpDao empDao;
	private IRoleDao roleDao;

	/*
	 * MD5加密的散列数
	 */
	private int hashIterations = 2;

	/*
	 * 重写添加员工方法
	 */
	@Override
	public void add(Emp emp) {
		// 加密
		emp.setPwd(encrypt(emp.getPwd(), emp.getUsername()));
		empDao.add(emp);
	}

	/*
	 * 根据用户名和密码查询登录
	 */
	@Override
	public Emp findByUsernameAndPwd(String username, String pwd) {
		// 对密码加密
		String newPwd = encrypt(pwd, username);
		return empDao.findByUsernameAndPwd(username, newPwd);
	}

	/*
	 * 登陆后修改密码
	 */
	@Override
	public void updatePwd(String oldPwd, String newPwd, Long uuid) {
		Emp emp = empDao.get(uuid);
		String encryptPwd = encrypt(oldPwd, emp.getUsername());
		// 判断旧密码是否输入正确
		if (!encryptPwd.equals(emp.getPwd())) {
			System.err.println(encryptPwd + emp.getPwd());
			throw new ErpException("原密码错误");
		}
		empDao.updatePwd(encrypt(newPwd, emp.getUsername()), uuid);
	}

	/*
	 * * 重置密码
	 */
	public void updatePwd_reset(String pwd, Long uuid) {
		Emp emp = empDao.get(uuid);
		String newPwd = encrypt(pwd, emp.getUsername());
		empDao.updatePwd(newPwd, uuid);
	}

	/*
	 * 根据用户id获取角色
	 */
	@Override
	public List<Tree> readEmpRole(Long uuid) {
		// 根据id获取用户
		Emp emp = empDao.get(uuid);
		// 获取emp的所有角色
		List<Role> empRoles = emp.getRoles();
		// 获取全部角色
		List<Role> roleList = roleDao.getList(null, null, null);
		// 创建接收tree集合
		List<Tree> treeList = new ArrayList<Tree>();
		Tree tree = null;
		// 遍历全部角色
		for (Role role : roleList) {
			tree = new Tree();
			tree.setId(role.getUuid() + "");
			tree.setText(role.getName());
			if (empRoles.contains(role)) {
				tree.setChecked(true);
			}
			treeList.add(tree);
		}
		return treeList;
	}

	/*
	 * MD5加密方法
	 */
	public String encrypt(String source, String salt) {
		Md5Hash newPwd = new Md5Hash(source, salt, hashIterations);
		System.err.println(newPwd.toString());
		return newPwd.toString();
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
		super.setBaseDao(this.empDao);
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

}
