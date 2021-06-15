package com.hbase.browser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hbase.client.HBaseClient;
import com.hbase.entity.Course;
import com.hbase.entity.Quiz;
import com.hbase.entity.Student;

@Controller
@RequestMapping("student")
public class showController {

	boolean tag = false;
	boolean wow = false;
	double averageaccu;

	ArrayList<String> know = new ArrayList<String>(Arrays.asList("B树", "C++", "EM算法", "IO", "IP", "KMP", "LR(k)方法",
			"Linux", "Python", "SimpleGUITk", "TCP协议", "java", "一阶谓词演算", "中间代码", "二叉树", "人工智能", "以太网", "伸展树", "信号",
			"内存", "函数", "函数调用", "列表", "刚度", "力", "协议", "向量", "命题", "哈希", "堆", "复杂度", "字符串", "容器", "寄存器", "序列", "应变",
			"异常", "弹性", "循环", "微分", "快速排序", "感知机", "指针", "排列", "排序", "推理", "搜索", "操作系统", "支持向量机", "收敛", "数据结构", "文件",
			"文法", "朴素贝叶斯", "机器学习", "条件语句", "极限", "构造函数", "栈", "树", "概率", "正则表达式", "母函数", "汇编", "派生类", "物理层", "用户",
			"神经网络", "积分", "移动网络", "程序设计", "符号表", "算术运算符", "类", "红黑树", "组合数学", "编译", "网络", "群", "聚类", "计算", "语法", "语法分析",
			"进程", "进程和线程", "逆波兰表示法", "逻辑", "逻辑斯谛", "重载", "面向对象", "马尔可夫"));

//	public final static Map map = new HashMap();
//	  static {
//	    map.put("key1", new ArrayList<String>(Arrays.asList("68, 39, 75, 43, 78, 80, 49, 56, 60, 62")));
//	    
//	    map.put("key2", new ArrayList<String>(27, 12, 45, 86));
//	  }

	ArrayList<Quiz> quizlist = new ArrayList<Quiz>();
	
	ArrayList<Course> courselist = new ArrayList<>();
	ArrayList<String> courseidlist = new ArrayList<String>();
	ArrayList<Double> videolengthlist = new ArrayList<Double>();
	ArrayList<Double> courseacculist = new ArrayList<Double>();
	
	ArrayList<Student> stulist = new ArrayList<>();
	
	ArrayList<Double> quizacculist = new ArrayList<Double>();
	ArrayList<Integer> quizcountlist = new ArrayList<Integer>();
	
	String knowledgelist;

