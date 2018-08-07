package com.dofun.sxl.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.view.DialogWaiting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SjdDetailActivity extends BaseActivity {

    @BindView(R.id.topic_type)
    TextView topicType;
    @BindView(R.id.tv_back_sjd)
    TextView tvBack;
    @BindView(R.id.tv_topic_num)
    TextView topicNum;
    @BindView(R.id.tv_topic_name)
    TextView topicName;
    @BindView(R.id.tv_topic_score)
    TextView topicScore;
    @BindView(R.id.tv_topic_diff)
    TextView topicDiff;
    @BindView(R.id.tv_previous)
    TextView tvPrevious;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.countdown_progress)
    ProgressBar countdownProgress;
    @BindView(R.id.tv_surplus)
    TextView tvSurplus;
    @BindView(R.id.iv_next)
    ImageView ivNext;

    private int i = 0;
    private MyTimer myTimer;

    private String type;
    private int position;
    private int[] sjd00 = new int[]{R.string.jwth00, R.string.sckl00, R.string.ggxs00, R.string.sqxm00, R.string.mbsf00, R.string.ryss00, R.string.syzy00};
    private int[] sjd = new int[]{R.string.jwth, R.string.sckl, R.string.ggxs, R.string.sqxm, R.string.mbsf, R.string.ryss, R.string.syzy};
    private String[] str = new String[]{"一、", "二、", "三、", "四、", "五、", "六、", "七、"};
    private int[] lys = new int[]{R.string.lys_tk, R.string.lys_lx, R.string.lys_pd, R.string.lys_xz, R.string.lys_js, R.string.lys_yy, R.string.lys_ht};
    private int[] xhz = new int[]{R.string.xhz_tk, R.string.xhz_lm};
    private final String TYPE_SJD = "question_types";
    private final String TYPE_XHZ = "characters_type";
    private final String TYPE_LYS = "operation_type";

    private List<TopicDetail> topicDetails = new ArrayList<>();
    private int[] kinds;
    private int homeworkId;
    private int fkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjd_detail);
        ButterKnife.bind(this);
        setStateBarColor();

        initData();
        initView();
    }

    private void initView() {
        countdownProgress.setProgress(300);
        myTimer = new MyTimer(5 * 60 * 1000, 1000);
        myTimer.start();
    }

    private void initData() {
        position = getIntent().getIntExtra("position", 0);
        type = getIntent().getStringExtra("type");
        changeViews(position);

        homeworkId = getIntent().getIntExtra("homeworkId", 0);
        fkId = getIntent().getIntExtra("fkId", 0);
        queryTopic();
    }

    private void queryTopic() {
        int kind = kinds[position];/*getIntent().getIntExtra("kind",101)*/
        JSONObject param = new JSONObject();
        param.put("homeworkId", homeworkId);
        param.put("fkId", fkId);
        param.put("kind", kind);
        HttpUs.send(Deploy.queryTopicByCondition(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                topicDetails = JSONArray.parseArray(info.getData(), TopicDetail.class);
                if (topicDetails.size() > 0) {
                    bindView(topicDetails);
                } else {
                    showTip("没有布置该题型");
                    topicDiff.setText("");
                    topicScore.setText("");
                }
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    private void bindView(List<TopicDetail> topicList) {
        TopicDetail topicDetail = topicList.get(position);
        topicDiff.setText(topicDetail.getDifficultyDegree() + "");
        topicScore.setText(topicDetail.getFraction() + "");
    }

    private void changeViews(int num) {
        setTopicText(num);
        if ((type.equals(TYPE_SJD) && num == 4)
                || (type.equals(TYPE_XHZ) && num == 1)
                || (type.equals(TYPE_LYS) && num == 6)) {
            tvNext.setText("提交");
            ivNext.setVisibility(View.GONE);
        } else {
            tvNext.setText("下一题");
            ivNext.setVisibility(View.VISIBLE);
        }
        if (num == 0) {
            tvPrevious.setVisibility(View.INVISIBLE);
        } else {
            tvPrevious.setVisibility(View.VISIBLE);
        }
    }

    private void setTopicText(int num) {
        switch (type) {
            case TYPE_SJD:
                topicType.setText(sjd00[num]);
                topicNum.setText(str[num]);
                topicName.setText(sjd[num]);
                kinds = new int[]{101, 102, 103, 104, 105, 106, 107};
                break;
            case TYPE_XHZ:
                topicType.setText(xhz[num]);
                topicNum.setText(str[num]);
                topicName.setText(xhz[num]);
                kinds = new int[]{111, 112};
                break;
            case TYPE_LYS:
                topicType.setText(lys[num]);
                topicNum.setText(str[num]);
                topicName.setText(lys[num]);
                kinds = new int[]{121, 122, 123, 124, 125, 126, 127};
                break;
        }
    }

    @OnClick({R.id.tv_back_sjd, R.id.tv_previous, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_sjd:
                showMyDialog(R.string.content_topic);
                break;
            case R.id.tv_previous:
                final DialogWaiting dialog = DialogWaiting.build(this);
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dimiss();
                        position--;
                        changeViews(position);
                        queryTopic();
                    }
                }, 500);
                break;
            case R.id.tv_next:
                if (tvNext.getText().equals("提交")) {
                    commitAnswer();
                    return;
                }
                final DialogWaiting dialogNext = DialogWaiting.build(this);
                dialogNext.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogNext.dimiss();
                        position++;
                        changeViews(position);
                        queryTopic();
                    }
                }, 500);
                break;
        }
    }

    private void commitAnswer() {
        showTip("已提交");
    }

    class MyTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            i++;
            countdownProgress.setProgress(300 - i);
            DateFormat format = new SimpleDateFormat("mm:ss");
            String surplusTime = TimeUtils.millis2String(millisUntilFinished, format);
            String time[] = surplusTime.split(":");
            tvSurplus.setText(time[0] + "分" + time[1] + "秒");
        }

        @Override
        public void onFinish() {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTimer.cancel();
    }
}
