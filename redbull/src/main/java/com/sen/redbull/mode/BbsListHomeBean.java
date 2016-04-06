package com.sen.redbull.mode;

import java.util.List;

public class BbsListHomeBean {
	private String success;
	private List<BbsListBean> BBSList;
	public BbsListHomeBean() {
		super();
	}

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

	public List<BbsListBean> getBBSList() {
		return BBSList;
	}

	public void setBBSList(List<BbsListBean> bBSList) {
		BBSList = bBSList;
	}

	@Override
	public String toString() {
		return "BbsListHomeBean [success=" + success + ", BBSList=" + BBSList
				+ "]";
	}

}
