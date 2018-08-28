package com.dofun.sxl.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.dofun.sxl.MyApplication;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.TermBean;
import com.dofun.sxl.bean.UserInfo;
import com.dofun.sxl.util.SPUtils;
import com.tandong.sa.eventbus.EventBus;

import java.util.List;

public class BaseActivity extends AppCompatActivity {
    public Context mContext;
    public BaseActivity mActivity;
    protected Typeface mTfLight;

    public List<Activity> activityList = MyApplication.activityList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (hasEventBus()) {
            EventBus.getDefault().register(this);
        }
        mContext = this;
        mActivity = this;
        activityList.add(mActivity);
        //隐藏标题栏
        getSupportActionBar().hide();

        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
    }

    public void showTip(String str) {
        ToastUtils.showShort(str);
    }

    public void showTip(int resId) {
        ToastUtils.showShort(resId);
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

    /****************************对话框dialog******************************/
    /**
     * 简单的退出确认
     *
     * @param msg String
     */
    public void showMyDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(true)
                .setNegativeButton("取消", null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    /**
     * 简单的退出确认
     *
     * @param resId 资源id
     */
    public void showMyDialog(int resId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(resId)
                .setCancelable(true)
                .setNegativeButton("取消", null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    /**
     * @param resId            内容
     * @param negativeListener 取消键监听
     * @param positiveListener 确认键监听
     */
    public void showMyDialog(int resId, DialogInterface.OnClickListener negativeListener, DialogInterface.OnClickListener positiveListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(resId)
                .setCancelable(false)
                .setNegativeButton("取消", negativeListener)
                .setPositiveButton("确认", positiveListener)
                .show();
    }

    /**
     * 获取输入框的内容
     *
     * @return
     */
    public void showMyDialog(final TextView tv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);
        editText.setHint("请输入新昵称：");
        editText.setTextSize(14);
        builder.setTitle("修改昵称").setView(editText)
                .setCancelable(true)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String content = editText.getText().toString();
                        tv.setText(content);
                    }
                })
                .show();
    }

    /*---------------------------------------------*/
    // 设置用户信息缓存
    public void setUserInfo(JSONObject data) {
        try {
            UserInfo userInfo = data.getObject("user", UserInfo.class);
            SPUtils.setBaseBean(SPUtils.USER, userInfo);

            TermBean term = data.getJSONObject("user").getObject("term",
                    TermBean.class);
            SPUtils.setBaseBean(SPUtils.TERM, term);
            //SPUtils.setBoolean(SPUtils.ISSUBJECT, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用EventBus必须重写此方法，返回true
     */
    public boolean hasEventBus() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hasEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }
}
