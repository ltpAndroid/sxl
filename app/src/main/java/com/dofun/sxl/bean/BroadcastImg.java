package com.dofun.sxl.bean;

public class BroadcastImg extends BaseBean {

    /**
     * createTime : 1534127492000
     * id : 668
     * imageLink : 231231
     * imagePath : attachment/images/1534127491751_tMdAed.gif
     * publishTime : 1534127522000
     * status : 1
     * title : 杨紫分手了
     * type : 1
     */

    private long createTime;
    private int id;
    private String imageLink;
    private String imagePath;
    private long publishTime;
    private int status;
    private String title;
    private int type;

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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
