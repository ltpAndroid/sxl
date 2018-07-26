package com.dofun.sxl.activity;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.dofun.sxl.R;

public class BaseActivity extends AppCompatActivity {
    private BaseActivity mContext;

    protected Typeface mTfLight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        //隐藏标题栏
        getSupportActionBar().hide();

        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
    }

    public void showTip(String str) {
        ToastUtils.showShort(str);
    }

    public String getStringByID(int resId) {
        return getResources().getString(resId);
    }

    public int setColor(int colorId) {
        return getResources().getColor(colorId);
    }

    /**
     * 设置状态栏颜色
     */
    public void setStateBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.main_color));
        }
    }
}
