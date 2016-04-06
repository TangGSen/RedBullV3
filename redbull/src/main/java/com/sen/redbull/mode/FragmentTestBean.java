package com.sen.redbull.mode;

import java.util.List;

public class FragmentTestBean {
	private String success;
	private List<ExamItemBean> ExamList ;
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public List<ExamItemBean> getExamList() {
		return ExamList;
	}
	public void setExamList(List<ExamItemBean> examList) {
		ExamList = examList;
	}
	@Override
	public String toString() {
		return "MyTextBean [success=" + success + ", ExamList=" + ExamList
				+ "]";
	}
}
