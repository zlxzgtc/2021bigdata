$(function() {
	var myChart = echarts.init(document.getElementById('course-sum-accu'));
	option = {
		title: {
			// text: '折线图堆叠'
		},
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			data: ['各课程做题准确率']
		},
		grid: {
			left: '3%',
			right: '4%',
			bottom: '3%',
			containLabel: true
		},
		toolbox: {
			feature: {
				saveAsImage: {}
			}
		},
		xAxis: {
			type: 'category',
			boundaryGap: false,
			data: ['课程1', '课程2', '课程3', '课程4', '课程5', '课程6','课程7']
		},
		yAxis: {
			type: 'value',
			min: 0,
			max: 100,
			interval: 20,
			axisLabel: {
				formatter: '{value} %'
			}
		},
		series: [{
			name: '准确率',
			type: 'line',
			data: [10, 89, 76, 23, 14, 12, 87]
		}]
	};
	myChart.setOption(option);
});
