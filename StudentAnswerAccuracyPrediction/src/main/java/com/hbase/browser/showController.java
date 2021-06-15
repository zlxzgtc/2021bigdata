//package com.hbase.browser;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.hbase.entity.Course;
//import com.hbase.entity.Quiz;
//import com.hbase.entity.Student;
//
//@Controller
//@RequestMapping("student")	
//public class showController {
//	
//		ArrayList<String> know =new ArrayList<String>(Arrays.asList("B树", "C++", "EM算法", "IO", "IP", "KMP", "LR(k)方法", "Linux", "Python", "SimpleGUITk", "TCP协议", "java", "一阶谓词演算", "中间代码", "二叉树", "人工智能", "以太网", "伸展树", "信号", "内存", "函数", "函数调用", "列表", "刚度", "力", "协议", "向量", "命题", "哈希", "堆", "复杂度", "字符串", "容器", "寄存器", "序列", "应变", "异常", "弹性", "循环", "微分", "快速排序", "感知机", "指针", "排列", "排序", "推理", "搜索", "操作系统", "支持向量机", "收敛", "数据结构", "文件", "文法", "朴素贝叶斯", "机器学习", "条件语句", "极限", "构造函数", "栈", "树", "概率", "正则表达式", "母函数", "汇编", "派生类", "物理层", "用户", "神经网络", "积分", "移动网络", "程序设计", "符号表", "算术运算符", "类", "红黑树", "组合数学", "编译", "网络", "群", "聚类", "计算", "语法", "语法分析", "进程", "进程和线程", "逆波兰表示法", "逻辑", "逻辑斯谛", "重载", "面向对象", "马尔可夫"));
//
//		ArrayList<Student> stulist = new ArrayList<>();
//		ArrayList<Course> courselist = new ArrayList<>();
//		ArrayList<String> courseidlist = new ArrayList<String>(Arrays.asList("Math","English","Japanese","Hadoop"));
//		ArrayList<String> courseacculist = new ArrayList<String>(Arrays.asList("12","34","76","43"));
//		ArrayList<String> videolengthlist = new ArrayList<String>(Arrays.asList("23","56","32","76"));
//		ArrayList<String> quizacculist = new ArrayList<String>(Arrays.asList("12","34","76","43"));
//		ArrayList<String> quizcountlist = new ArrayList<String>(Arrays.asList("23","56","32","76"));
//		String knowledgelist="[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89]";
//
//		@RequestMapping(path="/index", method = RequestMethod.GET)
//		public String list(Model model) {
//			if(stulist.size()==0) {
//		
//			}
//			model.addAttribute("knowledgelist", knowledgelist);
//			model.addAttribute("stulist", stulist);
//			model.addAttribute("quizacculist", quizacculist);
//			model.addAttribute("quizcountlist", quizcountlist);
//			return "index";
//		}
//		
//		@RequestMapping(path = "/index",method = RequestMethod.POST)
//		public String show(Model model,@RequestParam(value = "id",defaultValue="0") String id) {
////			User u = userService.findByName(name);
//			ArrayList<Student> stulist2 = new ArrayList<>();
////			System.out.println(num);
////			for(int i=0;i<stulist.size();i++) {
////				if(stulist.get(i).getNum()==num) {
////					stulist2.add(stulist.get(i));
////				}
////			}
//			
//
//			model.addAttribute("knowledgelist", knowledgelist);
//			model.addAttribute("stulist", stulist2);
//			model.addAttribute("quizacculist", quizacculist);
//			model.addAttribute("quizcountlist", quizcountlist);
//			return "index";
//		}
//		
//		
//		@RequestMapping(path="/courseinfo", method = RequestMethod.GET)
//		public String courseall(Model model) {
//			ArrayList<Course> courselist = new ArrayList<>();
//			
//			double averageaccu=0.8;
//			model.addAttribute("averageaccu", averageaccu);
//			model.addAttribute("courselist", courselist);
//			model.addAttribute("courseidlist", courseidlist);
//			model.addAttribute("videolengthlist", videolengthlist);
//			model.addAttribute("courseacculist", courseacculist);
//			return "courseinfo";
//		}
//		
//		@RequestMapping(path="/courseinfo", method = RequestMethod.POST)
//		public String coursesearch(Model model,@RequestParam(value = "mincount",defaultValue="0") int mincount,@RequestParam(value = "maxcount",defaultValue="0") int maxcount,@RequestParam(value = "minlength",defaultValue="0") double minlength,@RequestParam(value = "maxlength",defaultValue="0") double maxlength) {
//			ArrayList<Course> courselist = new ArrayList<>();
//			
//			
//			double averageaccu=0.8;
//			model.addAttribute("averageaccu", averageaccu);
//			model.addAttribute("courselist", courselist);
//			model.addAttribute("courseidlist", courseidlist);
//			model.addAttribute("videolengthlist", videolengthlist);
//			model.addAttribute("courseacculist", courseacculist);
//			return "courseinfo";
//		}
//		
//		@RequestMapping(path="/quizbase", method = RequestMethod.GET)
//		public String quizall(Model model) {
//			ArrayList<Quiz> quizlist = new ArrayList<>();
//			
//			model.addAttribute("quizlist", quizlist);
//			return "quizbase";
//		}
//		
//		@RequestMapping(path="/quizbase", method = RequestMethod.POST)
//		public String quizsearch(Model model,@RequestParam(value = "cid",defaultValue="0") String cid,@RequestParam(value = "knowledgepoint",defaultValue="0") String knowledgepoint) {
//			ArrayList<Quiz> quizlist = new ArrayList<>();
//			
//			model.addAttribute("quizlist", quizlist);
//			return "quizbase";
//		}
//	
//}
