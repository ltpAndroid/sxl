package com.dofun.sxl.bean;

public class Comment extends BaseBean {

    /**
     * content : 公交卡
     * createTime : 1536048943000
     * id : 22
     * itemId : 6
     * name : 视频名称01
     * status : 0
     * userId : 12
     * username : 1212121212
     */

    private String content;
    private long createTime;
    private int id;
    private int itemId;
    private String name;
    private int status;
    private int userId;
    private String username;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
