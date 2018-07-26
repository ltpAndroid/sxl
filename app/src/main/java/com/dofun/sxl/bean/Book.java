package com.dofun.sxl.bean;

public class Book {
    private String bookName;
    private String anthor;
    private String introduction;

    public Book(String bookName, String anthor, String introduction) {
        this.bookName = bookName;
        this.anthor = anthor;
        this.introduction = introduction;
    }

    public String getBookName() {

        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAnthor() {
        return anthor;
    }

    public void setAnthor(String anthor) {
        this.anthor = anthor;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
