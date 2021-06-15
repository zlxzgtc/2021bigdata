$(function() {
	var myChart = echarts.init(document.getElementById('quiz-accu'));
	var quizcountlist = document.getElementById('quizcountlist').getAttribute('value');
	quizcountlist = quizcountlist.slice(1, -1);
	quizcountlist = quizcountlist.split(",");
	var quizacculist = document.getElementById('quizacculist').getAttribute('value');
	quizacculist = quizacculist.slice(1, -1);
	quizacculist = quizacculist.split(",");
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
		xAxis: {
			type: 'category',
			name: '做题数',
			data: quizcountlist,
		},
		yAxis: {
			type: 'value',
			name: '正确率',
			min: 0,
			max: 100,
			axisLabel: {
				formatter: '{value} %'
			}
		},
		series: [{
			name: "正确率",
			data: quizacculist,
			type: 'line'
		}]
	};
	myChart.setOption(option);
});
