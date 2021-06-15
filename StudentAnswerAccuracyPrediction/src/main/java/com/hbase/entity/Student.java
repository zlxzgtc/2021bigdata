package com.hbase.entity;

import java.util.ArrayList;

public class Student {
	private String id;
	private int quizcount;
	private double accu;
	private double preaccu;
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
	public double getPreaccu() {
		return preaccu;
	}
	public void setPreaccu(double preaccu) {
		this.preaccu = preaccu;
	}
	public Student(String id, int quizcount, double accu, double preaccu) {
		super();
		this.id = id;
		this.quizcount = quizcount;
		this.accu = accu;
		this.preaccu = preaccu;
	}
	
}
