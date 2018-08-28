package com.dofun.sxl;

/**
 * 接口文件
 *
 * @author Administrator
 */
public class Deploy {
    // 接口地址
    // http://139.224.2.231/aplus/system/interfaceList.jsp
    // public static String URL = "http://139.224.2.231/aplus/api/";

    // 后台地址
    // http://app.aoshiapp.com/aplus/adminLogin.jsp

    //public static String U = "http://app.aoshiapp.com/";
    //public static String U = "http://10.168.1.147:8080/";
    public static String U = "http://116.255.145.242:8801/";
    public static String ImgURL = "http://116.255.145.242:8801/funny/";
    public static String URL = U + "funny/api/" /*"http://192.168.0.1/:8080/aplus/api/"*/;
    //    public static String URL = "http://172.30.14.1:8080/aplus/api/";

    // 学趣部落使用条款及隐私
    public static String SERVICEPROTOCOL_URL = U + "funny/serviceProtocol.html";

    // 下载刷新广播,进度
    public final static String UPDATFILE = "com.lsl.aboutus.downloadfile";
    // 下载完成
    public final static String UPDATFILEOK = "com.lsl.aboutus.downloadfile.ok";

    /**
     * 用户登录 { "method": "login", "params": { "username": "aplus", "password":
     * "111111" } }
     */
    public static String[] getLogin() {
        String ret[] = {"login", "login"};
        return ret;
    }

    /**
     * 用户注销 { "method": "logout", "token": "p5aJiUaqxeTbWMfkl3Cu1Ihu2PP0cmCy" }
     */
    public static String[] getLogout() {
        String ret[] = {"login", "logout"};
        return ret;
    }

    /**
     * 发送验证码 { "method": "sendCode", "params": { "mobile": "13800138000" } }
     */
    public static String[] getSendCode() {
        String ret[] = {"login", "sendCode"};
        return ret;
    }

    /**
     * 校验验证码，完成注册 { "method": "register", "params": { "mobile": "18901780827"，
     * "verifyCode": "1234" } }
     */
    public static String[] getRegister() {
        String ret[] = {"login", "register"};
        return ret;
    }
    //
    //    /**
    //     * 设置密码 { "method": "setPassword",
    //     * "token":"Klzt03rdn6snk1vcnfD3asp9TjanPLU1", "params": { "password":
    //     * "password" } }
    //     */
    //    public static String[] getSetPassword() {
    //        String ret[] = {"login", "setPassword"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 找回密码 { "method": "forgotPassword", "params": { "mobile": "18901780827",
    //     * "verifyCode": "1234" } }
    //     */
    //    public static String[] getForgotPassword() {
    //        String ret[] = {"login", "forgotPassword"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 获得当前用户信息 { "method": "info", "token":"fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv" }
    //     */
    //    public static String[] getInfo() {
    //        String ret[] = {"user", "info"};
    //        return ret;
    //    }
    //

    /**
     * 修改个人信息 { "method": "modify", "token": "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv",
     * "params": { "username": "昵称", "sex": "1" } }
     * //     * <p>
     * //     * { "method": "modify", "token": "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv",
     * //     * "params": { "username": "昵称", "sex": "1",
     * //     * <p>
     * //     * "schoolType": "1", // 1：初中，2：高中 "schoolSystem": "1", // 1：五四制，2：六三制
     * //     * "grade": "1", // 1：初一，2：初二，3：初三，4：初四；1：高一，2：高二，3：高三 "semester": "1" //
     * //     * 1：上半学期，2：下半学期 （非必填） "subject"://分科 0不分科1文科2理科 } }
     * //
     */
    public static String[] getModify() {
        String ret[] = {"user", "modify"};
        return ret;
    }

