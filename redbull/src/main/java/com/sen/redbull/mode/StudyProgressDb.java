package com.sen.redbull.mode;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/21.
 * 这个是下载历史的数据库bean
 */
@Table(name = "studyprogress")
public class StudyProgressDb extends Model implements Serializable {

    @Column(name = "userid")
    public String userid;
    @Column(name = "lessonid")
    public String lessonid;
    @Column(name = "time")
    public int time;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public StudyProgressDb() {
        super();
    }

    public StudyProgressDb(String userid, String lessonid, int time) {
        this.userid = userid;
        this.lessonid = lessonid;
        this.time = time;
    }

    public String getLessonid() {
        return lessonid;
    }

    public void setLessonid(String lessonid) {
        this.lessonid = lessonid;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
