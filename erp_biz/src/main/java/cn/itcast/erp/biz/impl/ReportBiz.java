package cn.itcast.erp.biz.impl;

import java.util.Date;
import java.util.List;

import cn.itcast.erp.biz.IReportBiz;
import cn.itcast.erp.dao.IReportDao;

public class ReportBiz implements IReportBiz {

	private IReportDao reportDao;

	@Override
	public List orsderReport(Date startDate, Date endDate) {
		return reportDao.orsderReport(startDate,endDate);
	}

	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}

	
}
