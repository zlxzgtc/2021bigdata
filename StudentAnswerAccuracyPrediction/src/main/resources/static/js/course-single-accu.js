$(window.onload = function() {
	var myChart = echarts.init(document.getElementById('course-single-accu'));
	// var courselist = document.getElementById('courselist').getAttribute('data');
	var courselist = [[${courselist}]];
	var courseacculist = document.getElementById('courseacculist').getAttribute('data');
	option = {
		xAxis: {
			type: 'category',
			// data: ['课程1', '课程2', '课程3', '课程4', '课程5', '课程6']
			data: courselist,
		},
		yAxis: {
			type: 'value'
		},
		series: [{
			// data: [80, 100, 60, 40, 70, 90],
			data: courseacculist,
			type: 'bar',
			showBackground: true,
			backgroundStyle: {
				color: 'rgba(180, 180, 180, 0.2)'
			}
		}]
	};
	myChart.setOption(option);
	alert(courselist);
});

// var chartOutChar = null;
// var myChart = echarts.init(document.getElementById('course-single-accu'));
// var courselist = document.getElementById('courselist');
// var courseacculist = document.getElementById('courseacculist');
// var option = {
// 	xAxis: {
// 		type: 'category',
// 		data: ['课程1', '课程2', '课程3', '课程4', '课程5', '课程6']
// 	},
// 	yAxis: {
// 		type: 'value'
// 	},
// 	series: [{
// 		data: [80, 100, 60, 40, 70, 90],
// 		type: 'bar',
// 		showBackground: true,
// 		backgroundStyle: {
// 			color: 'rgba(180, 180, 180, 0.2)'
// 		}
// 	}]
// };
// 
// function loadChartOut() {
// 	$.getJSON('/student/index', function(data) {
// 		if (data.success1) {
// 			chartOutChar.showLoading({
// 				text: '正在努力的读取数据中...'
// 			});
// 			chartOutChar.setOption({
// 				xAxis: {
// 					type: 'category',
// 					data: courselist,
// 				},
// 				yAxis: {
// 					type: 'value'
// 				},
// 				series: [{
// 					data: courseacculist,
// 					type: 'bar',
// 					showBackground: true,
// 					backgroundStyle: {
// 						color: 'rgba(180, 180, 180, 0.2)'
// 					}
// 				}]
// 			});
// 			chartOutChar.hideLoading();
// 		} else {
// 			alert('提示', data.msg);
// 		}
// 	});
// }
// 
// $(function() {
// 	chartOutChar = echarts.init(document.getElementById('course-single-accu'));
// 	chartOutChar.setOption(option);
// 	loadChartOut();
// 	window.addEventListener('resize', function() {
// 		chartOutChar.resize();
// 		mychart.resize();
// 	});
// });
// 