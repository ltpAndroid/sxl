package com.dofun.sxl.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dofun.sxl.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setStateBarColor();
        btnToLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
    }

    @OnClick({R.id.tv_verCode, R.id.btn_register, R.id.btn_to_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_verCode:
                showTip("获取验证码");
                break;
            case R.id.btn_register:

                break;
            case R.id.btn_to_login:
                finish();
                break;
        }
    }
}
