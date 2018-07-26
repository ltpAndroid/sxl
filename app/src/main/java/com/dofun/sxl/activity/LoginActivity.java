package com.dofun.sxl.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.dofun.sxl.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_to_register)
    TextView btnToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btnToRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
    }


    @OnClick({R.id.btn_login, R.id.btn_to_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                ActivityUtils.startActivity(MainActivity.class);
                finish();
                break;
            case R.id.btn_to_register:
                ActivityUtils.startActivity(RegisterActivity.class);
                break;
        }
    }
}
