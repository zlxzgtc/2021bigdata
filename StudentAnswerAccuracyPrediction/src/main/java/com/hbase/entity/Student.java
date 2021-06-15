package com.hbase.entity;

import java.util.ArrayList;

public class Student {
	private String id;
	private int quizcount;
	private double accu;
	private String stuknowledge;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getQuizcount() {
		return quizcount;
	}
	public void setQuizcount(int quizcount) {
		this.quizcount = quizcount;
	}
	public double getAccu() {
		return accu;
	}
	public void setAccu(double accu) {
		this.accu = accu;
	}
	public String getStuknowledge() {
		return stuknowledge;
	}
	public void setStuknowledge(String stuknowledge) {
		this.stuknowledge = stuknowledge;
	}
	public Student() {
	}
	
}
