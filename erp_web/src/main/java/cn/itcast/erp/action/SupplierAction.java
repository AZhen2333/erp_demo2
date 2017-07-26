package cn.itcast.erp.action;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.itcast.erp.biz.ISupplierBiz;
import cn.itcast.erp.entity.Supplier;

/**
 * 供应商Action
 * 
 * @author Administrator
 *
 */
public class SupplierAction extends BaseAction<Supplier> {

	private static final Logger log = LoggerFactory.getLogger(SupplierAction.class);

	private ISupplierBiz supplierBiz;

	private String p;

	// 自动补全
	public void list() {
		if (null == getT1()) {
			setT1(new Supplier());
		}
		getT1().setName(p);
		super.list();
	}

	public void setSupplierBiz(ISupplierBiz supplierBiz) {
		this.supplierBiz = supplierBiz;
		super.setBaseBiz(this.supplierBiz);
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	/*
	 * 导出excel文件
	 */
	public void exprot() {
		String fileName = "供应商";
		// 生成文件名
		if ("2".equals(getT1().getType())) {
			fileName = "客户";
		}
		// 添加格式后缀
		fileName += ".xls";
		// 对传输的中文进行转码，ISO-8859-1
		try {
			fileName = new String(fileName.getBytes(), "ISO-8859-1");
			// 获取响应
			HttpServletResponse response = ServletActionContext.getResponse();
			// 获取输出流
			ServletOutputStream os = response.getOutputStream();
			// 告诉客户端，传输是一个文件
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			// 业务层处理
			supplierBiz.export(os, getT1());
		} catch (Exception e) {
			log.error("导出数据失败", e);
		}

	}
}
