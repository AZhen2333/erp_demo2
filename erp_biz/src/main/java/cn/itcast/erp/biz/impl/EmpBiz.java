package cn.itcast.erp.biz.impl;

import org.apache.shiro.crypto.hash.Md5Hash;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.biz.exception.ErpException;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.entity.Emp;

/**
 * 员工业务逻辑类
 * 
 * @author Administrator
 *
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {

	private IEmpDao empDao;
	
	/*
	 * MD5加密的散列数
	 * */ 
	private int hashIterations = 2;

	/*
	 * 重写添加员工方法
	 * */ 
	@Override 
	public void add(Emp emp) {
		//加密
		emp.setPwd(encrypt(emp.getPwd(), emp.getUsername()));
		empDao.add(emp);
	}

	/*
	 * 根据用户名和密码查询登录
	 * */
	@Override 
	public Emp findByUsernameAndPwd(String username, String pwd) {
		// 对密码加密
		String newPwd = encrypt(pwd, username);
		return empDao.findByUsernameAndPwd(username, newPwd);
	}

	/*
	 * 登陆后修改密码
	 * */
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
		String newPwd = encrypt(pwd,emp.getUsername());
		empDao.updatePwd(newPwd,uuid);
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

}
