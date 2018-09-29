package com.dofun.sxl.bean;

public class Statistics extends BaseBean {


    /**
     * courseId : 10
     * totalScore : 105
     */

    private String courseId;
    private int totalScore;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
