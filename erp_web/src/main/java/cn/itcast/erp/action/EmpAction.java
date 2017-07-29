package cn.itcast.erp.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.biz.exception.ErpException;
import cn.itcast.erp.dao.impl.EmpDao;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Tree;

/**
 * 员工Action
 * 
 * @author Administrator
 *
 */
public class EmpAction extends BaseAction<Emp> {
	private static final Logger log = LoggerFactory.getLogger(EmpAction.class);
	private IEmpBiz empBiz;
	private String oldPwd;
	private String newPwd;
	private String checkedIds;

	/*
	 * 修改密码
	 * */
	public void updatePwd() {
		Emp emp = getLoginUser();
		try {
			empBiz.updatePwd(oldPwd, newPwd, emp.getUuid());
			ajaxReturn(true, "修改密码成功");
		} catch (ErpException e) {
			log.error("修改密码失败", e);
			ajaxReturn(false, e.getMessage());
		} catch (Exception e) {
			log.error("修改密码失败", e);
			ajaxReturn(false, "修改失败");
		}
	}

	/*
	 * 重置密码
	 * */
	public void updatePwd_reset() {
		try {
			empBiz.updatePwd_reset(newPwd, getId());
			ajaxReturn(true, "重置密码成功");
		} catch (ErpException e) {
			log.error("修改密码失败", e);
			ajaxReturn(false, e.getMessage());
		} catch (Exception e) {
			log.error("重置密码失败", e);
			ajaxReturn(false, "重置密码失败");
		}
	}

	/*
	 * 根据用户id获取用户角色
	 * */
	public void readEmpRole(){
		List<Tree> empRole = empBiz.readEmpRole(getId());
		write(JSON.toJSONString(empRole));
	}
	
	public void updateEmpRole(){
		try {
			empBiz.updateEmpRole(getId(), checkedIds);
			ajaxReturn(true, "更新用户角色成功");
		} catch (Exception e) {
			ajaxReturn(false, "更新用户角色失败");
			e.printStackTrace();
		}
	}
	
	
	
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
		super.setBaseBiz(this.empBiz);
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}
}
