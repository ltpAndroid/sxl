package com.dofun.sxl.bean;

public class TermBean extends BaseBean {

    /**
     * grade : 1
     * gradeCode : P1
     * id : 21
     * name : 一年级上半学期
     * schoolSystem : 0
     * schoolType : 0
     * semester : 1
     */

    private int grade;
    private String gradeCode;
    private int id;
    private String name;
    private int schoolSystem;
    private int schoolType;
    private int semester;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSchoolSystem() {
        return schoolSystem;
    }

    public void setSchoolSystem(int schoolSystem) {
        this.schoolSystem = schoolSystem;
    }

    public int getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(int schoolType) {
        this.schoolType = schoolType;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
