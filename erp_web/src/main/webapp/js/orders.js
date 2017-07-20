$(function(){
	$('#grid').datagrid({
		url:'orders_listByPage?t1.type=1',
		columns:[[
			{field:'uuid',title:'编号',width:100},
			{field:'createtime',title:'生成日期',width:100,formatter:formatDate},
			{field:'checktime',title:'审核日期',width:100,formatter:formatDate},
			{field:'starttime',title:'确认日期',width:100,formatter:formatDate},
			{field:'endtime',title:'入库或出库日期',width:100,formatter:formatDate},
			{field:'createrName',title:'下单员',width:100},
			{field:'checkerName',title:'审核员',width:100},
			{field:'starterName',title:'采购员',width:100},
			{field:'enderName',title:'库管员',width:100},
			{field:'supplierName',title:'供应商或客户',width:100},
			{field:'totalmoney',title:'合计金额',width:100},
			{field:'state',title:'采购状态',width:100,formatter:formatState},
			{field:'waybillsn',title:'运单号',width:100},       
		]],
		singleSelect: true,
		pagination: true,
//		singleSelect:true,
		fitColumns:true
		
	});
	
})

/**/
/**格式化时间
 * @param value
 * @returns
 */
function formatDate(value){
	return new Date(value).Format('yyyy-MM-dd');
}

/**/
/**格式化订单状态
 * @param value
 */
function formatState(value){
	//采购: 0:未审核 1:已审核, 2:已确认, 3:已入库
	switch(value*1){
	case 0:return '未审核';
	case 1:return '已审核';
	case 2:return '已审核';
	case 3:return '已入库';
	default:return '';
	}
}
	
