$(function() {
	var myChart = echarts.init(document.getElementById('knowledge'));
	var knowledgelist = document.getElementById('knowledgelist').getAttribute('value');
	knowledgelist = eval(knowledgelist);
	
	var catemap = catemap = {
		"高等数学": [68, 39, 75, 43, 78, 80, 49, 56, 60, 62],
		"离散数学": [27, 12, 45, 86],
		"物理": [24, 35, 37, 23],
		"机器学习": [2, 67, 41, 15, 79, 48, 53, 54, 87, 90],
		"计算机网络": [65, 4, 69, 10, 77, 16, 18, 25],
		"操作系统": [33, 66, 7, 47, 19, 83, 84, 51, 63],
		"数据结构": [6, 71, 76, 13, 82, 52, 85, 61],
		"编译原理": [6, 71, 76, 13, 82, 52, 85, 61]
	}
	var klist = ["B树", "C++", "EM算法", "IO", "IP", "KMP", "LR(k)方法", "Linux", "Python", "SimpleGUITk", "TCP协议", "java",
		"一阶谓词演算", "中间代码", "二叉树", "人工智能", "以太网", "伸展树", "信号", "内存", "函数", "函数调用", "列表", "刚度", "力", "协议", "向量", "命题", "哈希",
		"堆", "复杂度", "字符串", "容器", "寄存器", "序列", "应变", "异常", "弹性", "循环", "微分", "快速排序", "感知机", "指针", "排列", "排序", "推理", "搜索",
		"操作系统", "支持向量机", "收敛", "数据结构", "文件", "文法", "朴素贝叶斯", "机器学习", "条件语句", "极限", "构造函数", "栈", "树", "概率", "正则表达式", "母函数",
		"汇编", "派生类", "物理层", "用户", "神经网络", "积分", "移动网络", "程序设计", "符号表", "算术运算符", "类", "红黑树", "组合数学", "编译", "网络", "群", "聚类",
		"计算", "语法", "语法分析", "进程", "进程和线程", "逆波兰表示法", "逻辑", "逻辑斯谛", "重载", "面向对象", "马尔可夫"
	]
	var clist = ["高等数学", "离散数学", "物理", "机器学习", "计算机网络", "操作系统",
		"数据结构",
		"编译原理"
	]
	
	var k1 = []
	for (var i = 0; i < 8; i++) {
		var c = clist[i]
		var data = []
		for (var j = 0; j < catemap[c].length; j++) {

			data[j] = {
				value: knowledgelist[catemap[c][j]],
				name: klist[catemap[c][j]]
			}
		}
		k1[i] = data
	}

	option = {
		tooltip: {
			trigger: 'item'
		},
		toolbox: {
			show: true,
			feature: {
				mark: {
					show: true
				},
				dataView: {
					show: true,
					readOnly: false
				},
				restore: {
					show: true
				},
				saveAsImage: {
					show: true
				}
			}
		},
		series: [{
				name: '高等数学',
				type: 'pie',
				radius: [10, 50],
				center: ['25%', '15%'],
				roseType: 'area',
				data: k1[0],
				itemStyle: {
					borderRadius: 5
				}
			},
			{
				name: '离散数学',
				type: 'pie',
				radius: [10, 50],
				center: ['75%', '15%'],
				roseType: 'area',
				data: k1[1],
				itemStyle: {
					borderRadius: 8
				}
			},
			{
				name: '物理',
				type: 'pie',
				radius: [10, 50],
				center: ['25%', '40%'],
				roseType: 'area',
				data: k1[2],
				itemStyle: {
					borderRadius: 8
				}
			},
			{
				name: '机器学习',
				type: 'pie',
				radius: [10, 50],
				center: ['75%', '40%'],
				roseType: 'area',
				data: k1[3],
				itemStyle: {
					borderRadius: 8
				}
			},
			{
				name: '计算机网络',
				type: 'pie',
				radius: [10, 50],
				center: ['25%', '65%'],
				roseType: 'area',
				data: k1[4],
				itemStyle: {
					borderRadius: 8
				}
			},
			{
				name: '操作系统',
				type: 'pie',
				radius: [10, 50],
				center: ['75%', '65%'],
				roseType: 'area',
				data: k1[5],
				itemStyle: {
					borderRadius: 8
				}
			},
			{
				name: '数据结构',
				type: 'pie',
				radius: [10, 50],
				center: ['25%', '90%'],
				roseType: 'area',
				data: k1[6],
				itemStyle: {
					borderRadius: 8
				}
			},
			{
				name: '编译原理',
				type: 'pie',
				radius: [10, 50],
				center: ['75%', '90%'],
				roseType: 'area',
				data: k1[7],
				itemStyle: {
					borderRadius: 8
				}
			}
		],

	};
	myChart.setOption(option);
});
