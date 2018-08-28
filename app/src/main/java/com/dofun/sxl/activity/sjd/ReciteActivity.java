package com.dofun.sxl.activity.sjd;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.bean.xf.Result;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.TimeTool;
import com.dofun.sxl.util.xf.XmlResultParser;
import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReciteActivity extends BaseActivity {

    @BindView(R.id.tv_back_recite)
    TextView tvBack;
    @BindView(R.id.tv_topic_score)
    TextView tvScore;
    @BindView(R.id.tv_recite_content)
    TextView tvContent;
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @BindView(R.id.start_record)
    TextView tvStart;
    @BindView(R.id.stop_record)
    TextView tvStop;
    @BindView(R.id.tv_evaluate)
    TextView tvEvaluate;

    private long duration = 0;
    private Timer timer;

    private SpeechEvaluator evaluator;
    private String content = "";
    private String mLastResult;
    public static int count = 2;//此题总计做3次

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recite);
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
        param.put("kind", 106);
        HttpUs.send(Deploy.queryTopicByCondition(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                List<TopicDetail> detailList = JSONArray.parseArray(info.getData(), TopicDetail.class);
                if (detailList.size() == 0) {
                    showTip("没有布置该题型");
                    return;
                }
                String detail = detailList.get(0).getDetail();
                tvContent.setText(detail);
                tvScore.setText(detailList.get(0).getFraction());
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });

    }

    private void initView() {
        evaluator = SpeechEvaluator.createEvaluator(ReciteActivity.this, null);
        content = tvContent.getText().toString();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    tvDuration.setText((String) msg.obj);
                    break;
                case 1:
                    timer.cancel();
                    break;

            }
        }
    };

    @OnClick({R.id.tv_back_recite, R.id.start_record, R.id.stop_record, R.id.tv_evaluate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_recite:
                finish();
                break;
            case R.id.start_record:
                if (count == 0) {
                    showTip("重录次数已用尽");
                    return;
                }
                tvEvaluate.setVisibility(View.INVISIBLE);
                startRecord();

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        duration += 1000;
                        String time = TimeTool.getFormatHMS(duration);
                        Message msg = handler.obtainMessage();
                        msg.obj = time;
                        handler.sendMessage(msg);
                    }
                }, 2000, 1000);
                tvStart.setEnabled(false);
                break;
            case R.id.stop_record:
                stopRecord();

                if (timer == null) {
                    return;
                }
                handler.sendEmptyMessage(1);
                duration = 0;
                tvStart.setEnabled(true);
                break;
            case R.id.tv_evaluate:
                // 解析最终结果
                if (!TextUtils.isEmpty(mLastResult)) {
                    XmlResultParser resultParser = new XmlResultParser();
                    Result result = resultParser.parse(mLastResult);

                    if (null != result) {
                        //String evaluateResult = result.toString();
                        String duration = tvDuration.getText().toString();
                        float totalScore = result.total_score;
                        float integrityScore = result.integrity_score;
                        Bundle bundle = new Bundle();
                        bundle.putFloat("totalScore", totalScore);
                        bundle.putFloat("integrityScore", integrityScore);
                        bundle.putString("content", content);
                        bundle.putString("duration", duration);
                        ActivityUtils.startActivity(bundle, EvaluateActivity.class);
                    } else {
                        showTip("解析结果为空");
                    }
                }
                break;
        }
    }

    private void stopRecord() {
        if (evaluator.isEvaluating()) {
            evaluator.stopEvaluating();
        }
    }

    private void startRecord() {
        if (evaluator == null) {
            return;
        }
        setParams();
        evaluator.startEvaluating(content, null, mEvaluatorListener);
    }

    private void setParams() {
        evaluator.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        evaluator.setParameter(SpeechConstant.ISE_CATEGORY, "read_sentence");
        evaluator.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        evaluator.setParameter(SpeechConstant.RESULT_LEVEL, "complete");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        evaluator.setParameter(SpeechConstant.VAD_BOS, "5000");
        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        evaluator.setParameter(SpeechConstant.VAD_EOS, "2000");
        // 语音输入超时时间，即用户最多可以连续说多长时间；
        evaluator.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, "-1");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        evaluator.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/msc/ise.wav";
        evaluator.setParameter(SpeechConstant.ISE_AUDIO_PATH, path);
    }

    // 评测监听接口
    private EvaluatorListener mEvaluatorListener = new EvaluatorListener() {

        @Override
        public void onResult(EvaluatorResult result, boolean isLast) {
            LogUtils.i("evaluator result :" + isLast);

            if (isLast) {// isLast为true，表示最终结果
                StringBuilder builder = new StringBuilder();
                builder.append(result.getResultString());
                mLastResult = builder.toString();

                tvStop.performClick();
                tvEvaluate.setVisibility(View.VISIBLE);

                showTip("录音结束");
            }
        }

        @Override
        public void onError(SpeechError error) {

            if (error != null) {
                showTip("error:" + error.getErrorCode() + ","
                        + error.getErrorDescription());
                tvStop.performClick();//8-21
            } else {
                LogUtils.i("evaluator over");
            }
        }

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            LogUtils.i("evaluator begin");
            showTip("请开始朗读");
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            LogUtils.i("evaluator stoped");
            showTip("朗读结束");
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            // showTip("当前音量：" + volume);
            LogUtils.i("返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            // String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            // Log.d(TAG, "session id =" + sid);
            // }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (null != evaluator) {
            evaluator.destroy();
            evaluator = null;
        }
    }
}
