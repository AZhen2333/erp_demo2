
var existEditIndex=-1;
$(function(){
	$('#grid').datagrid({
        singleSelect:true,
        showFooter:true,
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
                	//获取商品编号的编辑器
                	var goodsuuidEditor= getEditor('goodsuuid');
                	$(goodsuuidEditor.target).val(goods.uuid);
                	//获取商品价格的编辑器
                	var priceEditor= getEditor('price');
                	$(priceEditor.target).val(goods.inprice);
                	
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
					$('#grid').datagrid('endEdit',existEditIndex);
				}
				//新增行
				$('#grid').datagrid('appendRow',{
					num:0,
					money:0,
				}),
				
				//计算最新行的下表，下边0开始，最大行数-1
				existEditIndex=$('#grid').datagrid('getRows').length-1;
				//开启编辑最新的一行
				$('#grid').datagrid('beginEdit',existEditIndex);
				//单击编辑当前行
				$('#grid').datagrid({onClickRow:function(rowIndex, rowData){
						$('#grid').datagrid('endEdit',existEditIndex);
						existEditIndex=rowIndex;
						$('#grid').datagrid('beginEdit',existEditIndex);
						//对数量列绑定键盘事件
						bindGridEvent();
					}
				});
				
				
			}
		},{
			text: '保存',
			iconCls: 'icon-save',
			handler: function(){}
		}],
	
	});
	
	//加载行脚数据
	$('#grid').datagrid('reloadFooter',[{
		num: '合计',
		money: 0
	}]);
	
	
});


/*
 * 指定编辑器
 * */
function getEditor(field){
	return $('#grid').datagrid('getEditor', {index:existEditIndex,field:field});
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
	var rows= $('#grid').datagrid('getRows');
	$.each(rows,function(i,row){
		totalMoney+=parseFloat(row.money);
	});
	totalMoney=totalMoney.toFixed(2);
	$('#grid').datagrid('reloadFooter',[{num:'合计',money:totalMoney}]);
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
	$('#grid').datagrid('getRows')[existEditIndex].money = money;
}

/*
 * 删除行
 * */
 function deleteRow(index){
	 //关闭编辑
	 $('#grid').datagrid('deleteRow',index);
	 //获取页面数据
	 var data=$('#grid').datagrid('getData');
	 //重新加载本地数据
	 $('#grid').datagrid('loadData',data);
	 sum();
 }
