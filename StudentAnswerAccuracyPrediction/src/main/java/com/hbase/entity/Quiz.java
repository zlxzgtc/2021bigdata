package com.hbase.entity;

import java.util.ArrayList;

public class Quiz {
	private String qid;
	private String cid;
	private int donecount;
	private String quizinfo;
	private ArrayList<String> konwlegpoint;
	private double accu;
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public int getDonecount() {
		return donecount;
	}
	public void setDonecount(int donecount) {
		this.donecount = donecount;
	}
	public String getQuizinfo() {
		return quizinfo;
	}
	public void setQuizinfo(String quizinfo) {
		this.quizinfo = quizinfo;
	}
	public ArrayList<String> getKonwlegpoint() {
		return konwlegpoint;
	}
	public void setKonwlegpoint(ArrayList<String> konwlegpoint) {
		this.konwlegpoint = konwlegpoint;
	}
	public double getAccu() {
		return accu;
	}
	public void setAccu(double accu) {
		this.accu = accu;
	}
	public Quiz(String qid, String cid, int donecount, String quizinfo, ArrayList<String> konwlegpoint, double accu) {
		super();
		this.qid = qid;
		this.cid = cid;
		this.donecount = donecount;
		this.quizinfo = quizinfo;
		this.konwlegpoint = konwlegpoint;
		this.accu = accu;
	}
}
