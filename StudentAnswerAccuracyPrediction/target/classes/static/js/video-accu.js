$(function() {
	var myChart = echarts.init(document.getElementById('video-accu'));
	var courseidlist = document.getElementById('courseidlist').getAttribute('value');
	courseidlist = courseidlist.slice(1, -1);
	courseidlist = courseidlist.split(",");
	var videolengthlist = document.getElementById('videolengthlist').getAttribute('value');
	videolengthlist = videolengthlist.slice(1, -1);
	videolengthlist = videolengthlist.split(",");
	var courseacculist = document.getElementById('courseacculist').getAttribute('value');
	courseacculist = courseacculist.slice(1, -1);
	courseacculist = courseacculist.split(",");
	option = {
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'cross',
				crossStyle: {
					color: '#999'
				}
			}
		},
		toolbox: {
			feature: {
				dataView: {
					show: true,
					readOnly: false
				},
				magicType: {
					show: true,
					type: ['line', 'bar']
				},
				restore: {
					show: true
				},
				saveAsImage: {
					show: true
				}
			}
		},
		legend: {
			data: ['观看时长', '正确率']
		},
		xAxis: [{
			type: 'category',
			data: courseidlist,
			axisPointer: {
				type: 'shadow'
			}
		}],
		yAxis: [{
				type: 'value',
				name: '观看时长',
				// min: 0,
				// max: 250,
				axisLabel: {
					formatter: '{value} h'
				}
			},
			{
				type: 'value',
				name: '正确率',
				min: 0,
				max: 1
			}
		],
		series: [{
				name: '观看时长',
				type: 'bar',
				data: videolengthlist
			},
			{
				name: '正确率',
				type: 'line',
				yAxisIndex: 1,
				data: courseacculist
			}
		]
	};
	myChart.setOption(option);
});
