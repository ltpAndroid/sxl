package com.dofun.sxl.bean;

public class ListBean extends BaseBean {
    /**
     * 题型列表
     */

    /**
     * code : 101
     * id : 101
     * status : 0
     * type : question_types
     * value : 填空
     */

    private String code;
    private int id;
    private int status;
    private String type;
    private String value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
