package cn.itcast.erp.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.Data;

import com.redsun.bos.ws.impl.IWaybillWs;

import cn.itcast.erp.biz.IReportBiz;
import cn.itcast.erp.dao.IReportDao;
import cn.itcast.erp.dao.ISupplierDao;

public class ReportBiz implements IReportBiz {

	private IReportDao reportDao;
	

	@SuppressWarnings("rawtypes")
	@Override
	public List orsderReport(Date startDate, Date endDate) {
		return reportDao.orsderReport(startDate, endDate);
	}

	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}

	@Override
	public List<Map<String, Object>> getSumMoney(int year) {
		// 按月分组查询结果（月份不完整）{"name":月份,"y":金额}
		List<Map<String, Object>> sumMoney = reportDao.getSumMoney(year);
		// 新建集合保存最后结果（完整月份）{"name":月份,"y":金额}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// 临时存放，用于判别月份，格式：{月份：{"name":月份,"y":金额}}
		Map<Integer, Map<String, Object>> mapMonthData = new HashMap<Integer, Map<String, Object>>();
		// 遍历dao查询的list
		for (Map<String, Object> map : sumMoney) {
			// 放进mapMonthData中：如:{7：{"name":7,"y":71}}
			mapMonthData.put((Integer) map.get("name"), map);
		}
		// 定义存放结果的map
		Map<String, Object> data = null;
		// 遍历mapMonthData
		for (int i = 1; i <= 12; i++) {
			// 取出每个value的值：map
			data = mapMonthData.get(i);
			if (null == data) {
				// 如果value为null，name为月份，y为0
				data = new HashMap<String, Object>();
				data.put("name", i);
				data.put("y", 0);
				// 放进结果集
				result.add(data);
			} else {
				// 不为空，直接放进结果集
				result.add(data);
			}
		}
		return result;
	}

}
