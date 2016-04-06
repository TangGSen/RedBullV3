package com.sen.redbull.mode;

import java.util.List;

public class NoticeListHomeBean {
	
	private List<NoticeItemBean> askList ;
	private int maxPage ;
	private String pageNum ;
	private String success ;
	public List<NoticeItemBean> getAskList() {
		return askList;
	}
	public void setAskList(List<NoticeItemBean> askList) {
		this.askList = askList;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	@Override
	public String toString() {
		return "NoticeListHomeBean [askList=" + askList + ", maxPage="
				+ maxPage + ", pageNum=" + pageNum + ", success=" + success
				+ "]";
	}
}
