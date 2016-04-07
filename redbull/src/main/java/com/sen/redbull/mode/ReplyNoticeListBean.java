package com.sen.redbull.mode;

import java.util.List;

public class ReplyNoticeListBean {
	private int maxPage ;
	private String success ;
	private String pageNum ;
	private List<ReplyNoticeItemBean> answerList;
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public List<ReplyNoticeItemBean> getAnswerList() {
		return answerList;
	}
	public void setAnswerList(List<ReplyNoticeItemBean> answerList) {
		this.answerList = answerList;
	}
	@Override
	public String toString() {
		return "ReplyNoticeListBean [maxPage=" + maxPage + ", success="
				+ success + ", pageNum=" + pageNum + ", answerList="
				+ answerList + "]";
	}
}
