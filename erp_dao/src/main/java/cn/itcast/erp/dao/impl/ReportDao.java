package cn.itcast.erp.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.erp.dao.IReportDao;

public class ReportDao extends HibernateDaoSupport implements IReportDao {

	/* 按日期和商品类型统计查找 */
	@SuppressWarnings("rawtypes")
	public List orsderReport(Date startDate, Date endDate) {
		String str = "select new map(gt.name as name,sum(od.money)as y)"
				+ "from Goods g,Goodstype gt,Orders o,Orderdetail od "
				+ "where g.goodstype=gt and od.orders=o and od.goodsuuid=g.uuid " + "and o.type='2' ";
		StringBuffer stringBuffer = new StringBuffer(str);
		List<Date> list = new ArrayList<Date>();
		if (null != startDate) {
			stringBuffer.append("and createtime >= ? ");
			list.add(startDate);
		}
		if (null != endDate) {
			stringBuffer.append("and createtime <= ? ");
			list.add(endDate);
		}
		stringBuffer.append(" group by gt.name");
		String hql = stringBuffer.toString();
		if (list.size() > 0) {
			return getHibernateTemplate().find(hql, list.toArray(new Object[] {}));
		}
		return getHibernateTemplate().find(hql);
	}

	/* 按年份统计查找每个月销售额 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getSumMoney(int year) {
		String hql = "select new map(month(o.createtime) as name,sum(od.money) as y) "
				+ "from Orders o,Orderdetail od "
				+ "where od.orders=o " 
				+ "and o.type='2' and year(o.createtime)=? " 
				+ "group by month(o.createtime)";
		return (List<Map<String, Object>>) this.getHibernateTemplate().find(hql, year);
	}

}