    //    /**
    //     * 用户反馈 { "method": "feedback", "token": "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv",
    //     * "params": { "content": "反馈内容" } }
    //     */
    //    public static String[] getFeedback() {
    //        String ret[] = {"user", "feedback"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 首页课程列表{ "method": "", "token": "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv",
    //     * "params": { } }
    //     */
    //    public static String[] getSubjectList() {
    //        String ret[] = {"lesson", "getSubjectList"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 课程列表 { "method": "getCourseList", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "schoolType": "1",
    //     * "schoolSystem": "1", "grade": "1", "subject": "1" } }
    //     */
    //    public static String[] getCourseList() {
    //        String ret[] = {"lesson", "getCourseList"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 学科详情 { "method": "getCourse", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "courseId": "1" } }
    //     */
    //    public static String[] getCourse() {
    //        String ret[] = {"lesson", "getCourse"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 学期列表 { "method": "getTermList", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "schoolType": "1",
    //     * "schoolSystem": "1", "grade": "1" } }
    //     */
    //    public static String[] getTermList() {
    //        String ret[] = {"lesson", "getTermList"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 学期{ "method": "getTerm", "token": "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv",
    //     * "params": { "id":"1" } }
    //     */
    //    public static String[] getTerm() {
    //        String ret[] = {"lesson", "getTerm"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 选择教材列表{ "method": "getMaterialList", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "courseId":"1" } }
    //     * <p>
    //     * { "deviceId": "设备码", "method": "getMaterialList", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "schoolType": "2",
    //     * "grade": "1" } }
    //     */
    //    public static String[] getMaterialList() {
    //        String ret[] = {"lesson", "getMaterialList"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 课件纠错用户反馈 { "deviceId": "设备码", "method": "feedback", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "content": "反馈内容" } }
    //     */
    //    public static String[] getLessonFeedback() {
    //        String ret[] = {"lesson", "feedback"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 获得教材详情{ "method": "getMaterial", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "materialId":"1" } }
    //     */
    //    public static String[] getMaterial() {
    //        String ret[] = {"lesson", "getMaterial"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 获得单元列表{ "method": "getUnitList", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "materialId":"1" } }
    //     */
    //    public static String[] getUnitList() {
    //        String ret[] = {"lesson", "getUnitList"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 获得单元详情{ "method": "getUnit", "token": "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv",
    //     * "params": { "unitId":"1" } }
    //     */
    //    public static String[] getUnit() {
    //        String ret[] = {"lesson", "getUnit"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 获得课程列表{ "method": "getLessonList", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "unitId":"1" } }
    //     */
    //    public static String[] getLessonList() {
    //        String ret[] = {"lesson", "getLessonList"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 获得课程详情{ "method": "getLesson", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "lessonId":"1" } }
    //     */
    //    public static String[] getLesson() {
    //        String ret[] = {"lesson", "getLesson"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 获得课程视频列表{ "method": "getItemList", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "lessonId":"1" } }
    //     */
    //    public static String[] getItemList() {
    //        String ret[] = {"lesson", "getItemList"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 获得课程视频详情{ "method": "getItem", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "itemId":"1" } }
    //     */
    //    public static String[] getItem() {
    //        String ret[] = {"lesson", "getItem"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 获得教材全部（多级）详情 { "deviceId": "设备码", "method": "getMaterialOverview",
    //     * "token": "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "materialId":
    //     * "6" } }
    //     */
    //    public static String[] getMaterialOverview() {
    //        String ret[] = {"lesson", "getMaterialOverview"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 收藏 { "deviceId": "设备码", "method": "", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "type": "1", "targetId":
    //     * "1" } }
    //     */
    //    public static String[] getAddFavorite() {
    //        String ret[] = {"lesson", "addFavorite"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 取消收藏 { "deviceId": "设备码", "method": "removeFavorite", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "type": "1", "targetId":
    //     * "1" } }
    //     */
    //    public static String[] removeFavorite() {
    //        String ret[] = {"lesson", "removeFavorite"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 批量取消收藏 { "deviceId": "设备码", "method": "removeFavorites", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "type": "1", "targetIds":
    //     * "1,2,3" } }
    //     */
    //    public static String[] removeFavorites() {
    //        String ret[] = {"lesson", "removeFavorites"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 获得单元全部（多级）详情 { "deviceId": "设备码", "method": "getUnitOverview", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "unitId": "1" } }
    //     */
    //    public static String[] getUnitOverview() {
    //        String ret[] = {"lesson", "getUnitOverview"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 设置收藏（显示）的课程 { "deviceId":"设备码", "method": "setCourseFavorited", "token":
    //     * "7UHSIn5HbWiyFmJH7YfZaf6DPTV935z4", "params": {"courseIds": "1,2,3"} }
    //     */
    //    public static String[] getCourseFavorited() {
    //        String ret[] = {"lesson", "setCourseExclude"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 所有学科列表 { "deviceId": "设备码", "method": "`", "token":
    //     * "FOCqtBIHqZspU9WIyCUDm1EHhQz8k0jn", "params": {} }
    //     */
    //    public static String[] getCourseAll() {
    //        String ret[] = {"lesson", "getCourseAll"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 分享视频 { "deviceId": "设备码", "method": "shareItem", "token":
    //     * "q3ePRK9FqmVyuUI4Vtv60Mzh28gh3mvO", "params": { "itemId": "17" } }
    //     */
    //    public static String[] getShareItem() {
    //        String ret[] = {"lesson", "shareItem"};
    //        return ret;
    //    }
    //
    //    /**
    //     * AR Target下的视频信息 { "deviceId": "设备码", "method": "getItems", "token":
    //     * "PICmLYtR2bVjzJgP8c45C2YHH28zxkKl", "params": { "arTarget":
    //     * "fe3a84b8d5394ed784ba8bcc8f685f3c" } }
    //     */
    //    public static String[] getArItems() {
    //        String ret[] = {"ar", "getItems"};
    //        return ret;
    //    }
    //
    //    /**
    //     * AR Target下的视频信息（新） { "deviceId": "867031025008886", "method":
    //     * "getItemsExams", "token": "HpmP4XUjmtZFowk7R2bR51rB6FDlqvb8", "params": {
    //     * "arTarget": "fe3a84b8d5394ed784ba8bcc8f685f3c" } }
    //     */
    //    public static String[] getItemsExams() {
    //        String ret[] = {"ar", "getItemsExams"};
    //        return ret;
    //    }
    //
    //    /**
    //     * AR下的相关教材 { "deviceId": "设备码", "method": "getMaterials", "token":
    //     * "CMPnutFvWTJOxfOUwgKNeVEHyP38xf4E", "params": {} }
    //     */
    //    public static String[] getMaterials() {
    //        String ret[] = {"ar", "getMaterials"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 分享AR { "deviceId": "设备码", "method": "shareAR", "token":
    //     * "q3ePRK9FqmVyuUI4Vtv60Mzh28gh3mvO", "params": { "file": "文件的BASE64编码",
    //     * "type": "jpg" } }
    //     */
    //    public static String[] getShareAR() {
    //        String ret[] = {"ar", "shareAR"};
    //        return ret;
    //    }
    //

