$(function() {
	var myChart = echarts.init(document.getElementById('stu-sum-accu'));

	option = {
    title: {
        // text: '答题正确率'
    },
    legend: {
        data: ['正确率']
    },
    radar: {
        // shape: 'circle',
        indicator: [
            { name: '课程1', max: 100},
            { name: '课程2', max: 100},
            { name: '课程3', max: 100},
            { name: '课程4', max: 100},
            { name: '课程5', max: 100},
            { name: '课程6', max: 100}
        ]
    },
    series: [{
        type: 'radar',
        data: [
            {
                value: [12, 87, 90, 34, 45, 12],
                name: '正确率'
            }
        ]
    }]
};
	myChart.setOption(option);
});
