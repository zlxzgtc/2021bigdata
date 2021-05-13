package com.hbase.entity;

public class Student {
	private long id;
	private double accu;
	private double preaccu;
	private String cid;
	private int num;
	private double duration;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Student(long id, double accu, double preaccu, String cid, int num, double duration) {
		super();
		this.id = id;
		this.accu = accu;
		this.preaccu = preaccu;
		this.cid = cid;
		this.num = num;
		this.duration = duration;
	}
}
