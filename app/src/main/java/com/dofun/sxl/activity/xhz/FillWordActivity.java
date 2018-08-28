package com.dofun.sxl.activity.xhz;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FillWordActivity extends BaseActivity {

    @BindView(R.id.tv_back_xhz)
    TextView tvBack;
    @BindView(R.id.topic_layout)
    LinearLayout tvTopic;
    @BindView(R.id.tv_topic_score)
    TextView tvScore;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;

    private List<TopicDetail> topicDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_word);
        ButterKnife.bind(this);
        setStateBarColor();

        initData();
        initView();
    }

    private void initData() {
        int homeworkId = getIntent().getIntExtra("homeworkId", 0);
        int fkId = getIntent().getIntExtra("fkId", 0);
        JSONObject param = new JSONObject();
        param.put("homeworkId", homeworkId);
        param.put("fkId", fkId);
        param.put("kind", 111);
        HttpUs.send(Deploy.queryTopicByCondition(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                topicDetails = JSONArray.parseArray(info.getData(), TopicDetail.class);
                String[] detail = topicDetails.get(0).getDetail().split("@");
                tv1.setText(detail[0]);
                tv2.setText(detail[1]);
                tvScore.setText(topicDetails.get(0).getFraction() + "");
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    private void initView() {

    }

    @OnClick(R.id.tv_back_xhz)
    public void onViewClicked() {
        showMyDialog(R.string.content_topic, null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }
}
