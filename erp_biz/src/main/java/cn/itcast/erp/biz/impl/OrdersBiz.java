package cn.itcast.erp.biz.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.redsun.bos.ws.impl.IWaybillWs;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.biz.exception.ErpException;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;

/**
 * 订单业务逻辑类
 * 
 * @author Administrator
 *
 */
public class OrdersBiz extends BaseBiz<Orders> implements IOrdersBiz {

	/*
	 * 注入bean
	 */
	private IOrdersDao ordersDao;
	private IEmpDao empDao;
	private ISupplierDao supplierDao;
	
	
	

	@SuppressWarnings("static-access")
	@Override // 添加采购订单
	public void add(Orders orders) {
		// 生成日期
		orders.setCreatetime(new Date());
		// 订单状态
		orders.setState(orders.STATE_CREATE);
		// 订单类型
		// orders.setType(orders.TYPE_IN);
		// 合计金额
		double totalMoney = 0;
		for (Orderdetail orderdetail : orders.getOrderdetails()) {
			totalMoney += orderdetail.getMoney();
			orderdetail.setOrders(orders);
			// 设置明细的状态
			orderdetail.setState(Orderdetail.STATE_NOT_IN);
		}
		orders.setTotalmoney(totalMoney);

		ordersDao.add(orders);
	}

	/*
	 * 订单查询
	 */
	public List<Orders> getListByPage(Orders t1, Orders t2, Object param, int firstResult, int maxResults) {
		// 通过父类，获取orders分页
		List<Orders> list = super.getListByPage(t1, t2, param, firstResult, maxResults);
		// emp的名字缓存
		Map<Long, String> empNameMap = new HashMap<Long, String>();
		// supplier的名字缓存
		Map<Long, String> supplierNameMap = new HashMap<Long, String>();
		// 遍历，封装 下单员名称 审核员名称 采购员名称 库管员名称 供应商或客户名称
		for (Orders orders : list) {
			orders.setCreaterName(getEmpName(orders.getCreater(), empNameMap));
			orders.setCheckerName(getEmpName(orders.getChecker(), empNameMap));
			orders.setStarterName(getEmpName(orders.getStarter(), empNameMap));
			orders.setEnderName(getEmpName(orders.getEnder(), empNameMap));
			orders.setSupplierName(getSupplierName(orders.getSupplieruuid(), supplierNameMap));
		}
		return list;
	}

	/*
	 * 通过uuid获取emp的名字
	 */
	private String getEmpName(Long uuid, Map<Long, String> empNameMap) {
		if (null == uuid) {
			return null;
		}
		String empName = empNameMap.get(uuid);
		if (null == empName) {
			empName = empDao.get(uuid).getName();
			empNameMap.put(uuid, empName);
		}
		return empName;
	}

	/*
	 * 通过uuid获取supplier的名字
	 */
	private String getSupplierName(Long uuid, Map<Long, String> supplierNameMap) {
		if (null == uuid) {
			return null;
		}
		String supplierName = supplierNameMap.get(uuid);
		if (null == supplierName) {
			supplierName = supplierDao.get(uuid).getName();
			supplierNameMap.put(uuid, supplierName);
		}
		return supplierName;
	}

	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
		super.setBaseDao(this.ordersDao);
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	/*
	 * 采购订单审核
	 */
	@SuppressWarnings("static-access")
	@Override
	public void doCheck(Long uuid, Long empuuid) {
		// 订单信息
		Orders orders = ordersDao.get(uuid);
		// 是否审核
		if (!orders.getState().equals(orders.STATE_CREATE)) {
			throw new ErpException("已审核");
		}
		// 封装审核员、审核时间、审核状态
		orders.setChecker(empuuid);
		orders.setChecktime(new Date());
		orders.setState(orders.STATE_CHECK);

	}

	/*
	 * 采购订单确认
	 */
	@SuppressWarnings("static-access")
	@Override
	public void doStart(Long uuid, Long empuuid) {
		// 订单信息
		Orders orders = ordersDao.get(uuid);
		// 是否审核
		if (!orders.getState().equals(orders.STATE_CHECK)) {
			throw new ErpException("已确定过了");
		}
		// 封装审核员、审核时间、审核状态
		orders.setStarter(empuuid);
		orders.setStarttime(new Date());
		orders.setState(orders.STATE_START);

	}

