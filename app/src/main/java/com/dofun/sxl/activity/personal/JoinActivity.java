package com.dofun.sxl.activity.personal;

import android.os.Bundle;
import android.widget.TextView;

import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinActivity extends BaseActivity {

    @BindView(R.id.tv_back_join)
    TextView tvBackJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_back_join)
    public void onViewClicked() {
        finish();
    }
}
