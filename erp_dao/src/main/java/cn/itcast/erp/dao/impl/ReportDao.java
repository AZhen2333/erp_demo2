package cn.itcast.erp.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.erp.dao.IReportDao;

public class ReportDao extends HibernateDaoSupport implements IReportDao {

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

}
