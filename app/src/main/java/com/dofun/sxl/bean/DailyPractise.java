package com.dofun.sxl.bean;

public class DailyPractise {
    private String title;

    private String start_time;

    private String teacher;

    private String type;

    private String end_time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public DailyPractise(String title, String start_time, String teacher,
                         String type, String end_time) {
        super();
        this.title = title;
        this.start_time = start_time;
        this.teacher = teacher;
        this.type = type;
        this.end_time = end_time;
    }


}
