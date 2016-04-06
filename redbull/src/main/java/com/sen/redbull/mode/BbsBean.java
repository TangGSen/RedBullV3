package com.sen.redbull.mode;

public class BbsBean {
	private String countanswer;
	private String countask;
	private String id;
	private String lasttime;
	private String name;
	public BbsBean() {
		super();
	}
	public String getCountanswer() {
		if(countanswer==null){
			return null ;
		}else {
			return countanswer;	
		}
		
	}
	public void setCountanswer(String countanswer) {
		this.countanswer = countanswer;
	}
	public String getCountask() {
		if(countask==null){
			return null ;
		}else {
			return countask;	
		}
	}
	public void setCountask(String countask) {
		this.countask = countask;
	}
	public String getId() {
		if(id==null){
			return null ;
		}else {
			return id;	
		}
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLasttime() {
		if(lasttime==null){
			return null ;
		}else {
			return lasttime;	
		}
	}
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	public String getName() {
		if(name==null){
			return null ;
		}else {
			return name;	
		}
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "BbsBean [countanswer=" + countanswer + ", countask=" + countask
				+ ", id=" + id + ", lasttime=" + lasttime + ", name=" + name
				+ "]";
	}
	
}
