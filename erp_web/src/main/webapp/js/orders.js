$(function(){
	var url="orders_listByPage?t1.type=1";
	if(Request['oper'] == 'doCheck'){
		url+="&t1.state=0";
	}
	if(Request['oper'] == 'doStart'){
		url +='&t1.state=1';
	}
	
	
	$('#grid').datagrid({
		url:url,
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
		//双击事件
		onDblClickRow: function(rowIndex, rowData){
			$('#ordersDlg').dialog('open');
			$('#uuid').html(rowData.uuid);
			$('#supplierName').html(rowData.supplierName);
			$('#state').html(formatState(rowData.state));
			$('#createrName').html(rowData.createrName);
			$('#checkerName').html(rowData.checkerName);
			$('#starterName').html(rowData.starterName);
			$('#enderName').html(rowData.enderName);
			$('#createtime').html(formatDate(rowData.createtime));
			$('#checktime').html(formatDate(rowData.checktime));
			$('#starttime').html(formatDate(rowData.starttime));
			$('#endtime').html(formatDate(rowData.endtime));
			//加载明细的数据
			$('#itemgrid').datagrid('loadData',rowData.orderdetails);
		}
	
	});
	
	
	$('#ordersDlg').dialog({
		title: '订单明细',//窗口标题
		width: 700,//窗口宽度
		height: 320,//窗口高度
		closed: true,//窗口是是否为关闭状态, true：表示关闭
		modal: true,//模式窗口
		toolbar: [{
			iconCls: 'icon-search',
			handler: doStart
//			handler: doCheck
		}]
	});
	
	
	//订单明细表格
	$('#itemgrid').datagrid({
		columns:[[
			{field:'uuid',title:'编号',width:100},
			{field:'goodsuuid',title:'商品编号',width:100},
			{field:'goodsname',title:'商品名称',width:100},
			{field:'price',title:'价格',width:100},
			{field:'num',title:'数量',width:100},
			{field:'money',title:'金额',width:100},
			{field:'state',title:'状态',width:100,formatter:formatDetailState}
		]],
		singleSelect: true,
		fitColumns:true,
	});

	
	//订单详情窗口的配置
	var orderDlgCfg = {
			title:'订单详情',
			height:320,
			width:700,
			modal:true,
			closed:true
	};
	
	//订单详情窗口的工具栏
	var orderDlgToolbar=new Array();
	//审核按钮
	if(Request['oper'] == 'doCheck'){
		orderDlgToolbar.push({
				text:'审核',
				iconCls:'icon-search',
				handler:doCheck
		});
	}
	
	//确认按钮
	if(Request['oper'] == 'doStart'){
		orderDlgToolbar.push({
				text:'确认',
				iconCls:'icon-search',
				handler:doStart
		});
	}	

	//动态添加详情窗口工具栏
	if(orderDlgToolbar.length>0){
		orderDlgCfg.toolbar=orderDlgToolbar;
	}
	$('#ordersDlg').dialog(orderDlgCfg);
});
/**状态
 * @param value
 * @returns
 */
function formatDetailState(value){
	switch(value * 1){
	case 0:return '未入库';
	case 1:return '已入库';
	default:return '';
	}
}


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
	case 2:return '已确定';
	case 3:return '已入库';
	default:return '';
	}
}

/**
 * 审核
 */
function doCheck(){
	$.messager.confirm("确认","确认要审核吗？",function(yes){
		if(yes){
			$.ajax({
				url:'orders_doCheck',
				data:{id:$('#uuid').html()},
				dataType:'json',
				type:'post',
				success:function(rtn){
					$.messager.alert("提示",rtn.message,'info',function(){
						if(rtn.success){
							$('#ordersDlg').dialog("close");
							$('#grid').datagrid('reload');
						}
					});
				}
			});
		}
	});
}

/**
 * 确认
 */
function doStart(){
	$.messager.confirm("确认","确定要确认吗？",function(yes){
		if(yes){
			$.ajax({
				url:'orders_doStart',
				data:{id:$('#uuid').html()},
				dataType:'json',
				type:'post',
				success:function(rtn){
					$.messager.alert("提示",rtn.message,'info',function(){
						if(rtn.success){
							$('#ordersDlg').dialog("close");
							$('#grid').datagrid('reload');
						}
					});
				}
			});
		}
	});
}