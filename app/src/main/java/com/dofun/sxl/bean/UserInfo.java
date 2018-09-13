package com.dofun.sxl.bean;

public class UserInfo extends BaseBean {
    //private static final long serialVersionUID = -832979692118596910L;
    /**
     * avatarUrl : http://127.0.0.1:8080/funny/attachment/images/1533540391424_x1vPnd.jpg
     * id : 2
     * mobile : 13112345678
     * mobileDown : 1
     * mobileView : 1
     * nickname : 小学生
     * sex : 5
     * sexStr : 性别存疑
     * status : 1
     * subject : 0
     * username : 13112345678
     * "classId":1
     * "className":"1602"
     * "schoolId":1
     * "schoolName":"武汉市江汉区大兴路小学"
     */

    private String avatarUrl;
    private int id;
    private String mobile;
    private int mobileDown;
    private int mobileView;
    private String nickname;
    private int sex;
    private String sexStr;
    private int status;
    private int subject;
    private String username;
    private String className;
    private int classId;
    private String schoolName;
    private int schoolId;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getMobileDown() {
        return mobileDown;
    }

    public void setMobileDown(int mobileDown) {
        this.mobileDown = mobileDown;
    }

    public int getMobileView() {
        return mobileView;
    }

    public void setMobileView(int mobileView) {
        this.mobileView = mobileView;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSexStr() {
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        this.sexStr = sexStr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
