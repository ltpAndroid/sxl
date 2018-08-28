package com.dofun.sxl.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.Notice;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoticeDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back_notice_detail)
    ImageView ivBack;
    @BindView(R.id.tv_notice_title)
    TextView tvTitle;
    @BindView(R.id.tv_notice_content)
    TextView tvContent;
    @BindView(R.id.tv_notice_publisher)
    TextView tvPublisher;
    @BindView(R.id.tv_notice_time)
    TextView tvTime;

    private Notice notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        ButterKnife.bind(this);
        setStateBarColor();

        initData();
        initView();
    }

    private void initData() {
        notice = (Notice) getIntent().getSerializableExtra("notice");
        int noticeId = notice.getId();
        JSONObject param = new JSONObject();
        param.put("id", noticeId);
        HttpUs.send(Deploy.queryNoticeDetail(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    private void initView() {
        tvTitle.setText(notice.getTitle());
        tvContent.setText(notice.getContent());
        tvPublisher.setText(notice.getPublisher());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = TimeUtils.millis2String(notice.getPublishTime(), format);
        tvTime.setText(time);
    }

    @OnClick(R.id.iv_back_notice_detail)
    public void onViewClicked() {
        finish();
    }
}
