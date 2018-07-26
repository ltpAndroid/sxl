package com.dofun.sxl;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class MyApplication extends Application {
    private static MyApplication myApp;

    public static MyApplication getMyApp() {
        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        //初始化AndroidUtilCode
        Utils.init(this);
    }
}
