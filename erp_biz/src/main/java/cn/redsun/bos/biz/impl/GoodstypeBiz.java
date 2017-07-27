package cn.redsun.bos.biz.impl;
import cn.redsun.bos.biz.IGoodstypeBiz;
import cn.redsun.bos.dao.IGoodstypeDao;
import cn.redsun.bos.entity.Goodstype;
/**
 * 商品分类业务逻辑类
 * @author Administrator
 *
 */
public class GoodstypeBiz extends BaseBiz<Goodstype> implements IGoodstypeBiz {

	private IGoodstypeDao goodstypeDao;
	
	public void setGoodstypeDao(IGoodstypeDao goodstypeDao) {
		this.goodstypeDao = goodstypeDao;
		super.setBaseDao(this.goodstypeDao);
	}
	
}
