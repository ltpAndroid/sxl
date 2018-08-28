package com.dofun.sxl.activity.xhz;

import android.os.Bundle;
import android.widget.TextView;

import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecognizeActivity extends BaseActivity {

    @BindView(R.id.tv_result)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize);
        ButterKnife.bind(this);
        setStateBarColor();

        initData();
    }

    private void initData() {
        String result = getIntent().getStringExtra("result");
        tvResult.setText(result);
    }
}
