package com.dofun.sxl.http;

import java.io.Serializable;

/**
 * 网络访问返回类
 *
 * @author Administrator
 */
public class ResInfo implements Serializable {
    private String status; // 200成功，其余值 失败
    private String msg; // 状态描述信息
    private String data;
    private String extra;// 返回临时信息,

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    //	 {
    //    "status": "200",                    //200 成功，其余值 失败
    //    "msg": "success",                 //状态描述信息
    //    "data": [						  //返回信息，具体形式为 key-value 的形式，如果需要则返回列表，例如 data:{} 或 data:[{},{}]
    //        {
    //            "id": 1,
    //            "title": "广告标题",
    //            "position": "INFO",
    //            "url": "广告URL",
    //            "photo": "images/1.jpg",
    //            "status": "1",
    //            "addTime": 1418465127261,
    //            "expiry": "30"
    //        }
    //    ],
    //    "extra":{
    //        "page":1,
    //        "pageSize":3
    //    }
    //}


}
