package cn.redsun.bos.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.redsun.bos.dao.IEmpDao;
import cn.redsun.bos.entity.Emp;
/**
 * 员工数据访问类
 * @author Administrator
 *
 */
public class EmpDao extends BaseDao<Emp> implements IEmpDao {

	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Emp emp1,Emp emp2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Emp.class);
		if(emp1!=null){
			if(null != emp1.getUsername() && emp1.getUsername().trim().length()>0){
				dc.add(Restrictions.like("username", emp1.getUsername(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getPwd() && emp1.getPwd().trim().length()>0){
				dc.add(Restrictions.like("pwd", emp1.getPwd(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getName() && emp1.getName().trim().length()>0){
				dc.add(Restrictions.like("name", emp1.getName(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getEmail() && emp1.getEmail().trim().length()>0){
				dc.add(Restrictions.like("email", emp1.getEmail(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getTele() && emp1.getTele().trim().length()>0){
				dc.add(Restrictions.like("tele", emp1.getTele(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getAddress() && emp1.getAddress().trim().length()>0){
				dc.add(Restrictions.like("address", emp1.getAddress(), MatchMode.ANYWHERE));
			}

		}
		return dc;
	}

}
