$(function() {
	var myChart = echarts.init(document.getElementById('stu-pre-accu'));
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
				show: false
			},
			data: [{
					value: 735,
					name: '预测正确率'
				},
				{
					value: 1048,
					name: ''
				},
			]
		}]
	};
	myChart.setOption(option);
});
