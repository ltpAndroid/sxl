package com.dofun.sxl.bean;

public class BookType extends BaseBean {
    private String type;
    private int resId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public BookType(String type, int resId) {
        this.type = type;
        this.resId = resId;
    }
}
