package cn.itcast.erp.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IReportBiz {
	@SuppressWarnings("rawtypes")
	public List orsderReport(Date startDate,Date endDate);
	public List<Map<String, Object>> getSumMoney(int year);
}
