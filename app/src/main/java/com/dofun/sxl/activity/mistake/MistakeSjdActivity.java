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
import com.dofun.sxl.fragment.sjd.ChooseFragment;
import com.dofun.sxl.fragment.sjd.GapFillFragment;
import com.dofun.sxl.fragment.sjd.JudgeFragment;
import com.dofun.sxl.fragment.sjd.LigatureFragment;
import com.dofun.sxl.fragment.sjd.SpellFragment;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.HintDiaUtils;
import com.dofun.sxl.util.SPUtils;
import com.dofun.sxl.view.DialogWaiting;
import com.tandong.sa.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MistakeSjdActivity extends BaseActivity {
    @BindView(R.id.iv_back_mistake)
    ImageView ivBack;
    @BindView(R.id.detail_container)
    FrameLayout detailContainer;
    @BindView(R.id.tv_surplus)
    TextView tvSurplus;
    @BindView(R.id.countdown_progress)
    SeekBar countdownProgress;
    @BindView(R.id.btn_restart)
    Button btnRestart;
    @BindView(R.id.tv_previous)
    TextView tvPrevious;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.iv_next)
    ImageView ivNext;

    GapFillFragment gapFillFragment;
    LigatureFragment ligatureFragment;
    SpellFragment spellFragment;
    ChooseFragment chooseFragment;
    JudgeFragment judgeFragment;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_score)
    TextView tvScore;

    private List<TopicDetail> topicDetails = new ArrayList<>();
    private int i = 0;
    private MyTimer myTimer;

    private MistakeList note;
    private List<Integer> kindList = new ArrayList<>();
    private int position = 0;
    private int kind;
    private ArrayList<TopicDetail> detailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistake_sjd);
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
                bindView();
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

    private void bindView() {
        int score = 0;
        switch (kind) {
            case 101:
                tvName.setText(R.string.jwth);
                break;
            case 102:
                tvName.setText(R.string.sckl);
                break;
            case 103:
                tvName.setText(R.string.ggxs);
                break;
            case 104:
                tvName.setText(R.string.sqxm);
                break;
            case 105:
                tvName.setText(R.string.mbsf);
                break;
        }
        for (TopicDetail topicDetail :
                detailList) {
            score += topicDetail.getFraction();
        }
        tvScore.setText(String.valueOf(score));
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
            case 101:
                if (gapFillFragment == null) {
                    gapFillFragment = GapFillFragment.newInstance(detailList);
                    ft.add(R.id.detail_container, gapFillFragment);
                } else {
                    ft.show(gapFillFragment);
                }
                break;
            case 102:
                if (ligatureFragment == null) {
                    ligatureFragment = LigatureFragment.newInstance(detailList);
                    ft.add(R.id.detail_container, ligatureFragment);
                } else {
                    ft.show(ligatureFragment);
                }
                break;
            case 103:
                if (spellFragment == null) {
                    spellFragment = SpellFragment.newInstance(detailList);
                    ft.add(R.id.detail_container, spellFragment);
                } else {
                    ft.show(spellFragment);
                }
                break;
            case 104:
                if (chooseFragment == null) {
                    chooseFragment = ChooseFragment.newInstance(detailList);
                    ft.add(R.id.detail_container, chooseFragment);
                } else {
                    ft.show(chooseFragment);
                }
                break;
            case 105:
                if (judgeFragment == null) {
                    judgeFragment = JudgeFragment.newInstance(detailList);
                    ft.add(R.id.detail_container, judgeFragment);
                } else {
                    ft.show(judgeFragment);
                }
                break;
        }
        ft.commit();

    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (gapFillFragment != null) {
            transaction.hide(gapFillFragment);
        }
        if (ligatureFragment != null) {
            transaction.hide(ligatureFragment);
        }
        if (spellFragment != null) {
            transaction.hide(spellFragment);
        }
        if (chooseFragment != null) {
            transaction.hide(chooseFragment);
        }
        if (judgeFragment != null) {
            transaction.hide(judgeFragment);
        }
    }

    @OnClick({R.id.iv_back_mistake, R.id.btn_restart, R.id.tv_previous, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_mistake:
                showMyDialog(R.string.content_topic, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AnswerConstants.sjdMap.clear();
                        finish();
                    }
                });
                break;
            case R.id.btn_restart:
                showMyDialog(R.string.do_again, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recreateFragment(kind);
                    }
                });
                break;
            case R.id.tv_previous:
                if (position == 0) {
                    showTip("当前已是第一题");
                    return;
                }
                position--;
                initFragment();
                bindView();

                tvNext.setText("下一题");
                ivNext.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_next:
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
                bindView();

                if (position == kindList.size() - 1) {
                    tvNext.setText("提交");
                    ivNext.setVisibility(View.GONE);
                } else {
                    tvNext.setText("下一题");
                    ivNext.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void sendEvent() {
        switch (kind) {
            case 101:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.SJD_TK, ""));
                break;
            case 102:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.SJD_LX, ""));
                break;
            case 103:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.SJD_PS, ""));
                break;
            case 104:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.SJD_XZ, ""));
                break;
            case 105:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.SJD_PD, ""));
                break;
        }
    }

    private void commit() {
        String total = "";
        List<Answer> object = new ArrayList<>();
        for (Map.Entry<Object, Object> entry :
                AnswerConstants.sjdMap.entrySet()) {
            object.addAll((List<Answer>) entry.getValue());
        }
        for (int i = 0; i < object.size(); i++) {
            total += object.get(i).getTopicId() + "\n";
        }
        //showTip(total.substring(0, total.length() - 1));
        JSONObject params = new JSONObject();
        params.put("workId", String.valueOf(note.getHomeworkId()));
        params.put("topicList", object);
        HttpUs.send(Deploy.subHomework(), params, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                AnswerConstants.sjdMap.clear();
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

    private void recreateFragment(int kind) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (kind) {
            case 101:
                ft.remove(gapFillFragment);
                gapFillFragment = null;
                break;
            case 102:
                ft.remove(ligatureFragment);
                ligatureFragment = null;
                break;
            case 103:
                ft.remove(spellFragment);
                spellFragment = null;
                break;
            case 104:
                ft.remove(chooseFragment);
                chooseFragment = null;
                break;
            case 105:
                ft.remove(judgeFragment);
                judgeFragment = null;
                break;
        }
        ft.commit();
        final DialogWaiting dialog = DialogWaiting.build(this);
        dialog.show();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                initFragment();
                dialog.dimiss();
            }
        }, 100);
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
                    AnswerConstants.sjdMap.clear();
                    finish();
                }
            });
        }
        return true;
    }
}
