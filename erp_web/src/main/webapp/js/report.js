$(function(){
	/*表数据*/
	$('#grid').datagrid({
		url:'report_orsderReport',
		singleSelect: true,
		columns:[[
			{field:'name',title:'商品类型',width:100},
			{field:'y',title:'销售额',width:100,editor:{type:'numberbox',options:{
				precision:2,
				prefix:'￥'
            }}},
		]],
		onLoadSuccess:function(_data){
			showChart(_data.rows);
		}
	});
	
	/*查询*/
	$('#btnSearch').bind('click',function(){
		var formdata=$('#searchFrom').serializeJSON();
		if(formdata.endDate!=''){
			formdata.endDate=formdata.endDate+"59:59:59";
		}
		$('#grid').datagrid('load',formdata);
	});
	
});

/*统计图*/
function showChart(_data){
	

$('#pieChart').highcharts({
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie'
		},
		title: {
			text: '销售统计图'
		},
		tooltip: {
			pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions: {
			pie: {
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					format: '<b>{point.name}</b>: {point.percentage:.1f} %',
					style: {
						color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
					}
				}
			}
		},
		series: [{
			colorByPoint: true,
			data:_data
		}]
	});
}