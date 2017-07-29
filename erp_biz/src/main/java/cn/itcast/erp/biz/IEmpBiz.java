package cn.itcast.erp.biz;

import java.util.List;

import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Tree;

/**
 * 员工业务逻辑层接口
 * 
 * @author Administrator
 *
 */
public interface IEmpBiz extends IBaseBiz<Emp> {

	/**
	 * 登录查询
	 * 
	 * @param name
	 * @param pwd
	 * @return
	 */
	Emp findByUsernameAndPwd(String username, String pwd);

	// 更改密码
	void updatePwd(String oldPwd, String newPwd, Long uuid);

	// 重置密码
	void updatePwd_reset(String pwd, Long uuid);

	//根据用户id获取用户角色
	List<Tree> readEmpRole(Long uuid);

	//更新用户角色
	void updateEmpRole(Long uuid, String checkedIds);
}
