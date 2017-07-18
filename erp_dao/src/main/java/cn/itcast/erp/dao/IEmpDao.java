package cn.itcast.erp.dao;

import cn.itcast.erp.entity.Emp;

/**
 * 员工数据访问接口
 * 
 * @author Administrator
 *
 */
public interface IEmpDao extends IBaseDao<Emp> {
	//根据用户名和密码登录
	Emp findByUsernameAndPwd(String username, String pwd);

	//更改用户密码
	void updatePwd(String pwd, Long uuid);
	
}
