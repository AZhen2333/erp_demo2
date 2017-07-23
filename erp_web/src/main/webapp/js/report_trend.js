$(function(){
	$('#grid').datagrid({
		singleSelect: true,
		columns:[[
			{field:'name',title:'月份',width:100},
			{field:'y',title:'销售额',width:100},
            
		]],
		onLoadSuccess:function(_data){
			$.each(_data.rows,function(i,row){
				row.name+='月';
			});
		}
		
	});

	/*查询*/
	$('#btnSearch').bind('click',function(){

		var formData = $('#searchForm').serializeJSON();
		$('#grid').datagrid('load',formData);
	});
		/*默认当前年份*/
		var date=new Date();
		$('#yearCob').combobox('setValue',date.getFullYear());
		$('#grid').datagrid({
			url:"report_getSumMoney",
			queryParams:{year:date.getFullYear()}
	});
	
	
});

/*销售趋势图*/
$(function (_data) {
	var yearMonth=[];
	for(var i=1,i<=12,i++){
		yearMonth.push[i+'月'];
	}
    $('#highCharts').highcharts({
        title: {
            text: '销售趋势图',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: itheima.com',
            x: -20
        },
        xAxis: {categories:yearMonth},
        yAxis: {
            title: {
                text: '销售额 (￥)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '￥'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: '年度销售趋势',
            data:
    });
});