package cn.itcast.erp.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.biz.exception.ErpException;
import cn.itcast.erp.entity.Emp;

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

	// 修改密码
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

	// 重置密码
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
}