	/*
	 * 根据订单编号导出订单
	 */
	@SuppressWarnings("resource")
	@Override
	public void exportById(OutputStream os, Long uuid) throws IOException {
		Orders orders = ordersDao.get(uuid);
		// 创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 判断工作表的名称，采购单/销售单
		String title = "";
		if (Orders.TYPE_OUT.equals(orders.getType())) {
			title = "销 售 单";
		}
		if (Orders.TYPE_IN.equals(orders.getType())) {
			title = "采 购 单";
		}
		// 创建工作表，定义工作表名称
		HSSFSheet sheet = workbook.createSheet(title);
		// 创建行
		HSSFRow row = null;
		// 单元格样式
		HSSFCellStyle style_content = workbook.createCellStyle();
		style_content.setAlignment(HorizontalAlignment.RIGHT);// 水平居中
		style_content.setVerticalAlignment(VerticalAlignment.BOTTOM);// 垂直居中

		HSSFCellStyle style_title = workbook.createCellStyle();
		style_title.cloneStyleFrom(style_content);

		// 边框
		style_content.setBorderBottom(BorderStyle.MEDIUM);
		style_content.setBorderLeft(BorderStyle.MEDIUM);
		style_content.setBorderRight(BorderStyle.MEDIUM);
		style_content.setBorderTop(BorderStyle.MEDIUM);

		// 内容字体
		HSSFFont font_content = workbook.createFont();
		font_content.setFontName("宋体");
		font_content.setFontHeightInPoints((short) 12);
		style_content.setFont(font_content);

		// 标题字体 黑体
		HSSFFont font_title = workbook.createFont();
		font_title.setFontName("黑体");
		font_title.setFontHeightInPoints((short) 18);
		style_title.setFont(font_title);

		// 创建单元格
		HSSFCell cell = null;
		// 标题行
		sheet.createRow(0).createCell(0).setCellStyle(style_title);

		// 明细数量
		int size = orders.getOrderdetails().size();
		// 表格行数
		int rowCnt = 9 + size;
		// 设置行高
		sheet.getRow(0).setHeight((short) 1000);// 第一行行高
		for (int i = 2; i <= rowCnt; i++) {// 第三行开始
			row = sheet.createRow(i);
			row.setHeight((short) 500);// 内容区域每行的行高
			for (int col = 0; col < 4; col++) {
				cell = row.createCell(col);
				// 单元格样式
				cell.setCellStyle(style_content);
			}
		}

		// 列宽
		for (int i = 0; i <= 3; i++) {
			sheet.setColumnWidth(i, 5000);
		}

		// 日期格式
		HSSFDataFormat dFormat = workbook.createDataFormat();
		HSSFCellStyle style_date = workbook.createCellStyle();
		style_date.cloneStyleFrom(style_content);
		style_date.setDataFormat(dFormat.getFormat("yyyy-MM-dd"));
		// 设置日期格式
		for (int i = 3; i <= 6; i++) {
			sheet.getRow(i).getCell(1).setCellStyle(style_date);
		}
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));// 标题
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));// 供应商
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 1, 3));// 订单明细
		sheet.addMergedRegion(new CellRangeAddress(rowCnt, rowCnt, 0, 2));// 合计

		// 设置内容
		// 标题
		sheet.getRow(0).getCell(0).setCellValue(title);
		// 3行
		sheet.getRow(2).getCell(0).setCellValue("供应商");
		sheet.getRow(2).getCell(1).setCellValue(ordersDao.getName(orders.getSupplieruuid()));
		// 4行
		sheet.getRow(3).getCell(0).setCellValue("下单日期");
		setDate(sheet.getRow(3).getCell(1), orders.getCreatetime());
		sheet.getRow(3).getCell(2).setCellValue("经办人");
		sheet.getRow(3).getCell(3).setCellValue(empDao.getName(orders.getCreater()));
		// 5行
		sheet.getRow(4).getCell(0).setCellValue("审核日期");
		setDate(sheet.getRow(4).getCell(1), orders.getChecktime());
		sheet.getRow(4).getCell(2).setCellValue("经办人");
		sheet.getRow(4).getCell(3).setCellValue(empDao.getName(orders.getChecker()));
		// 6行
		sheet.getRow(5).getCell(0).setCellValue("采购日期");
		setDate(sheet.getRow(5).getCell(1), orders.getStarttime());
		sheet.getRow(5).getCell(2).setCellValue("经办人");
		sheet.getRow(5).getCell(3).setCellValue(empDao.getName(orders.getStarter()));
		// 7行
		sheet.getRow(6).getCell(0).setCellValue("入库日期");
		setDate(sheet.getRow(6).getCell(1), orders.getEndtime());
		sheet.getRow(6).getCell(2).setCellValue("经办人");
		sheet.getRow(6).getCell(3).setCellValue(empDao.getName(orders.getEnder()));
		// 8行
		sheet.getRow(7).getCell(0).setCellValue("订单明细");
		// 9行
		sheet.getRow(8).getCell(0).setCellValue("商品名称");
		sheet.getRow(8).getCell(1).setCellValue("数量");
		sheet.getRow(8).getCell(2).setCellValue("价格");
		sheet.getRow(8).getCell(3).setCellValue("金额");

		// 明细内容
		int i = 9;
		for (Orderdetail od : orders.getOrderdetails()) {
			row = sheet.getRow(i);
			row.getCell(0).setCellValue(od.getGoodsname());
			row.getCell(1).setCellValue(od.getNum());
			row.getCell(2).setCellValue(od.getPrice());
			row.getCell(3).setCellValue(od.getMoney());
			i++;
		}

		// 最后一行，合计
		sheet.getRow(rowCnt).getCell(0).setCellValue("合计");
		sheet.getRow(rowCnt).getCell(3).setCellValue(orders.getTotalmoney());

		// 输出
		workbook.write(os);

	}

	/*
	 * 设置日期
	 */
	private void setDate(Cell cell, Date date) {
		if (null != date) {
			cell.setCellValue(date);
		}
	}



}
