package com.hbase.entity;

public class Query {
	
	private String cid;
	private int num;
	private double duration;
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	public Query(String cid, int num, double duration) {
		super();
		this.cid = cid;
		this.num = num;
		this.duration = duration;
	}
}
