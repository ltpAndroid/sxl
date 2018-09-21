package com.dofun.sxl.activity.xhz;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.activity.StatisticsActivity;
import com.dofun.sxl.bean.EventBusBean;
import com.dofun.sxl.bean.RecogWord;
import com.dofun.sxl.constant.EventConstants;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.view.CircleProgress;
import com.tandong.sa.eventbus.EventBus;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecognizeActivity extends BaseActivity {

    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_back_recognize)
    TextView tvBack;
    @BindView(R.id.total_score)
    CircleProgress totalScore;
    @BindView(R.id.tv_kind_1)
    TextView tvKind1;
    @BindView(R.id.tv_kind_2)
    TextView tvKind2;
    @BindView(R.id.tv_kind_3)
    TextView tvKind3;
    @BindView(R.id.iv_my_word)
    ImageView ivWord;
    @BindView(R.id.tv_again)
    TextView tvAgain;
    @BindView(R.id.tv_look)
    TextView tvLook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        String result = getIntent().getStringExtra("result").trim();
        tvResult.setText(result);
        String filePath = getIntent().getStringExtra("filePath");
        String topicId = getIntent().getStringExtra("topicId");
        JSONObject param = new JSONObject();
        param.put("topicId", topicId);
        param.put("filePath", filePath);
        param.put("score", result);
        HttpUs.send(Deploy.evaluationPicture(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                RecogWord word = JSONObject.parseObject(info.getData(), RecogWord.class);
                bindView(word);
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
                totalScore.setProgress(0);
                tvKind1.setText("评测失败，暂无结果");
                tvKind2.setText("评测失败，暂无结果");
                tvKind3.setText("评测失败，暂无结果");
            }
        });
        Glide.with(this).load(WriteWordActivity.pathName).into(ivWord);
    }

    private void bindView(RecogWord word) {
        //        int score = getNum(80, 100);
        totalScore.setProgress(word.getPoint());
        tvKind1.setText(word.getEvaluate1());
        tvKind2.setText(word.getEvaluate2());
        tvKind3.setText(word.getEvaluate3());
    }

    @OnClick({R.id.tv_back_recognize, R.id.tv_again, R.id.tv_look})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_recognize:
                finish();
                break;
            case R.id.tv_again:
                finish();
                break;
            case R.id.tv_look:
                EventBus.getDefault().post(new EventBusBean<String>(EventConstants.FINISH_CODE, EventConstants.FINISH, ""));

                ActivityUtils.startActivity(StatisticsActivity.class);
                finish();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WriteWordActivity.pathName = "";
    }

    /**
     * 生成一个startNum 到 endNum之间的随机数(不包含endNum的随机数)
     *
     * @param startNum
     * @param endNum
     * @return
     */
    private int getNum(int startNum, int endNum) {
        if (endNum > startNum) {
            Random random = new Random();
            return random.nextInt(endNum - startNum) + startNum;
        }
        return 0;
    }
}
