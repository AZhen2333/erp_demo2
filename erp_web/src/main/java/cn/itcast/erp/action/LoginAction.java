package cn.itcast.erp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.entity.Emp;

public class LoginAction {

	// bean
	private IEmpBiz empBiz;
	// 属性驱动
	private String username;
	private String pwd;

	public void loginUser() {
		try {
			Emp logerUser = empBiz.findByUsernameAndPwd(username, pwd);
			if (null == logerUser) {
				ajaxReturn(false, "用户名或密码错误");
				return;
			}
			// 放入session
			ActionContext.getContext().getSession().put("logerUser", logerUser);
			ajaxReturn(true, "");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "登录失败");
		}
	}

	public void showUserName() {
		Emp loginUser = (Emp) ActionContext.getContext().getSession().get("logerUser");
		if (null != loginUser) {
			ajaxReturn(true, loginUser.getUsername());
		} else {
			ajaxReturn(false, "");
		}
	}

	public void loginOut() {
		ActionContext.getContext().getSession().remove("logerUser");

	}

	/**
	 * 返回前端操作结果
	 * 
	 * @param success
	 * @param message
	 */
	public void ajaxReturn(boolean success, String message) {
		// 返回前端的JSON数据
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("success", success);
		rtn.put("message", message);
		write(JSON.toJSONString(rtn));
	}

	/**
	 * 输出字符串到前端
	 * 
	 * @param jsonString
	 */
	public void write(String jsonString) {
		try {
			// 响应对象
			HttpServletResponse response = ServletActionContext.getResponse();
			// 设置编码
			response.setContentType("text/html;charset=utf-8");
			// 输出给页面
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public IEmpBiz getEmpBiz() {
		return empBiz;
	}

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
