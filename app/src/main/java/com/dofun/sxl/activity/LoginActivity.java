package com.dofun.sxl.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_to_register)
    TextView btnToRegister;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setStateBarColor();
        btnToRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线

    }

    @Override
    protected void onResume() {
        super.onResume();
        etPhone.setText(SPUtils.getString(SPUtils.UserName, ""));
        etPassword.setText(SPUtils.getString(SPUtils.UserPwd, ""));
    }

    @OnClick({R.id.btn_login, R.id.btn_to_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                final String phone = etPhone.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
                    showTip("账号或密码为空");
                    return;
                }
                JSONObject params = new JSONObject();
                params.put("username", phone);
                params.put("password", password);
                HttpUs.send(Deploy.getLogin(), params, new HttpUs.CallBackImp() {
                    @Override
                    public void onSuccess(ResInfo info) {
                        LogUtils.i(info.toString());
                        showTip(info.getMsg());

                        JSONObject data = JSON.parseObject(info.getData());
                        String token = data.getString("token");
                        SPUtils.setString(SPUtils.TOKEN, token);
                        SPUtils.setString(SPUtils.UserName, phone);
                        SPUtils.setString(SPUtils.UserPwd, password);
                        setUserInfo(data);

                        ActivityUtils.startActivity(MainActivity.class);
                        finish();
                    }

                    @Override
                    public void onFailure(ResInfo info) {
                        LogUtils.i(info.toString());
                        showTip(info.getMsg());
                    }
                }, mContext, "正在登录");

                break;
            case R.id.btn_to_register:
                ActivityUtils.startActivity(RegisterActivity.class);
                break;
        }
    }
}
