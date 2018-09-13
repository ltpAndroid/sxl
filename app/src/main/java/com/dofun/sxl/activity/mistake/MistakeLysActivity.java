package com.dofun.sxl.activity.mistake;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.bean.Answer;
import com.dofun.sxl.bean.EventBusBean;
import com.dofun.sxl.bean.MistakeList;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.bean.UserInfo;
import com.dofun.sxl.constant.AnswerConstants;
import com.dofun.sxl.constant.EventConstants;
import com.dofun.sxl.fragment.lys.CalculateFragment;
import com.dofun.sxl.fragment.lys.FastFragment;
import com.dofun.sxl.fragment.lys.FillFragment;
import com.dofun.sxl.fragment.lys.PickFragment;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.HintDiaUtils;
import com.dofun.sxl.util.SPUtils;
import com.tandong.sa.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MistakeLysActivity extends BaseActivity {

    @BindView(R.id.iv_back_mistake)
    ImageView ivBack;
    @BindView(R.id.detail_container)
    FrameLayout detailContainer;
    @BindView(R.id.tv_surplus)
    TextView tvSurplus;
    @BindView(R.id.countdown_progress)
    SeekBar countdownProgress;
    @BindView(R.id.btn_next)
    Button btnNext;

    private List<TopicDetail> topicDetails = new ArrayList<>();
    private int i = 0;
    private MyTimer myTimer;

    private MistakeList note;
    private List<Integer> kindList = new ArrayList<>();
    private int position = 0;
    private int kind;
    private ArrayList<TopicDetail> detailList = new ArrayList<>();

    FastFragment fastFragment;
    CalculateFragment calculateFragment;
    FillFragment fillFragment;
    PickFragment pickFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistake_lys);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initView() {
        countdownProgress.setProgress(300);
        countdownProgress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        myTimer = new MyTimer(5 * 60 * 1000, 1000);
        myTimer.start();
    }

    private void initData() {
        note = (MistakeList) getIntent().getSerializableExtra("mistakeNote");
        UserInfo userInfo = (UserInfo) SPUtils.getBaseBean(SPUtils.USER, UserInfo.class);
        JSONObject param = new JSONObject();
        param.put("homeworkId", note.getHomeworkId());
        param.put("userId", userInfo.getId());
        HttpUs.send(Deploy.getCurrentWrongBook(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                topicDetails = JSONArray.parseArray(info.getData(), TopicDetail.class);
                for (TopicDetail topicDetail :
                        topicDetails) {
                    if (!kindList.contains(topicDetail.getKind())) {
                        kindList.add(topicDetail.getKind());
                    }
                }
                initFragment();
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
                initFragment();
            }
        });
    }

    private void initFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideAllFragment(ft);
        kind = kindList.get(position);
        detailList.clear();
        for (TopicDetail topicDetail :
                topicDetails) {
            if (kind == topicDetail.getKind()) {
                detailList.add(topicDetail);
            }
        }
        switch (kind) {
            case 121:
                if (fastFragment == null) {
                    fastFragment = FastFragment.newInstance(detailList);
                    ft.add(R.id.detail_container, fastFragment);
                } else {
                    ft.show(fastFragment);
                }
                break;
            case 122:
                if (calculateFragment == null) {
                    calculateFragment = CalculateFragment.newInstance(detailList);
                    ft.add(R.id.detail_container, calculateFragment);
                } else {
                    ft.show(calculateFragment);
                }
                break;
            case 123:
                if (fillFragment == null) {
                    fillFragment = FillFragment.newInstance(detailList);
                    ft.add(R.id.detail_container, fillFragment);
                } else {
                    ft.show(fillFragment);
                }
                break;
            case 124:
                if (pickFragment == null) {
                    pickFragment = PickFragment.newInstance(detailList);
                    ft.add(R.id.detail_container, pickFragment);
                } else {
                    ft.show(pickFragment);
                }
                break;
        }
        ft.commit();

    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (fastFragment != null) {
            transaction.hide(fastFragment);
        }
        if (calculateFragment != null) {
            transaction.hide(calculateFragment);
        }
        if (fillFragment != null) {
            transaction.hide(fillFragment);
        }
        if (pickFragment != null) {
            transaction.hide(pickFragment);
        }
    }


    int num = 1;

    @OnClick({R.id.iv_back_mistake, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_mistake:
                showMyDialog(R.string.content_topic, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AnswerConstants.lysMap.clear();
                        finish();
                    }
                });
                break;
            case R.id.btn_next:
                if (num < detailList.size() && detailList.size() > 1) {
                    showTip("请先做完当前题型");
                    return;
                }
                sendEvent();
                if (position == kindList.size() - 1) {
                    showMyDialog(R.string.final_toast, null, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            commit();
                        }
                    });
                    return;
                }

                position++;
                initFragment();
                num = 1;

                if (position == kindList.size() - 1) {
                    btnNext.setText("提交");
                    btnNext.setTextColor(setColor(R.color.md_red_500));
                } else {
                    btnNext.setText("下一题");
                    btnNext.setTextColor(setColor(R.color.white));
                }
                break;
        }
    }

    private void commit() {
        String total = "";
        List<Answer> object = new ArrayList<>();
        for (Map.Entry<Object, Object> entry :
                AnswerConstants.lysMap.entrySet()) {
            object.addAll((List<Answer>) entry.getValue());
        }
        for (int i = 0; i < object.size(); i++) {
            total += object.get(i).getTopicId() + "\n";
        }
        showTip(total.substring(0, total.length() - 1));
        JSONObject params = new JSONObject();
        params.put("workId", String.valueOf(note.getHomeworkId()));
        params.put("topicList", object);
        HttpUs.send(Deploy.subHomework(), params, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                AnswerConstants.lysMap.clear();
                HintDiaUtils.createDialog(mContext).showSucceedDialog("提交成功");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    private void sendEvent() {
        switch (kind) {
            case 121:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.LYS_KS, ""));
                break;
            case 122:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.LYS_JS, ""));
                break;
            case 123:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.LYS_TK, ""));
                break;
            case 124:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.LYS_XZ, ""));
                break;
        }
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
            commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTimer.cancel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showMyDialog(R.string.content_topic, null, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AnswerConstants.lysMap.clear();
                    finish();
                }
            });
        }
        return true;
    }


    @Override
    public boolean hasEventBus() {
        return true;
    }

    public void onEventMainThread(EventBusBean bean) {
        switch (bean.getCode()) {
            case EventConstants.LYS_POSITION:
                num = (int) bean.getData();
                break;
        }
    }
}
