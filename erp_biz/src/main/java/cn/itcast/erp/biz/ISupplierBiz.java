package cn.itcast.erp.biz;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.itcast.erp.entity.Supplier;
/**
 * 供应商业务逻辑层接口
 * @author Administrator
 *
 */
public interface ISupplierBiz extends IBaseBiz<Supplier>{

	/*
	 * 导出文件
	 * */
	void export(OutputStream os,Supplier t1) throws IOException;
	/*
	 * 导入文件
	 * */
	void doImport(InputStream is) throws Exception;
}

