$(function(){
	//供应商列表
	$('#grid').datagrid({ 
	    url: 'storeoper_listByPage',    
	    singleSelect:true,
	    pagination:true,
	    columns:[[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'empName',title:'操作员工',width:100},
		  		    {field:'opertime',title:'操作日期',width:100,formatter:date},
		  		    {field:'storeName',title:'仓库',width:100},
		  		    {field:'goodsName',title:'商品',width:100},
		  		    {field:'num',title:'数量',width:100},
		  		    {field:'type',title:'操作类型',width:100,formatter:function(value){
		  		    	if(value * 1 == 1){
		  		    		return "入库";
		  		    	}
		  		    	if(value * 1 == 2){
		  		    		return "出库";
		  		    	}
		  		    }}
					]],
	});  
	
	//点击查询按钮
	$('#btnSearch').bind('click',function(){
		//把表单数据转换成json对象
		var formData = $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formData);
	});
	
	
});

/*
 * 格式化日期
 * */
function date(value){
  	return new Date(value).Format("yyyy-MM-dd");
  }



/*
 * 仓库有类型
 * */
function type(value){
		if(value==1){
			return '入库';
		}
		if(value==2){
			return '出库';
		}
}