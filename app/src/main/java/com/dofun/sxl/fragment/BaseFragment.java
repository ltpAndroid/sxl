package com.dofun.sxl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.dofun.sxl.activity.BaseActivity;
import com.tandong.sa.eventbus.EventBus;

import java.lang.reflect.Method;


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
        if (hasEventBus()) {
            EventBus.getDefault().register(this);
        }

    }

    /**
     * 使用EventBus必须重写此方法，返回true
     */
    public boolean hasEventBus() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hasEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }


    public void disableShowInput(final EditText editText) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            editText.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method method;
            try {
                method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {//TODO: handle exception
            }
            try {
                method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                method.setAccessible(true);
                method.invoke(editText, false);
            } catch (Exception e) {//TODO: handle exception
            }
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editText.setSelection(editText.length());
            }
        });
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


    /********************dialog*******************/

}

