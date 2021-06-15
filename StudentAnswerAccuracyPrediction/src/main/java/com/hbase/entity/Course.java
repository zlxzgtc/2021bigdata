package com.hbase.entity;

public class Course {
	private String cid;
	private int quizcount;
	private int videocount;
	private double accu;
	private double videolength;
	private int videoview;
	private double videototallength;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public int getQuizcount() {
		return quizcount;
	}
	public void setQuizcount(int quizcount) {
		this.quizcount = quizcount;
	}
	public int getVideocount() {
		return videocount;
	}
	public void setVideocount(int videocount) {
		this.videocount = videocount;
	}
	public double getAccu() {
		return accu;
	}
	public void setAccu(double accu) {
		this.accu = accu;
	}
	public double getVideolength() {
		return videolength;
	}
	public void setVideolength(double videolength) {
		this.videolength = videolength;
	}
	public int getVideoview() {
		return videoview;
	}
	public void setVideoview(int videoview) {
		this.videoview = videoview;
	}
	public double getVideototallength() {
		return videototallength;
	}
	public void setVideototallength(double videototallength) {
		this.videototallength = videototallength;
	}
}
