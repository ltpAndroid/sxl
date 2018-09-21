package com.dofun.sxl.activity.sjd;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
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
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.bean.Answer;
import com.dofun.sxl.bean.EventBusBean;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.bean.xf.Result;
import com.dofun.sxl.constant.AnswerConstants;
import com.dofun.sxl.constant.EventConstants;
import com.dofun.sxl.fragment.sjd.ChooseFragment;
import com.dofun.sxl.fragment.sjd.GapFillFragment;
import com.dofun.sxl.fragment.sjd.JudgeFragment;
import com.dofun.sxl.fragment.sjd.LigatureFragment;
import com.dofun.sxl.fragment.sjd.ReciteFragment;
import com.dofun.sxl.fragment.sjd.SpellFragment;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.HintDiaUtils;
import com.dofun.sxl.util.SPUtils;
import com.dofun.sxl.util.xf.XmlResultParser;
import com.dofun.sxl.view.DialogWaiting;
import com.hjq.permissions.Permission;
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
    @BindView(R.id.tv_previous)
    TextView tvPrevious;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.countdown_progress)
    SeekBar countdownProgress;
    @BindView(R.id.tv_surplus)
    TextView tvSurplus;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.detail_container)
    FrameLayout detailContainer;
    @BindView(R.id.btn_restart)
    Button btnRestart;
    @BindView(R.id.iv_animation)
    ImageView ivAnimation;
    @BindView(R.id.tv_evaluate)
    TextView tvEvaluate;

    private int i = 0;
    private MyTimer myTimer;

    private int position;
    private int[] sjd00 = new int[]{R.string.jwth00, R.string.sckl00, R.string.ggxs00, R.string.sqxm00, R.string.mbsf00, R.string.ryss00, R.string.syzy00};
    private int[] sjd = new int[]{R.string.jwth, R.string.sckl, R.string.ggxs, R.string.sqxm, R.string.mbsf, R.string.ryss, R.string.syzy};
    private String[] str = new String[]{"一、", "二、", "三、", "四、", "五、", "六、", "七、"};

    private ArrayList<TopicDetail> topicDetails = new ArrayList<>();
    private int[] kinds = new int[]{101, 102, 103, 104, 105, 106, 107};
    private int homeworkId;
    private int fkId;

    GapFillFragment gapFillFragment;
    LigatureFragment ligatureFragment;
    SpellFragment spellFragment;
    ChooseFragment chooseFragment;
    JudgeFragment judgeFragment;
    ReciteFragment reciteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjd_detail);
        ButterKnife.bind(this);

        initData();
        initView();
        checkPer(Permission.RECORD_AUDIO);
        checkPer(Permission.WRITE_EXTERNAL_STORAGE);
    }

    private void initFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideAllFragment(ft);
        switch (position) {
            case 0:
                if (gapFillFragment == null) {
                    gapFillFragment = GapFillFragment.newInstance(topicDetails);
                    ft.add(R.id.detail_container, gapFillFragment);
                } else {
                    ft.show(gapFillFragment);
                }
                break;
            case 1:
                if (ligatureFragment == null) {
                    ligatureFragment = LigatureFragment.newInstance(topicDetails);
                    ft.add(R.id.detail_container, ligatureFragment);
                } else {
                    ft.show(ligatureFragment);
                }
                break;
            case 2:
                if (spellFragment == null) {
                    spellFragment = SpellFragment.newInstance(topicDetails);
                    ft.add(R.id.detail_container, spellFragment);
                } else {
                    ft.show(spellFragment);
                }
                break;
            case 3:
                if (chooseFragment == null) {
                    chooseFragment = ChooseFragment.newInstance(topicDetails);
                    ft.add(R.id.detail_container, chooseFragment);
                } else {
                    ft.show(chooseFragment);
                }
                break;
            case 4:
                if (judgeFragment == null) {
                    judgeFragment = JudgeFragment.newInstance(topicDetails);
                    ft.add(R.id.detail_container, judgeFragment);
                } else {
                    ft.show(judgeFragment);
                }
                break;
            case 5:
                if (reciteFragment == null) {
                    reciteFragment = ReciteFragment.newInstance(topicDetails);
                    ft.add(R.id.detail_container, reciteFragment);
                } else {
                    ft.show(reciteFragment);
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
        if (reciteFragment != null) {
            transaction.hide(reciteFragment);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        countdownProgress.setProgress(20 * 60);
        countdownProgress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        myTimer = new MyTimer(20 * 60 * 1000, 1000);
        myTimer.start();
    }

    private void initData() {

        position = getIntent().getIntExtra("position", 0);
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
                topicDetails = (ArrayList<TopicDetail>) JSONArray.parseArray(info.getData(), TopicDetail.class);
                if (topicDetails.size() > 0) {
                    bindView(topicDetails);
                } else {
                    //showTip("没有布置该题型");
                    topicScore.setText("");
                }
                initFragment();
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getStatus() + ":" + info.getMsg());
                initFragment();
            }
        });
    }

    private void bindView(List<TopicDetail> topicList) {
        int score = 0;
        for (TopicDetail topicDetail :
                topicList) {
            score += topicDetail.getFraction();
        }
        topicScore.setText(String.valueOf(score));
    }

    private void changeViews(int num) {
        setTopicText(num);
        if (num == 5) {
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
        topicType.setText(sjd00[num]);
        topicNum.setText(str[num]);
        topicName.setText(sjd[num]);

    }

    @OnClick({R.id.tv_back_sjd, R.id.tv_previous, R.id.tv_next, R.id.btn_restart, R.id.tv_evaluate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_sjd:
                showMyDialog(R.string.content_topic, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AnswerConstants.sjdMap.clear();
                        finish();
                    }
                });
                break;
            case R.id.tv_previous:
                tvEvaluate.setVisibility(View.INVISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        position--;
                        changeViews(position);
                        queryTopic();
                    }
                }, 500);
                break;
            case R.id.tv_next:
                sendEvent(position);

                if (tvNext.getText().equals("提交")) {
                    commitAnswer();
                    return;
                }

                YoYo.with(Techniques.DropOut)
                        .duration(1500)
                        .withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                ivAnimation.setVisibility(View.VISIBLE);
                                tvNext.setEnabled(false);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ivAnimation.setVisibility(View.GONE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvNext.setEnabled(true);
                                        position++;
                                        changeViews(position);
                                        queryTopic();
                                    }
                                }, 500);
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        })
                        .playOn(ivAnimation);


                break;
            case R.id.btn_restart:
                recreateFragment(position);
                break;
            case R.id.tv_evaluate:
                // 解析最终结果
                if (!TextUtils.isEmpty(mLastResult)) {
                    XmlResultParser resultParser = new XmlResultParser();
                    Result result = resultParser.parse(mLastResult);

                    if (null != result) {
                        //String evaluateResult = result.toString();
                        String duration = SPUtils.getString("recordDuration", "");
                        if (result.is_rejected) {
                            result.total_score = 0;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putFloat("totalScore", result.total_score);
                        bundle.putFloat("integrityScore", result.integrity_score);
                        bundle.putFloat("phone_score", result.phone_score);
                        bundle.putFloat("fluency_score", result.fluency_score);
                        bundle.putFloat("tone_score", result.tone_score);
                        bundle.putString("content", SPUtils.getString("reciteContent", ""));
                        bundle.putString("duration", duration);
                        bundle.putString("result", result.toString());
                        ActivityUtils.startActivity(bundle, EvaluateActivity.class);
                    } else {
                        showTip("解析结果为空");
                    }
                }
                break;
        }
    }

    private void sendEvent(int position) {
        if (topicDetails.size() == 0) {
            return;
        }
        switch (position) {
            case 0:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.SJD_TK, ""));
                break;
            case 1:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.SJD_LX, ""));
                break;
            case 2:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.SJD_PS, ""));
                break;
            case 3:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.SJD_XZ, ""));
                break;
            case 4:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.SJD_PD, ""));
                break;
            case 5:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.SJD_SD, ""));
                break;
        }
    }

    private void recreateFragment(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                ft.remove(gapFillFragment);
                gapFillFragment = null;
                break;
            case 1:
                ft.remove(ligatureFragment);
                ligatureFragment = null;
                break;
            case 2:
                ft.remove(spellFragment);
                spellFragment = null;
                break;
            case 3:
                ft.remove(chooseFragment);
                chooseFragment = null;
                break;
            case 4:
                ft.remove(judgeFragment);
                judgeFragment = null;
                break;
            case 5:
                ft.remove(reciteFragment);
                reciteFragment = null;
                tvEvaluate.setVisibility(View.INVISIBLE);
                break;
        }
        ft.commit();
        final DialogWaiting dialog = DialogWaiting.build(this);
        dialog.show();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //queryTopic();
                initFragment();
                dialog.dimiss();
            }
        }, 100);
    }

    private void commitAnswer() {
        showMyDialog(R.string.final_toast, null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                commit();
            }
        });
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
        params.put("workId", String.valueOf(homeworkId));
        params.put("topicList", object);
        HttpUs.send(Deploy.subHomework(), params, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                //AnswerConstants.sjdMap.clear();
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.FINISH, ""));
                HintDiaUtils.createDialog(mContext).showSucceedDialog("提交成功");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("homeworkId", homeworkId);
                        bundle.putInt("fkId", fkId);
                        ActivityUtils.startActivity(bundle, SjdCheckActivity.class);
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
            countdownProgress.setProgress(20 * 60 - i);
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


    @Override
    public boolean hasEventBus() {
        return true;
    }

    private String mLastResult = "";

    public void onEventMainThread(EventBusBean bean) {
        switch (bean.getCode()) {
            case EventConstants.SD_FINISH:
                tvEvaluate.setVisibility(View.VISIBLE);
                mLastResult = (String) bean.getData();
                break;
        }
    }
}
