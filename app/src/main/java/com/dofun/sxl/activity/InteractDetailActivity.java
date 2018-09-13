package com.dofun.sxl.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.Interact;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InteractDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back_interact_detail)
    ImageView ivBack;
    @BindView(R.id.tv_interact_title)
    TextView tvTitle;
    @BindView(R.id.tv_interact_content)
    TextView tvContent;
    @BindView(R.id.tv_interact_publisher)
    TextView tvPublisher;
    @BindView(R.id.tv_interact_time)
    TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interact_detail);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        Interact interact = (Interact) getIntent().getSerializableExtra("interact");
        tvTitle.setText(interact.getTitle());
        tvContent.setText(interact.getContext());
        tvPublisher.setText(interact.getNickname());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = TimeUtils.millis2String(interact.getCreateTime(), format);
        tvTime.setText(time);
    }

    @OnClick(R.id.iv_back_interact_detail)
    public void onViewClicked() {
        finish();
    }
}