	@RequestMapping(path = "/index", method = RequestMethod.GET)
	public String list(Model model) {
		if (stulist.isEmpty()) {
			List<String> allstulist = new ArrayList<>();
			try {
				allstulist = HBaseClient.scan("student");
				Student student = new Student();
				boolean flag = false;
				for (int j = 0; j < 1000; j++) {
					String ss = allstulist.get(j);
					List<String> list1 = Arrays.asList(ss.split(":lynx:"));
					if (list1.get(0).equals("problem") && flag) {
						if(student.getId().charAt(0)=='U') {
							stulist.add(student);
						}
						student = new Student();
						student.setId(list1.get(1));
					} else if (list1.get(0).equals("problem") && !flag) {
						student.setId(list1.get(1));
						flag = true;
					} else if (list1.get(0).equals("p_num")) {
						student.setQuizcount(Integer.parseInt(list1.get(1)));
					} else if (list1.get(0).equals("true_accu")) {
						student.setAccu(Double.parseDouble(list1.get(1)));
					} else if (list1.get(0).equals("concepts")) {
						student.setStuknowledge(list1.get(1));
					} 
				}
				if(student.getId().charAt(0)=='U') {
					stulist.add(student);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Map<Integer, Double> map = new HashMap<Integer,Double>(); 
		
		int counts[] = new int[5000];
		if(wow==false) {
			for(int i=0;i<40;i++) {
				int k=stulist.get(i).getQuizcount();
				double v=stulist.get(i).getAccu();
				if(map.containsKey(k)) {
					map.put(k, map.get(k)+v);
					counts[k]=counts[k]+1;
				}else {
					map.put(k,v);
					counts[k]=1;
				}
			}
			Set<Integer> keySet = map.keySet();
	        Iterator<Integer> it =keySet.iterator();
	        while(it.hasNext()){   
	            Integer key = it.next();
	            Double value = map.get(key);
	            if(counts[key]!=0) {
	            	quizacculist.add(value/counts[key]);
		            quizcountlist.add(key);
	            }
	        }
//			Set set=map.keySet();
//	        Object[] arr=set.toArray();
//	        Arrays.sort(arr);
//	        for(Object key:arr){
//	        	if(counts[key]!=0) {
//	            	quizacculist.add(value/counts[key]);
//		            quizcountlist.add(key);
//	            }
//	        }
			wow=true;
		}

		
		model.addAttribute("knowledgelist", stulist.get(10).getStuknowledge());
		model.addAttribute("stulist", stulist);
		model.addAttribute("quizacculist", quizacculist);
		model.addAttribute("quizcountlist", quizcountlist);
		return "index";
	}

	@RequestMapping(path = "/index", method = RequestMethod.POST)
	public String show(Model model, @RequestParam(value = "id", defaultValue = "0") String id) {

		for(int i=0;i<stulist.size();i++) {
			if(stulist.get(i).getId().equals(id)) {
				knowledgelist=stulist.get(i).getStuknowledge();
			}
		}
		
		
		
		
		
		model.addAttribute("knowledgelist", knowledgelist);
		model.addAttribute("stulist", stulist);
		model.addAttribute("quizacculist", quizacculist);
		model.addAttribute("quizcountlist", quizcountlist);
		return "index";
	}

	@RequestMapping(path = "/courseinfo", method = RequestMethod.GET)
	public String courseall(Model model) {
		if (courselist.isEmpty()) {
			List<String> allcourselist = new ArrayList<>();
			try {
				allcourselist = HBaseClient.scan("course");
				Course course = new Course();
				boolean flag = false;
				for (int j = 0; j < allcourselist.size(); j++) {
					String ss = allcourselist.get(j);
					List<String> list1 = Arrays.asList(ss.split(":lynx:"));
					if (list1.get(0).equals("problem") && flag) {
						courselist.add(course);
						course = new Course();
						course.setCid(list1.get(1));
					} else if (list1.get(0).equals("problem") && !flag) {
						course.setCid(list1.get(1));
						flag = true;
					} else if (list1.get(0).equals("p_num")) {
						course.setQuizcount(Integer.parseInt(list1.get(1)));
					} else if (list1.get(0).equals("v_num")) {
						course.setVideocount(Integer.parseInt(list1.get(1)));
					} else if (list1.get(0).equals("accuracy")) {
						course.setAccu(Double.parseDouble(list1.get(1)));
					} else if (list1.get(0).equals("v_time")) {
						course.setVideolength(Double.parseDouble(list1.get(1)));
					} else if (list1.get(0).equals("v_plays")) {
						String plays=list1.get(1);
						if(plays.equals("")) {
							plays="0";
						}
						course.setVideoview((int)Double.parseDouble(plays));
					} else if (list1.get(0).equals("v_watch_time")) {
						course.setVideototallength(Double.parseDouble(list1.get(1))/60);
					}
				}
				courselist.add(course);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (tag == false) {
			double sum = 0;
			for (int i = 0; i < courselist.size()-12; i++) {
				sum = sum + courselist.get(i).getAccu();
				courseidlist.add(courselist.get(i).getCid());
				videolengthlist.add(courselist.get(i).getVideototallength());
				courseacculist.add(courselist.get(i).getAccu());
			}
			averageaccu = sum / courselist.size();
			tag = true;
		}

		model.addAttribute("courselist", courselist);
		model.addAttribute("averageaccu", averageaccu);
		model.addAttribute("courseidlist", courseidlist);
		model.addAttribute("videolengthlist", videolengthlist);
		model.addAttribute("courseacculist", courseacculist);
		return "courseinfo";
	}

	@RequestMapping(path = "/courseinfo", method = RequestMethod.POST)
	public String coursesearch(Model model, @RequestParam(value = "mincount", defaultValue = "0") int mincount,
			@RequestParam(value = "maxcount", defaultValue = "4000000") int maxcount,
			@RequestParam(value = "minlength", defaultValue = "0") double minlength,
			@RequestParam(value = "maxlength", defaultValue = "200000") double maxlength) {

		double sum=0;
		int count=0;
		
		for(int i=0;i<courselist.size();i++) {
			int viewcount = courselist.get(i).getVideoview();
			double viewlength = courselist.get(i).getVideototallength();
			if(viewcount<=maxcount && viewcount >= mincount && viewlength>=minlength && viewlength <= maxlength) {
				sum=sum+courselist.get(i).getAccu();
				count++;
			}
		}

		double averaccu = sum/count;
		
		model.addAttribute("averageaccu", averaccu);
		model.addAttribute("courselist", courselist);
		model.addAttribute("courseidlist", courseidlist);
		model.addAttribute("videolengthlist", videolengthlist);
		model.addAttribute("courseacculist", courseacculist);
		return "courseinfo";
	}

	@RequestMapping(path = "/quizbase", method = RequestMethod.GET)
	public String quizall(Model model) {
		if (quizlist.isEmpty()) {
			List<String> allquizlist = new ArrayList<>();
			try {
				allquizlist = HBaseClient.scan("problem");
				Quiz quiz = new Quiz();
				boolean flag = false;
				for (int j = 0; j < allquizlist.size(); j++) {
					String ss = allquizlist.get(j);
					List<String> list1 = Arrays.asList(ss.split(":lynx:"));
					if (list1.get(0).equals("problem") && flag) {
						quizlist.add(quiz);
						quiz = new Quiz();
						quiz.setQid(list1.get(1));
					} else if (list1.get(0).equals("problem") && !flag) {
						quiz.setQid(list1.get(1));
						flag = true;
					} else if (list1.get(0).equals("detail")) {
						quiz.setQuizinfo(list1.get(1));
					} else if (list1.get(0).equals("accuracy")) {
						quiz.setAccu(Double.valueOf(list1.get(1)));
					} else if (list1.get(0).equals("concept")) {
						String kp = list1.get(1);
						kp = StringUtils.strip(kp, "['']");
						quiz.setKnowledgepoint(kp);
					} else if (list1.get(0).equals("course_id")) {
						quiz.setCid(list1.get(1));
					} else if (list1.get(0).equals("count")) {
						quiz.setDonecount(list1.get(1));
					}
				}
				quizlist.add(quiz);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("quizlist", quizlist);
		return "quizbase";

	}

	@RequestMapping(path = "/quizbase", method = RequestMethod.POST)
	public String quizsearch(Model model, @RequestParam(value = "cid", defaultValue = "0") String cid,
			@RequestParam(value = "knowledgepoint", defaultValue = "0") String knowledgepoint) {

		List<Quiz> quizlist2 = new ArrayList<>();

		if (!cid.equals("0") && knowledgepoint.equals("0")) {
			for (int i = 0; i < quizlist.size(); i++) {
				if (quizlist.get(i).getCid().equals(cid)) {
					quizlist2.add(quizlist.get(i));
				}
			}
		} else if (cid.equals("0") && !knowledgepoint.equals("0")) {
			for (int i = 0; i < quizlist.size(); i++) {
				if (quizlist.get(i).getKnowledgepoint().equals(knowledgepoint)) {
					quizlist2.add(quizlist.get(i));
				}
			}
		} else {
			for (int i = 0; i < quizlist.size(); i++) {
				if (quizlist.get(i).getKnowledgepoint().equals(knowledgepoint)
						&& quizlist.get(i).getCid().equals(cid)) {
					quizlist2.add(quizlist.get(i));
				}
			}
		}

		model.addAttribute("quizlist", quizlist2);
		return "quizbase";
	}
}
