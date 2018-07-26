package com.dofun.sxl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.dofun.sxl.activity.BaseActivity;


public class BaseFragment extends BaseLazyFragment {
    public BaseActivity mActivity;

    @Override
    protected void onLazyLoad(View view) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }


    /**
     * 界面跳转
     *
     * @param clz
     */
    public void startToActivity(Class<?> clz) {
        Intent it = new Intent(mActivity, clz);
        startActivity(it);
    }

    public void showTip(String str) {
        ToastUtils.showShort(str);
    }

    public int setColor(int colorId) {
        return mActivity.getResources().getColor(colorId);
    }
}

