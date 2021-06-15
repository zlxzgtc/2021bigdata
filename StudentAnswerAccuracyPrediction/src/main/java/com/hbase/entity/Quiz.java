package com.hbase.entity;

public class Quiz {
	private String qid;
	private String cid;
	private String donecount;
	private String quizinfo;
	private String knowledgepoint;
	private Double accu;
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
	public String getDonecount() {
		return donecount;
	}
	public void setDonecount(String donecount) {
		this.donecount = donecount;
	}
	public String getQuizinfo() {
		return quizinfo;
	}
	public void setQuizinfo(String quizinfo) {
		this.quizinfo = quizinfo;
	}
	public String getKnowledgepoint() {
		return knowledgepoint;
	}
	public void setKnowledgepoint(String knowledgepoint) {
		this.knowledgepoint = knowledgepoint;
	}
	public Double getAccu() {
		return accu;
	}
	public void setAccu(Double accu) {
		this.accu = accu;
	}
	
}
