package com.sen.redbull.mode;

import java.util.List;

public class FragmentTestBean {
	private String success;
	private List<ExamBean> ExamList ;
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public List<ExamBean> getExamList() {
		return ExamList;
	}
	public void setExamList(List<ExamBean> examList) {
		ExamList = examList;
	}
	@Override
	public String toString() {
		return "MyTextBean [success=" + success + ", ExamList=" + ExamList
				+ "]";
	}
}
