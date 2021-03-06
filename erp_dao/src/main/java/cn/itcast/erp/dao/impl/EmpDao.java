package cn.itcast.erp.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.entity.Emp;

/**
 * 员工数据访问类
 * 
 * @author Administrator
 *
 */
public class EmpDao extends BaseDao<Emp> implements IEmpDao {

	/**
	 * 构建查询条件
	 * 
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Emp emp1, Emp emp2, Object param) {
		DetachedCriteria dc = DetachedCriteria.forClass(Emp.class);
		if (emp1 != null) {
			if (null != emp1.getUsername() && emp1.getUsername().trim().length() > 0) {
				dc.add(Restrictions.like("username", emp1.getUsername(), MatchMode.ANYWHERE));
			}
			if (null != emp1.getPwd() && emp1.getPwd().trim().length() > 0) {
				dc.add(Restrictions.like("pwd", emp1.getPwd(), MatchMode.ANYWHERE));
			}
			if (null != emp1.getName() && emp1.getName().trim().length() > 0) {
				dc.add(Restrictions.like("name", emp1.getName(), MatchMode.ANYWHERE));
			}
			if (null != emp1.getEmail() && emp1.getEmail().trim().length() > 0) {
				dc.add(Restrictions.like("email", emp1.getEmail(), MatchMode.ANYWHERE));
			}
			if (null != emp1.getTele() && emp1.getTele().trim().length() > 0) {
				dc.add(Restrictions.like("tele", emp1.getTele(), MatchMode.ANYWHERE));
			}
			if (null != emp1.getAddress() && emp1.getAddress().trim().length() > 0) {
				dc.add(Restrictions.like("address", emp1.getAddress(), MatchMode.ANYWHERE));
			}
			// 性别查询
			if (null != emp1.getGender()) {
				dc.add(Restrictions.eq("gender", emp1.getGender()));
			}
			if (null != emp1.getBirthday()) {
				dc.add(Restrictions.ge("birthday", emp1.getBirthday()));
			}
			// 小于或等于结束日期
			if (null != emp2) {
				if (null != emp2.getBirthday()) {
					dc.add(Restrictions.le("birthday", emp2.getBirthday()));
				}
			}
			if (null != emp1.getDep() && null != emp1.getDep().getUuid()) {
				dc.add(Restrictions.eq("dep", emp1.getDep()));
			}

		}
		return dc;
	}

	/*
	 * 登录，查询用户
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Emp findByUsernameAndPwd(String username, String pwd) {
		List<Emp> list = (List<Emp>) this.getHibernateTemplate().find("from Emp where username=? and pwd=?", username,
				pwd);
		return list.isEmpty() ? null : list.get(0);
	}

	/*
	 * 修改指定用户密码
	 */
	public void updatePwd(String pwd, Long uuid) {
		String hql = "update Emp set pwd=? where uuid=?";
		this.getHibernateTemplate().bulkUpdate(hql, pwd, uuid);
	}
	
	
	
}
