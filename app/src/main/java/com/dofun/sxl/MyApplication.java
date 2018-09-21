package com.dofun.sxl;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.Utils;
import com.dofun.sxl.activity.LoginActivity;
import com.dofun.sxl.constant.AnswerConstants;
import com.dofun.sxl.util.SPUtils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private static MyApplication myApp;

    public static MyApplication getMyApp() {
        return myApp;
    }

    public static List<AppCompatActivity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        //初始化AndroidUtilCode
        Utils.init(this);
        //初始化SP
        SPUtils.setSP(myApp);
        //初始化讯飞SDK
        initSpeech();
    }

    private void initSpeech() {//5b2cc6ed
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5b84ced1");
    }

    //退出应用
    public static void exitApp() {
        try {
            for (Activity ac : activityList) {
                ac.finish();
            }
            activityList.clear();
            AnswerConstants.lysMap.clear();
            AnswerConstants.sjdMap.clear();
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
