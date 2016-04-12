package com.sen.redbull.mode;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/12.
 */
public class ExamUserAnswer implements Serializable {
    private String id;
    private String answer;
    private String realAnswer;
    private float score;
    private String type;

    public String getRealAnswer() {
        return realAnswer;
    }

    public void setRealAnswer(String realAnswer) {
        this.realAnswer = realAnswer;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public ExamUserAnswer(String id, String answer, String realAnswer, float score, String type){
        this.id = id;
        this.answer = answer;
        this.type = type;
        this.realAnswer = realAnswer;
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
