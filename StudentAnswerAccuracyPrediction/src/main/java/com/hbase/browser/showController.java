package com.hbase.browser;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hbase.entity.Query;
import com.hbase.entity.Student;

@Controller
@RequestMapping("student")	
public class showController {
	
		ArrayList<Student> stulist = new ArrayList<>();
	
		@GetMapping("/index")
		public ModelAndView list() {
			ArrayList<String> courselist = new ArrayList<String>(Arrays.asList("Math","English","Japanese","Hadoop"));
			ArrayList<String> courseacculist = new ArrayList<String>(Arrays.asList("12%","34%","76%","43%"));
			stulist.add(new Student(1,2.5,3.6,"12",34,678));
			stulist.add(new Student(2,0.8,2.7,"18",34,500));
			stulist.add(new Student(3,1.3,1.0,"12",36,500));
			stulist.add(new Student(4,2.5,3.4,"13",36,678));
			stulist.add(new Student(5,0.8,2.6,"18",24,321));
			stulist.add(new Student(6,1.9,1.0,"19",24,678));
			stulist.add(new Student(7,2.5,3.6,"18",24,500));
			stulist.add(new Student(8,8.8,2.7,"12",34,678));
			stulist.add(new Student(9,1.4,1.0,"18",24,430));
			stulist.add(new Student(10,2.6,3.6,"19",23,67));
			stulist.add(new Student(11,0.8,2.7,"13",34,45));
			stulist.add(new Student(12,1.4,1.0,"13",46,890));
			stulist.add(new Student(13,2.6,3.6,"13",45,678));
			stulist.add(new Student(14,0.8,2.7,"13",92,500));
			stulist.add(new Student(15,1.4,1.0,"12",45,678));
			ModelAndView modelAndView = new ModelAndView("index");
			modelAndView.addObject("stulist", stulist);
			return modelAndView;
		}
}