    /**
     * 文件上传 { "deviceId": "设备码", "method": "uploadFile", "token":
     * "CMPnutFvWTJOxfOUwgKNeVEHyP38xf4E", "params": { "file": "<文件的BASE64编码>",
     * "type": "jpg" }}
     */
    public static String[] getUploadFile() {
        String ret[] = {"app", "uploadFile"};
        return ret;
    }

    //    /**
    //     * 收藏的视频列表 { "deviceId": "设备码", "method": "getItems", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "group": "time" } }
    //     */
    //    public static String[] getItems() {
    //        String ret[] = {"bag", "getItems"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 收藏的考点列表 { "deviceId": "设备码", "method": "getExams", "token":
    //     * "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv", "params": { "group": "time" } }
    //     */
    //    public static String[] getExams() {
    //        String ret[] = {"bag", "getExams"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 书包下的学科列表 { "deviceId": "设备码", "method": "getCourses", "token":
    //     * "q3ePRK9FqmVyuUI4Vtv60Mzh28gh3mvO", "params": { "subject": 1, "type": 1 }
    //     * }
    //     */
    //    public static String[] getCourses() {
    //        String ret[] = {"bag", "getCourses"};
    //        return ret;
    //    }
    //
    //    /**
    //     * 书包下的教材列表 { "deviceId": "设备码", "method": "getMaterials", "token":
    //     * "CMPnutFvWTJOxfOUwgKNeVEHyP38xf4E", "params": {} } }
    //     */
    //    public static String[] getBagMaterials() {
    //        String ret[] = {"bag", "getMaterials"};
    //        return ret;
    //    }


    /*------诵习练------*/
    //日常练习-学生端
    public static String[] getDailyPractise() {
        String ret[] = {"homework", "getDailyPractice"};
        return ret;
    }

    //题库列表-老师端
    public static String[] getTopicPage() {
        String ret[] = {"topic", "queryTopicPage"};
        return ret;
    }

    //题型列表
    public static String[] getTopicType() {
        String ret[] = {"dict", "queryType"};
        return ret;
    }

    //错题本，错题详情接口（学生端，老师端） //"type"有无都可，似乎默认为"1"，设为"2"的时候报500错误
    public static String[] getWrongBook() {
        String ret[] = {"answerWrongBook", "getAnswerWrongBookList"};
        return ret;
    }

    //学生端-学生端-当次作业下的错题列表
    public static String[] getCurrentWrongBook() { //报错400
        String ret[] = {"answerWrongBook", "getCurrentWrongList"};
        return ret;
    }

    //学生端-题目详情页
    public static String[] queryTopicByCondition() {
        String ret[] = {"topic", "queryTopicByCondition"};
        return ret;
    }

    //    //查询题目详情
    //    public static String[] showTopicDetail(){
    //        String ret[] = {"topic","showTopicDetail"};
    //        return ret;
    //    }

    /**
     * //     * 用户反馈 { "method": "feedback", "token": "fLlbvyL6sYS15vPYm1r9eZhK3Ydx05tv",
     * //     * "params": { "content": "反馈内容" } }
     * //
     */
    public static String[] sendFeedback() {
        String ret[] = {"user", "feedback"};
        return ret;
    }

    /**
     * 首页轮播图
     *
     * @return
     */
    public static String[] queryBroadcastImages() {
        String ret[] = {"broadcastImages", "queryBroadcastImages"};
        return ret;
    }

    /**
     * 通知列表
     */
    public static String[] queryNoticeList() {
        String ret[] = {"notice", "queryNoticeList"};
        return ret;
    }

    /**
     * 通知详情
     */
    public static String[] queryNoticeDetail() {
        String ret[] = {"notice", "queryNoticeDetail"};
        return ret;
    }

    /**
     * 练运算
     */
    public static String[] queryDetailImgs() {
        String ret[] = {"detailImgs", "queryDetailImgs"};
        return ret;
    }

    /**
     * 提交作业
     */
    public static String[] subHomework() {
        String ret[] = {"homework", "subHomework"};
        return ret;
    }
}
