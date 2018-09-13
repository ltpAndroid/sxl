package com.dofun.sxl.bean;

public class Book extends BaseBean {

    /**
     * content : 1.最小的正整数。见〖数字〗。
     * <p>
     * 2.表示同一：咱们是～家人。你们～路走。这不是～码事。
     * <p>
     * 3.表示另一：番茄～名西红柿。
     * <p>
     * 4.表示整个；全：～冬。～生。～路平安。～屋子人。～身的汗。
     * <p>
     * 5.表示专一：～心～意。
     * <p>
     * 6.表示动作是一次，或表示动作是短暂的，或表示动作是试试的。a）用在重叠的动词（多为单音）中间：歇～歇。笑～笑。让我闻～闻。b）用在动词之后，动量词之前：笑～声。看～眼。让我们商量～下。
     * <p>
     * 7.用在动词或动量词前面，表示先做某个动作（下文说明动作结果）：～跳跳了过去。～脚把它踢开。他在旁边～站，再也不说什么。
     * <p>
     * 8.与“就”配合，表示两个动作紧接着发生：～请就来。～说就明白了。
     * <p>
     * 9.一旦；一经：～失足成千古恨。
     * <p>
     * 10.“一”字单用或在一词一句末尾念阴平，如“十一、一一得一”，在去声字前念阳平，如“一半、一共”，在阴平、阳平、上声字前念去声，如“一天、一年、一点”。本词典为简便起见，条目中的“一”字，都注阴平。
     * <p>
     * 11.我国民族音乐音阶上的一级，乐谱上用作记音符号，相当于简谱的“”。见〖工尺〗。
     * contentImagePath : attachment/images/1536323129208_9FLAjU.jpg
     * createTime : 1536323129000
     * id : 3
     * imageLink : www.baidu.com
     * imagePath : attachment/images/1536323129208_a7zlZP.jpg
     * imageTitle : 图片标题
     * modifyTime : 1536326058000
     * position : 1
     * status : 1
     * title : 标题
     * type : 1
     */

    private String content;
    private String contentImagePath;
    private long createTime;
    private int id;
    private String imageLink;
    private String imagePath;
    private String imageTitle;
    private long modifyTime;
    private int position;
    private int status;
    private String title;
    private int type;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentImagePath() {
        return contentImagePath;
    }

    public void setContentImagePath(String contentImagePath) {
        this.contentImagePath = contentImagePath;
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

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
