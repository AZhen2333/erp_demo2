
var existEditIndex=-1;
$(function(){
	$('#ordersgrid').datagrid({
        singleSelect:true,
        showFooter:true,
        fitColumns:true,
        columns:[[
            {field:'goodsuuid',title:'商品编号',width:100,editor:{type:'numberbox',options:{
            	disabled:true
            	}
            }},
            {field:'goodsname',title:'商品名称',width:100,editor:{type:'combobox',options:{
            	url:'goods_list',    
                valueField:'name',    
                textField:'name',
                onSelect:function(goods){
                	/**价格判断*/
                	//采购价格
                	var price=goods.inprice;
                	//销售价格
                	if(type==2){
                		price=goods.outprice;
                	}
                	//获取商品编号的编辑器
                	var goodsuuidEditor= getEditor('goodsuuid');
                	$(goodsuuidEditor.target).val(goods.uuid);
                	//获取商品价格的编辑器
                	var priceEditor= getEditor('price');
                	$(priceEditor.target).val(price);
                	
                	var numEditor=getEditor('num');
                	$(numEditor.target).select();
                	//对数量列绑定键盘事件
                	bindGridEvent();
                	cal();
                	sum();
                }
     
            	}
            }},
            {field:'price',title:'价格',width:100,editor:'numberbox',editor:{type:'numberbox',options:{
            	disabled:true,
            	precision:2,
            	}
        	}},
            {field:'num',title:'数量',width:100,editor:'numberbox'},
            {field:'money',title:'金额',width:100,editor:'numberbox',editor:{type:'numberbox',options:{
            	 min:0,    
				 precision:2,
				 disabled:true
            	}
        	}},
        	{field:'-',title:'操作',width:200,formatter: function(value,row,index){
				if(row.num == '合计'){
					return '';
				}
				return '<a href="javascript:void(0)" onclick="deleteRow(' + index + ')">删除</a>';
			}}
        ]],
        toolbar: [{
			text: '新增',
			iconCls: 'icon-add',
			handler: function(){
				if(existEditIndex>-1){
					//点击新增行后，就结束编辑非新增行
					$('#ordersgrid').datagrid('endEdit',existEditIndex);
				}
				//新增行
				$('#ordersgrid').datagrid('appendRow',{
					num:0,
					money:0,
				}),
				
				//计算最新行的下表，下边0开始，最大行数-1
				existEditIndex=$('#ordersgrid').datagrid('getRows').length-1;
				//开启编辑最新的一行
				$('#ordersgrid').datagrid('beginEdit',existEditIndex);
				//单击编辑当前行
				$('#ordersgrid').datagrid({onClickRow:function(rowIndex, rowData){
						$('#ordersgrid').datagrid('endEdit',existEditIndex);
						existEditIndex=rowIndex;
						$('#ordersgrid').datagrid('beginEdit',existEditIndex);
						//对数量列绑定键盘事件
						bindGridEvent();
					}
				});
				
				
			}
		},{
			text: '保存',
			iconCls: 'icon-save',
			handler: function(){
				if(existEditIndex>-1){
					$('#ordersgrid').datagrid('endEdit',existEditIndex);
				}
				//数据转json格式
				var submitData= $('#orderForm').serializeJSON();
				if((submitData['t.supplieruuid'])==""){
					$.mesager.alter('提示',"请选择供应商",info);
					return;
				}
				var rows=$('#ordersgrid').datagrid('getRows')
				//将商品转json
				submitData.json=JSON.stringify(rows);
				alert(JSON.stringify($('#ordersgrid').datagrid("getData")));
				$.ajax({
					url: 'orders_add?t.type='+type,
					data:submitData,
					dataType: 'json',
					type: 'post',
					success:function(rtn){
						$.messager.alert("提示",rtn.message,'info',function(){
							//清除供应商数据
							$('#supplier').combogrid('clear');
							//清除商品数据
							$('#ordersgrid').datagrid('loadData',{total:0,rows:[],footer:[{num: '合计',money: 0}]});
							//关闭采购申请窗口
							$('#addOrdersDlg').dialog('close');
							$('#grid').datagrid('reload');
						});
					}
				});
				
				
			}
		}],
	
	});
	
	//加载行脚数据
	$('#ordersgrid').datagrid('reloadFooter',[{
		num: '合计',
		money: 0
	}]);
	
	//供应商列表
	$('#supplier').combogrid({ 
		panelWidth:750,  
	    url: 'supplier_list?t1.type='+type,    
	    idField: 'uuid',    
	    textField: 'name',
	    fitColumns:true,
	    columns: [[    
			{field:'uuid',title:'编号',width:100},
			{field:'name',title:'名称',width:100},
			{field:'address',title:'联系地址',width:100},
			{field:'contact',title:'联系人',width:100},
			{field:'tele',title:'联系电话',width:100},
			{field:'email',title:'邮件地址',width:100},
	    ]]    
	});  
	
	
});


/*
 * 指定编辑器
 * */
function getEditor(field){
	return $('#ordersgrid').datagrid('getEditor', {index:existEditIndex,field:field});
}


/*
 * 绑定表格事件
 * */
function bindGridEvent(){
	var numEditor=getEditor('num');
	$(numEditor.target).bind('keyup',function(){
		cal();
		sum();
	});
}

/*
 * 合计
 * */
function sum(){
	var totalMoney=0;
	var rows= $('#ordersgrid').datagrid('getRows');
	$.each(rows,function(i,row){
		totalMoney+=parseFloat(row.money);
	});
	totalMoney=totalMoney.toFixed(2);
	$('#ordersgrid').datagrid('reloadFooter',[{num:'合计',money:totalMoney}]);
}


/*
 * 计算金额
 * */
function cal(){
	/* 数量，获取==>1.获取数量编辑器  2.取值*/
	var numEditor= getEditor('num');
	var num=$(numEditor.target).val();
	/* 价格，获取==>1.获取价格编辑器  2.取值*/
	var priceEditor=getEditor('price');
	var price=$(priceEditor.target).val();
	//金额，赋值
	//金额编辑器
	var moneyEditor=getEditor('money');
	//计算金额
	var money=(num*1)*(price*1);
	money=money.toFixed(2);
	$(moneyEditor.target).val(money);
	//把金额更新到grid里面的数据源里去
	$('#ordersgrid').datagrid('getRows')[existEditIndex].money = money;
}

/*
 * 删除行
 * */
 function deleteRow(index){
	 //关闭编辑
	 $('#ordersgrid').datagrid('deleteRow',index);
	 //获取页面数据
	 var data=$('#ordersgrid').datagrid('getData');
	 //重新加载本地数据
	 $('#ordersgrid').datagrid('loadData',data);
	 sum();
 }
