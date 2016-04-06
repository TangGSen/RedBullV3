package com.sen.redbull.mode;

import java.util.List;

public class BbsListBean {
	private String creater;
	private String createtime;
	private String id;
	private int orderCol;
	private String pid;
	private List<BbsBean> tbztzbbs;
	private String text;

	public BbsListBean() {
		super();
	}

	public String getCreater() {
		if (creater == null) {
			return null;
		} else {
			return creater;
		}
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreatetime() {
		if (createtime == null) {
			return null;
		} else {
			return createtime;
		}
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getId() {
		if (id == null) {
			return null;
		} else {
			return id;
		}
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		if (pid == null) {
			return null;
		} else {
			return pid;
		}
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getOrderCol() {
		if (orderCol == 0) {
			return 0;
		} else {
			return orderCol;
		}
	}

	public void setOrderCol(int orderCol) {
		this.orderCol = orderCol;
	}

	public List<BbsBean> getTbztzbbs() {
		if (tbztzbbs==null) {
			return null ;
		}else {
			return tbztzbbs;
		}
	}

	public void setTbztzbbs(List<BbsBean> tbztzbbs) {
		this.tbztzbbs = tbztzbbs;
	}

	public String getText() {
		if (text == null) {
			return null;
		} else {
			return text;
		}
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "BbsListBean [creater=" + creater + ", createtime=" + createtime
				+ ", id=" + id + ", orderCol=" + orderCol + ", pid=" + pid
				+ ", tbztzbbs=" + tbztzbbs + ", text=" + text + "]";
	}
	
}
