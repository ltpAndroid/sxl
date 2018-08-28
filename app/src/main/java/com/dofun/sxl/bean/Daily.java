package com.dofun.sxl.bean;

public class Daily extends BaseBean {
    private String courseName;
    private long startTime;
    private String teacherName;
    private long endTime;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Daily(String courseName, long startTime, long endTime, String teacherName) {
        this.courseName = courseName;
        this.startTime = startTime;
        this.teacherName = teacherName;
        this.endTime = endTime;
    }
}
