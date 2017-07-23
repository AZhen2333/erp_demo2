package cn.itcast.erp.action;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IReportBiz;
import webutils.WebUtils;

public class ReportAction {

	private IReportBiz reportBiz;
	private Date startDate;
	private Date endDate;
	
	
	@SuppressWarnings("rawtypes")
	public void orsderReport(){
		List ordersList = reportBiz.orsderReport(startDate, endDate);
		WebUtils.write(JSON.toJSONString(ordersList));
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
}
