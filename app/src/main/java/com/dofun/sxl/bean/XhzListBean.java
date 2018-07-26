package com.dofun.sxl.bean;

public class XhzListBean {
    private String date;
    private String week;
    private String score;
    private String diffLevel;
    private String content;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDiffLevel() {
        return diffLevel;
    }

    public void setDiffLevel(String diffLevel) {
        this.diffLevel = diffLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public XhzListBean(String date, String week, String score, String diffLevel, String content) {
        this.date = date;
        this.week = week;
        this.score = score;
        this.diffLevel = diffLevel;
        this.content = content;
    }
}
