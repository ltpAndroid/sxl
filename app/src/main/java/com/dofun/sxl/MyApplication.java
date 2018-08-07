package com.dofun.sxl;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.blankj.utilcode.util.Utils;
import com.dofun.sxl.activity.LoginActivity;
import com.dofun.sxl.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private static MyApplication myApp;

    public static MyApplication getMyApp() {
        return myApp;
    }

    public static List<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        //初始化AndroidUtilCode
        Utils.init(this);

        SPUtils.setSP(myApp);
    }

    //退出应用
    public static void exitApp() {
        try {
            for (Activity ac : activityList) {
                ac.finish();
            }
            activityList.clear();
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //重新登录
    public static void toLogin() {
        try {
            //SPUtils.setBoolean(SPUtils.ISLOGIN,false);
            Intent intent = new Intent(myApp, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            myApp.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
