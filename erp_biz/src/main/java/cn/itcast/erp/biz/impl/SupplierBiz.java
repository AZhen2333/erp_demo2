package cn.itcast.erp.biz.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.itcast.erp.biz.ISupplierBiz;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Supplier;

/**
 * 供应商业务逻辑类
 * 
 * @author Administrator
 *
 */
public class SupplierBiz extends BaseBiz<Supplier> implements ISupplierBiz {

	private ISupplierDao supplierDao;

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
		super.setBaseDao(this.supplierDao);
	}

	/*
	 * 导出供应商或客户资料
	 */
	@Override
	public void export(OutputStream os, Supplier t1) throws IOException {
		// 获取供应商或客户列表
		List<Supplier> list = super.getList(t1, null, null);
		// 创建工作簿
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		// 工作表
		HSSFSheet sheet = null;
		// 根据类型查创建响应名称的工作表
		if ("1".equals(t1.getType())) {
			sheet = hssfWorkbook.createSheet("客户");
		}
		if ("2".equals(t1.getType())) {
			sheet = hssfWorkbook.createSheet("供应商");
		}
		// 写入表头
		HSSFRow row = sheet.createRow(0);
		// 定义每列的列名
		String[] headerNames = { "名称", "地址", "联系人", "电话", "Email" };
		// 遍历，列名赋值
		for (int i = 0; i < headerNames.length; i++) {
			row.createCell(i).setCellValue(headerNames[i]);
		}
		// 写入内容
		int i = 1;
		for (Supplier supplier : list) {
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(supplier.getName());
			row.createCell(1).setCellValue(supplier.getAddress());
			row.createCell(2).setCellValue(supplier.getContact());
			row.createCell(3).setCellValue(supplier.getTele());
			row.createCell(4).setCellValue(supplier.getEmail());
			i++;
		}
		hssfWorkbook.write(os);
		hssfWorkbook.close();
	}

}
