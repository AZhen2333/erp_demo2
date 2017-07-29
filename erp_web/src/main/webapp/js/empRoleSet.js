$(function(){
	$('#grid').datagrid({
		url:'emp_list',
		columns:[[
		     {field:'uuid',title:'编号',width:100},
		     {field:'name',title:'名称',width:100}
		 ]],
		 singleSelect:true,
		 onClickRow:function(roeIndex,rowData){
			 $('#tree').tree({    
				    url: 'emp_readEmpRole?id='+rowData.uuid,    
				    checkbox:true,
				    animate:true
				});
		 }
	});
	$('#butSave').bind('click',function(){
		//获取所有节点的值
		var nodes=$('#tree').tree('getChecked');
		var ids=[];
		//遍历选中的节点，添加进数组
		$.each(nodes,function(i,node){
			ids.push(node.id);
		});
		//判断是否有选择用户
		var role=$('#grid').datagrid('getSelected');
		if(null==role){
			$.messager.alert("提示","请选择用户",'info');
		}
		$.ajax({
			url:'emp_updateEmpRole',
			dataType:'json',
			type:'post',
			data:{id:role.uuid,checkedIds:ids.toString()},
			success:function(rtn){
					$.messager.alert("提示",rtn.message,'info');
			}
		});
	});
});