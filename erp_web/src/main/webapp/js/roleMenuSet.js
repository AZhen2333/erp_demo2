$(function(){
	$('#grid').datagrid({
		url:'role_list',
		columns:[[
		     {field:'uuid',title:'编号',width:100},
		     {field:'name',title:'名称',width:100}
		 ]],
		 singleSelect:true,
		 onClickRow:function(rowIndex,rowData){
			 $('#tree').tree({    
				    url: 'role_readRoleMenu?id='+rowData.uuid,
				    animate:true,
				    checkbox:true
				});  
		 }
	});
	$('#btnSave').bind('click',function(){
		var nodes=$('#tree').tree('getChecked');
		var ids=[];
		//遍历选中选项，获得节点id放入数组
		$.each(nodes,function(i,node){
			ids.push(node.id);
		});
		
		var role=$('#grid').datagrid('getSelected');
		if(role==null){
			$.messager.alert("提示","请选择角色",'info');
		}
		$.ajax({
			url:'role_updateRoleMenu',
			dataType:'json',
			type:'post',
			data:{id:role.uuid,checkedIds:ids.toString()},
			success:function(rtn){
					$.messager.alert("提示",rtn.message,'info');
			}
		});
	});
	 
	
});