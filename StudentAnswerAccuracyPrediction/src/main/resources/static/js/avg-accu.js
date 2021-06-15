$(function() {
	var myChart = echarts.init(document.getElementById('avg-accu'));
	var averageaccu = document.getElementById('averageaccu').getAttribute('value');
	option = {
		tooltip: {
			trigger: 'item'
		},
		legend: {
			top: '5%',
			left: 'center'
		},
		color: ['#4d49bf', '#fffff1'],
		series: [{
			type: 'pie',
			radius: ['40%', '70%'],
			avoidLabelOverlap: false,
			itemStyle: {
				borderRadius: 10,
				borderColor: '#fff',
				borderWidth: 2
			},
			label: {
				show: false,
			},
			labelLine: {
				show: false,
			},
			data: [{
					value: averageaccu,
					name: '平均正确率'
				},
				{
					value: 1-averageaccu,
					name: '',
				},
			]
		}]
	};
	myChart.setOption(option);
});
