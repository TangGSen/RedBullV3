package com.sen.redbull.mode;

import java.io.Serializable;
import java.util.List;

/**
 * 学习与资源库的课程bean，不一样
 */

public class ResouceLessonHomeBean implements Serializable {
	private String success;
	private List<ResourSecondItemBean> courseslist;


	public String getSuccess() {
		if (success == null) {
			return null;
		} else {
			return success;
		}
	}

	public void setSuccess(String success) {
		this.success = success;
	}


	public List<ResourSecondItemBean> getCourseslist() {
		return courseslist;
	}

	public void setCourseslist(List<ResourSecondItemBean> courseslist) {
		this.courseslist = courseslist;
	}
}
