package com.sen.redbull.mode;

import java.io.Serializable;

public class ExamItemBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String examid;
	private String title;
	private String joincount;
	private String yetjoincount;
	private String pass_mark;
	private String entertime_begin;
	private String entertime_end;
	public String getExamid() {
		return examid;
	}
	public void setExamid(String examid) {
		this.examid = examid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJoincount() {
		return joincount;
	}
	public void setJoincount(String joincount) {
		this.joincount = joincount;
	}
	public String getYetjoincount() {
		return yetjoincount;
	}
	public void setYetjoincount(String yetjoincount) {
		this.yetjoincount = yetjoincount;
	}
	public String getPass_mark() {
		return pass_mark;
	}
	public void setPass_mark(String pass_mark) {
		this.pass_mark = pass_mark;
	}
	public String getEntertime_begin() {
		return entertime_begin;
	}
	public void setEntertime_begin(String entertime_begin) {
		this.entertime_begin = entertime_begin;
	}
	public String getEntertime_end() {
		return entertime_end;
	}
	public void setEntertime_end(String entertime_end) {
		this.entertime_end = entertime_end;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	

}
