package cn.itcast.erp.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IReportBiz;
import webutils.WebUtils;

public class ReportAction {

	private IReportBiz reportBiz;
	private Date startDate;
	private Date endDate;
	private int year;
	
	/*商品分类统计*/
	@SuppressWarnings("rawtypes")
	public void orsderReport(){
		List ordersList = reportBiz.orsderReport(startDate, endDate);
		WebUtils.write(JSON.toJSONString(ordersList));
	}
	
	/*每年月份分类统计*/
	public void getSumMoney(){
		List<Map<String, Object>> sumMoney = reportBiz.getSumMoney(year);
		WebUtils.write(JSON.toJSONString(sumMoney));
	}
	
	public void setReportBiz(IReportBiz reportBiz) {
		this.reportBiz = reportBiz;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
