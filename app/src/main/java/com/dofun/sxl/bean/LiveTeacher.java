package com.dofun.sxl.bean;

public class LiveTeacher {
    private String headerImg;
    private String name;

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LiveTeacher(String name) {
        this.name = name;
    }
}
