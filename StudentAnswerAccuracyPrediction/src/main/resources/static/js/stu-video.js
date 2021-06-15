$(function() {
	var myChart = echarts.init(document.getElementById('stu-video'));
	option = {
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'cross',
				crossStyle: {
					color: '#136'
				}
			}
		},
		toolbox: {
			feature: {
				magicType: {
					show: true,
					type: ['line', 'bar']
				},
				saveAsImage: {
					show: true
				}
			}
		},
		legend: {
			data: ['观看时长占比', '观看次数']
		},
		xAxis: [{
			type: 'category',
			data: ['C1', 'C2', 'C3', 'C4', 'C5', 'C6','C7','C8','C9','C10'],
			axisPointer: {
				type: 'shadow'
			}
		}],
		yAxis: [{
				type: 'value',
				name: '观看时长占比',
				min: 0,
				max: 100,
				interval: 20,
				axisLabel: {
					formatter: '{value} %'
				}
			},
			{
				type: 'value',
				name: '观看次数',
				min: 0,
				max: 20,
				interval: 4,
				axisLabel: {
					formatter: '{value}'
				}
			}
		],
		series: [{
				name: '观看时长占比',
				type: 'bar',
				data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0]
			},
			{
				name: '观看次数',
				type: 'line',
				yAxisIndex: 1,
				data: [2.0, 2.0, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5]
			}
		]
	};
	myChart.setOption(option);

});
