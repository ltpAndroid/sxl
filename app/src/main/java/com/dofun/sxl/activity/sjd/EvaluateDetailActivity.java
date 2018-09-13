package com.dofun.sxl.activity.sjd;

import android.os.Bundle;
import android.widget.TextView;

import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EvaluateDetailActivity extends BaseActivity {

    @BindView(R.id.tv_back_evaluate_detail)
    TextView tvBack;
    @BindView(R.id.tv_detail_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_detail);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        String content = getIntent().getStringExtra("detail");
        tvContent.setText(content);
    }

    @OnClick(R.id.tv_back_evaluate_detail)
    public void onViewClicked() {
        finish();
    }
}
