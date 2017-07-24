package cn.itcast.erp.biz.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.biz.exception.ErpException;
import cn.itcast.erp.dao.IGoodsDao;
import cn.itcast.erp.dao.IStoreDao;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.entity.Storealert;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.utils.MailUtil;
import freemarker.template.SimpleDate;

/**
 * 仓库库存业务逻辑类
 * 
 * @author Administrator
 *
 */
public class StoredetailBiz extends BaseBiz<Storedetail> implements IStoredetailBiz {

	private IStoredetailDao storedetailDao;
	private IGoodsDao goodsDao;
	private IStoreDao storeDao;

	/*
	 * private String getGoodsName(Long uuid, Map<Long, String> goodsNameMap) {
	 * if (null == uuid) { return null; } String goodsName =
	 * goodsNameMap.get(uuid); if (null == goodsName) { goodsName =
	 * goodsDao.get(uuid).getName(); goodsNameMap.put(uuid, goodsName); } return
	 * goodsName; }
	 * 
	 * private String getStoreName(Long uuid, Map<Long, String> storeNameMap) {
	 * if (null == uuid) { return null; } String storeName =
	 * storeNameMap.get(uuid); if (null == storeName) { storeName =
	 * storeDao.get(uuid).getName(); storeNameMap.put(uuid, storeName); } return
	 * storeName; }
	 */

	private MailUtil mailUtil;
	private String to;// 收件人
	private String subject;// 邮件主题
	private String text;// 邮件正文

	public List<Storedetail> getListByPage(Storedetail t1, Storedetail t2, Object param, int firstResult,
			int maxResults) {
		List<Storedetail> list = super.getListByPage(t1, t2, param, firstResult, maxResults);
		Map<Long, String> goodsNameMap = new HashMap<Long, String>();
		Map<Long, String> storeNameMap = new HashMap<Long, String>();
		for (Storedetail sd : list) {
			sd.setGoodsName(getName(sd.getGoodsuuid(), goodsNameMap, goodsDao));
			sd.setStoreName(getName(sd.getStoreuuid(), storeNameMap, storeDao));
		}
		return list;
	}

	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
		super.setBaseDao(this.storedetailDao);
	}

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}

	// 库存预警列表
	@Override
	public List<Storealert> getStoreAlertList() {
		return storedetailDao.getStoreAlertList();
	}

	// 发送预警邮件
	@Override
	public void sendStoreAlterMail() throws MessagingException {
		List<Storealert> storeAlertList = storedetailDao.getStoreAlertList();
		if (storeAlertList.size() > 0) {
			// 格式化时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 发送邮件
			mailUtil.sendMail(to, subject.replace("[time]", sdf.format(new Date())),
					text.replace("[count]", storeAlertList.size() + ""));
		} else {
			throw new ErpException("没有需要的预警商品");
		}
	}

	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setText(String text) {
		this.text = text;
	}

}
