//提交的方法名称
$(function(){
	//加载表格数据
	 $('#grid').datagrid({
	        title: '员工信息列表',
	        url:'emp_listByPage',
	        columns:[[
	                    {checkbox:true},
	                    {field:'uuid',title:'编号',width:100},
	                    {field:'username',title:'系统登陆名称',width:100},
	                    {field:'name',title:'真实姓名',width:100},
	                    {field:'gender',title:'性别',width:100,formatter:function(gender){
	                        switch(gender){
	                            case 0: return '女';
	                            case 1: return '男';
	                            default: return '未知';
	                        }
	                    }},
	                    {field:'email',title:'电子邮箱地址',width:100},
	                    {field:'tele',title:'联系电话',width:100},
	                    {field:'address',title:'联系地址',width:100},
	                    {field:'birthday',title:'出生年月日',width:100,formatter:function(birthday){
	                        if(birthday){
	                            return new Date(birthday).Format("yyyy-MM-dd");
	                        }
	                        return "";
	                    }},
	                    {field:'dep',title:'部门',width:100,formatter:function(dep){
	                        if(dep){
	                            return dep.name;
	                        }
	                        return "";
	                    }},
	                    {field:'-',title:'操作',width:200,formatter:function(value,row,index){
	                          var operation = '<a href="javascript:void(0)" onclick="updatePwd_reset(' + row.uuid + ')">重置密码</a> ';
	                          return operation;
	                    }}
	        ]],

	  singleSelect: true,
      pagination: true,
      rownumbers: true
	 });


	//初始化编辑窗口
	$('#editDlg').dialog({
		title: '重置密码',//窗口标题
		width: 300,//窗口宽度
		height: 150,//窗口高度
		closed: true,//窗口是是否为关闭状态, true：表示关闭
		modal: true,//模式窗口
		buttons:[{
			text:'重置密码',
			iconCls: 'icon-save',
			handler:function(){
				//用记输入的部门信息
				var submitData= $('#editForm').serializeJSON();
				$.ajax({
					url: 'emp_updatePwd_reset',
					data: submitData,
					dataType: 'json',
					type: 'post',
					success:function(rtn){
						//{success:true, message: 操作失败}
						$.messager.alert('提示',rtn.message, 'info',function(){
							if(rtn.success){
								//关闭弹出的窗口
								$('#editDlg').dialog('close');
								//刷新表格
								$('#grid').datagrid('reload');
							}
						});
					}
				});
			}
		},{
			text:'关闭',
			iconCls:'icon-cancel',
			handler:function(){
				//关闭弹出的窗口
				$('#editDlg').dialog('close');
			}
		}]
	});`

});




/**
 * 修改
 */
function updatePwd_reset(uuid){
	//弹出窗口
	$('#editDlg').dialog('open');

	//清空表单内容
	$('#editForm').form('clear');

	$('#uuid').val(uuid);
}