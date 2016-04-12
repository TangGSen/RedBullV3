package com.sen.redbull.exam;

import com.sen.redbull.mode.Paper;

import java.io.Serializable;
import java.util.List;

public class ExamTestHomeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Paper paper;
	private List<QuestionList> questionList;
	private String success;


	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public List<QuestionList> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<QuestionList> questionList) {
		this.questionList = questionList;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}
}
