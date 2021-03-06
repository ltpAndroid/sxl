package com.dofun.sxl.activity.lys;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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
import com.dofun.sxl.constant.AnswerConstants;
import com.dofun.sxl.constant.EventConstants;
import com.dofun.sxl.fragment.lys.CalculateFragment;
import com.dofun.sxl.fragment.lys.FastFragment;
import com.dofun.sxl.fragment.lys.FillFragment;
import com.dofun.sxl.fragment.lys.PickFragment;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.HintDiaUtils;
import com.dofun.sxl.view.DialogWaiting;
import com.tandong.sa.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LysDetailActivity extends BaseActivity {

    @BindView(R.id.topic_type)
    TextView tvTopicType;
    @BindView(R.id.tv_back_lys)
    TextView tvBack;
    @BindView(R.id.detail_container)
    FrameLayout detailContainer;
    @BindView(R.id.tv_surplus)
    TextView tvSurplus;
    @BindView(R.id.countdown_progress)
    SeekBar countdownProgress;
    @BindView(R.id.rl_previous)
    RelativeLayout rlPrevious;
    @BindView(R.id.rl_next)
    RelativeLayout rlNext;
    @BindView(R.id.tv_next_one)
    TextView tvNextOne;
    @BindView(R.id.iv_animation)
    ImageView ivAnimation;

    private int i = 0;
    private MyTimer myTimer;

    private ArrayList<TopicDetail> topicDetails = new ArrayList<>();
    private int[] kinds = new int[]{121, 122, 123, 124};
    private int[] lys = {R.string.lys_ks, R.string.lys_js, R.string.lys_tk, R.string.lys_xz};
    private int homeworkId;
    private int fkId;
    private int position;

    FastFragment fastFragment;
    CalculateFragment calculateFragment;
    FillFragment fillFragment;
    PickFragment pickFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lys_detail);
        ButterKnife.bind(this);

        initView();
        initData();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {

        countdownProgress.setProgress(15 * 60);
        countdownProgress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        myTimer = new MyTimer(15 * 60 * 1000, 1000);
        myTimer.start();
    }

    private void initFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideAllFragment(ft);
        switch (position) {
            case 0:
                if (fastFragment == null) {
                    fastFragment = FastFragment.newInstance(topicDetails);
                    ft.add(R.id.detail_container, fastFragment);
                } else {
                    ft.show(fastFragment);
                }
                break;
            case 1:
                if (calculateFragment == null) {
                    calculateFragment = CalculateFragment.newInstance(topicDetails);
                    ft.add(R.id.detail_container, calculateFragment);
                } else {
                    ft.show(calculateFragment);
                }
                break;
            case 2:
                if (fillFragment == null) {
                    fillFragment = FillFragment.newInstance(topicDetails);
                    ft.add(R.id.detail_container, fillFragment);
                } else {
                    ft.show(fillFragment);
                }
                break;
            case 3:
                if (pickFragment == null) {
                    pickFragment = PickFragment.newInstance(topicDetails);
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

    private void initData() {

        position = getIntent().getIntExtra("position", 0);
        changeViews(position);

        homeworkId = getIntent().getIntExtra("homeworkId", 0);
        fkId = getIntent().getIntExtra("fkId", 0);

        queryTopic();
    }

    private void queryTopic() {
        int kind = kinds[position];
        JSONObject param = new JSONObject();
        param.put("homeworkId", homeworkId);
        param.put("fkId", fkId);
        param.put("kind", kind);
        HttpUs.send(Deploy.queryTopicByCondition(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                topicDetails = (ArrayList<TopicDetail>) JSONObject.parseArray(info.getData(), TopicDetail.class);
                //                if (topicDetails.size() > 1) {
                //                    rlNext.setEnabled(false);
                //                }
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

    private void changeViews(int num) {
        tvTopicType.setText(lys[num]);
        if (num == 3) {
            tvNextOne.setText("提交");
            tvNextOne.setTextColor(setColor(R.color.md_red_500));
        } else {
            tvNextOne.setText("下一题");
            tvNextOne.setTextColor(setColor(R.color.white));
        }
    }

    int num = 1;

    @OnClick({R.id.tv_back_lys, R.id.rl_previous, R.id.rl_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_lys:
                showMyDialog(R.string.content_topic, null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AnswerConstants.lysMap.clear();
                        finish();
                    }
                });
                break;
            case R.id.rl_previous:
                if (position == 0) {
                    showTip("已是第一题");
                    return;
                }

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
                num = topicDetails.size();
                break;
            case R.id.rl_next:
                if (num < topicDetails.size() && topicDetails.size() > 1) {
                    showTip("请先做完当前题型");
                    return;
                }

                sendEvent(position);

                if (position == 3) {
                    commitAnswer();
                    return;
                }


                YoYo.with(Techniques.DropOut)
                        .duration(2000)
                        .withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                ivAnimation.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                ivAnimation.setVisibility(View.GONE);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
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


                num = 1;
                break;
        }
    }

    private void sendEvent(int position) {
        switch (position) {
            case 0:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.LYS_KS, ""));
                break;
            case 1:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.LYS_JS, ""));
                break;
            case 2:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.LYS_TK, ""));
                break;
            case 3:
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.LYS_XZ, ""));
                break;
        }
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
                AnswerConstants.lysMap.entrySet()) {
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
                //AnswerConstants.lysMap.clear();
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.FINISH, ""));
                HintDiaUtils.createDialog(mContext).showSucceedDialog("提交成功");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle bundle = new Bundle();
                        bundle.putInt("homeworkId", homeworkId);
                        bundle.putInt("fkId", fkId);
                        ActivityUtils.startActivity(bundle, LysCheckActivity.class);
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
            countdownProgress.setProgress(15 * 60 - i);
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
    protected void onDestroy() {
        super.onDestroy();
        myTimer.cancel();
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
