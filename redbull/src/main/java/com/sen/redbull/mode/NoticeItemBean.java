package com.sen.redbull.mode;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NoticeItemBean implements Serializable {
	
	private int answercount; 
	private String bbschildren_id ;
	private String browse ;
	private String context ;
	private String createtime ;
	private String creatid ;
	private String creator ;
	private String dispose ;
	private String hot ;
	private String id;
	private String ischeck ;
	private String isfinish ;
	private String isnewask ;
	private String isrecommend ;
	private String photo ;
	private String recommend ;
	private String regname ;
	private String reward;
	private String title ;
	private String totop ;
	private String type ;
	public int getAnswercount() {
		return answercount;
	}
	public void setAnswercount(int answercount) {
		this.answercount = answercount;
	}
	public String getBbschildren_id() {
		return bbschildren_id;
	}
	public void setBbschildren_id(String bbschildren_id) {
		this.bbschildren_id = bbschildren_id;
	}
	public String getBrowse() {
		return browse;
	}
	public void setBrowse(String browse) {
		this.browse = browse;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getCreatid() {
		return creatid;
	}
	public void setCreatid(String creatid) {
		this.creatid = creatid;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getDispose() {
		return dispose;
	}
	public void setDispose(String dispose) {
		this.dispose = dispose;
	}
	public String getHot() {
		return hot;
	}
	public void setHot(String hot) {
		this.hot = hot;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIscheck() {
		return ischeck;
	}
	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}
	public String getIsfinish() {
		return isfinish;
	}
	public void setIsfinish(String isfinish) {
		this.isfinish = isfinish;
	}
	public String getIsnewask() {
		return isnewask;
	}
	public void setIsnewask(String isnewask) {
		this.isnewask = isnewask;
	}
	public String getIsrecommend() {
		return isrecommend;
	}
	public void setIsrecommend(String isrecommend) {
		this.isrecommend = isrecommend;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public String getRegname() {
		return regname;
	}
	public void setRegname(String regname) {
		this.regname = regname;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTotop() {
		return totop;
	}
	public void setTotop(String totop) {
		this.totop = totop;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "NoticeItemBean [answercount=" + answercount
				+ ", bbschildren_id=" + bbschildren_id + ", browse=" + browse
				+ ", context=" + context + ", createtime=" + createtime
				+ ", creatid=" + creatid + ", creator=" + creator
				+ ", dispose=" + dispose + ", hot=" + hot + ", id=" + id
				+ ", ischeck=" + ischeck + ", isfinish=" + isfinish
				+ ", isnewask=" + isnewask + ", isrecommend=" + isrecommend
				+ ", photo=" + photo + ", recommend=" + recommend
				+ ", regname=" + regname + ", reward=" + reward + ", title="
				+ title + ", totop=" + totop + ", type=" + type + "]";
	}
}
