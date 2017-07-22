$(function(){
	//加载表格数据
	$('#grid').datagrid({
		url:'storedetail_listByPage',
		columns:[[
			{field:'uuid',title:'编号',width:100},
			{field:'storeName',title:'仓库',width:100},
			{field:'goodsName',title:'商品',width:100},
		]],
		singleSelect: true,
		pagination: true,
	});
	
});