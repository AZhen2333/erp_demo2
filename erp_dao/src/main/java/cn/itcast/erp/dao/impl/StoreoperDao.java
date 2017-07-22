package cn.itcast.erp.dao.impl;

import java.util.Calendar;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IStoreoperDao;
import cn.itcast.erp.entity.Storeoper;

/**
 * 仓库操作记录数据访问类
 * 
 * @author Administrator
 *
 */
public class StoreoperDao extends BaseDao<Storeoper> implements IStoreoperDao {

	/**
	 * 构建查询条件
	 * 
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	@SuppressWarnings("static-access")
	public DetachedCriteria getDetachedCriteria(Storeoper storeoper1, Storeoper storeoper2, Object param) {
		DetachedCriteria dc = DetachedCriteria.forClass(Storeoper.class);
		if (storeoper1 != null) {
			if (null != storeoper1.getType() && storeoper1.getType().trim().length() > 0) {
				dc.add(Restrictions.eq("type", storeoper1.getType()));
			}
			if (null != storeoper1.getEmpuuid()) {
				dc.add(Restrictions.eq("empuuid", storeoper1.getEmpuuid()));
			}
			if (null != storeoper1.getStoreuuid()) {
				dc.add(Restrictions.eq("storeuuid", storeoper1.getStoreuuid()));
			}
			if (null != storeoper1.getGoodsuuid()) {
				dc.add(Restrictions.eq("goodsuuid", storeoper1.getGoodsuuid()));
			}
			if (null != storeoper1.getOpertime()) {
				dc.add(Restrictions.ge("opertime", storeoper1.getOpertime()));
			}
			if (null != storeoper2) {
				if (null != storeoper2.getOpertime()) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(storeoper2.getOpertime());
					calendar.set(calendar.HOUR, 59);
					calendar.set(calendar.MINUTE, 59);
					calendar.set(calendar.SECOND, 59);
					dc.add(Restrictions.le("opertime", storeoper2.getOpertime()));
				}
			}

		}
		return dc;
	}

}
