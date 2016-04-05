package com.sen.redbull.mode;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/5.
 *
 * 学习与资源库的课程bean，不一样
 */
public class ResourSecondItemBean implements Serializable{
    private String date;
    private String hour;
    private String le_name;
    private String leid;
    private String lessonimg;
    private String score;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getLe_name() {
        return le_name;
    }

    public void setLe_name(String le_name) {
        this.le_name = le_name;
    }

    public String getLeid() {
        return leid;
    }

    public void setLeid(String leid) {
        this.leid = leid;
    }

    public String getLessonimg() {
        return lessonimg;
    }

    public void setLessonimg(String lessonimg) {
        this.lessonimg = lessonimg;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
