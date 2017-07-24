$(function(){
	$('#grid').datagrid({    
	    url:'storedetail_getStoreAlert',
	    singleSelect:true,
        fitColumns:true,
	    columns:[[    
	        {field:'uuid',title:'商品编号',width:100},    
	        {field:'name',title:'商品名称',width:100},    
	        {field:'storenum',title:'库存数量',width:100},    
	        {field:'outnum',title:'待发货数量',width:100}    
	    ]],
	    toolbar: [{
			text:'发送预警邮件',
			iconCls:'icon-add',
			handler:function(){
				$.ajax({
					url:'storedetail_sendStorealterMail',
					dataType:'json',
					type:'post',
					success:function(rtn){
						$.messager.alert('提示',rtn.message,'info');
					}
				});
			}
		}]

	});  
});