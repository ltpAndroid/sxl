package com.dofun.sxl.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.HintDiaUtils;
import com.dofun.sxl.util.SPUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tv_verCode)
    TextView tvVerCode;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_to_login)
    TextView btnToLogin;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_code)
    EditText etCode;

    private String phone;
    private String password;
    private String code;

    private MyCountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        //btnToLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
    }

    @OnClick({R.id.tv_verCode, R.id.btn_register, R.id.btn_to_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_verCode:
                getCode();
                break;
            case R.id.btn_register:
                doRegister();
                break;
            case R.id.btn_to_login:
                finish();
                break;
        }
    }

    private void getCode() {
        phone = etPhone.getText().toString();
        if (StringUtils.isEmpty(phone)) {
            showTip("请输入手机号");
            return;
        }
        if (!RegexUtils.isMobileExact(phone)) {
            showTip("请输入正确手机号");
            return;
        }
        countDownTimer = new MyCountDownTimer(60 * 1000, 1000);
        countDownTimer.start();

        JSONObject param = new JSONObject();
        param.put("mobile", phone);
        HttpUs.send(Deploy.getSendCode(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                final HintDiaUtils dialog = HintDiaUtils.createDialog(mContext);
                dialog.showSucceedDialog("已发送");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 500);
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    private void doRegister() {
        //非空验证
        phone = etPhone.getText().toString();
        password = etPassword.getText().toString();
        code = etCode.getText().toString();
        if (StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            showTip("请填写完整信息");
            return;
        }
        //验证手机号
        if (!RegexUtils.isMobileExact(phone)) {
            showTip("请输入正确手机号");
            return;
        }
        //密码至少6位
        if (password.length() < 6) {
            showTip("密码长度至少6位");
            return;
        }

        JSONObject param = new JSONObject();
        param.put("mobile", phone);
        param.put("verifyCode", code);
        HttpUs.send(Deploy.getRegister(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                JSONObject data = JSONObject.parseObject(info.getData());
                String token = data.getString("token");
                SPUtils.setString(SPUtils.TOKEN, token);

                JSONObject params = new JSONObject();
                params.put("password", password);
                HttpUs.send(Deploy.getSetPassword(), params, new HttpUs.CallBackImp() {
                    @Override
                    public void onSuccess(ResInfo info) {
                        LogUtils.i(info.toString());
                        SPUtils.setString(SPUtils.UserName, phone);
                        SPUtils.setString(SPUtils.UserPwd, password);
                        HintDiaUtils.createDialog(mContext).showSucceedDialog("注册成功");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 500);
                    }

                    @Override
                    public void onFailure(ResInfo info) {
                        LogUtils.i(info.toString());
                        showTip(info.getMsg());
                    }
                });
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    class MyCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvVerCode.setEnabled(false);
            tvVerCode.setTextColor(setColor(R.color.md_grey_500));
            DateFormat format = new SimpleDateFormat("mm:ss");
            String surplusTime = TimeUtils.millis2String(millisUntilFinished, format);
            tvVerCode.setText(surplusTime);
        }

        @Override
        public void onFinish() {
            tvVerCode.setText("重新获取");
            tvVerCode.setTextColor(Color.parseColor("#00c68c"));
            tvVerCode.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
