package com.dofun.sxl.fragment.sjd;


import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.dofun.sxl.R;
import com.dofun.sxl.bean.Answer;
import com.dofun.sxl.bean.EventBusBean;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.constant.AnswerConstants;
import com.dofun.sxl.constant.EventConstants;
import com.dofun.sxl.fragment.BaseFragment;
import com.dofun.sxl.util.SPUtils;
import com.dofun.sxl.util.TimeTool;
import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.EvaluatorResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvaluator;
import com.tandong.sa.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReciteFragment extends BaseFragment {

    @BindView(R.id.tv_recite_content)
    TextView tvContent;
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @BindView(R.id.start_record)
    LinearLayout llStart;
    @BindView(R.id.stop_record)
    LinearLayout llStop;
    Unbinder unbinder;

    private List<TopicDetail> detailList = new ArrayList<>();

    private long duration = 0;
    private Timer timer;

    private SpeechEvaluator evaluator;
    private String content = "";
    private String mLastResult;

    public ReciteFragment() {
        // Required empty public constructor
    }

    public static ReciteFragment newInstance(ArrayList<TopicDetail> topicDetails) {
        ReciteFragment fragment = new ReciteFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", topicDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            ArrayList<TopicDetail> list = (ArrayList<TopicDetail>) args.getSerializable("data");
            detailList.clear();
            detailList.addAll(list);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recite, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        evaluator = SpeechEvaluator.createEvaluator(mActivity, null);
    }

    @Override
    protected void onLazyLoad(View view) {
        super.onLazyLoad(view);
        if (detailList.size() == 0) {
            showTip("没有布置该题型");
            return;
        }
        tvContent.setText(detailList.get(0).getDetail());
        content = tvContent.getText().toString();
        SPUtils.setString("reciteContent", content);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        if (null != evaluator) {
            evaluator.destroy();
            evaluator = null;
        }
    }

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

    @OnClick({R.id.start_record, R.id.stop_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_record:
                if (EmptyUtils.isEmpty(content)) {
                    showTip("无朗诵内容");
                    return;
                }

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
                llStart.setEnabled(false);
                break;
            case R.id.stop_record:
                stopRecord();

                if (timer == null) {
                    return;
                }
                handler.sendEmptyMessage(1);
                duration = 0;
                llStart.setEnabled(true);
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
        evaluator.setParameter("plev", "0");
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

                llStop.performClick();
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.SD_FINISH, mLastResult));
                SPUtils.setString("recordDuration", tvDuration.getText().toString());
                showTip("录音结束");
            }
        }

        @Override
        public void onError(SpeechError error) {

            if (error != null) {
                showTip("error:" + error.getErrorCode() + ","
                        + error.getErrorDescription());
                llStop.performClick();//8-21
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
    public boolean hasEventBus() {
        return true;
    }

    public void onEventMainThread(EventBusBean bean) {
        switch (bean.getCode()) {
            case EventConstants.SJD_SD:
                List<Answer> list = new ArrayList<>();
                TopicDetail topicDetail = detailList.get(0);
                Answer answerBean = new Answer();
                answerBean.setTopicId(topicDetail.getId() + "");
                answerBean.setAnswer(topicDetail.getAnalysis());
                answerBean.setAnswerU("");
                answerBean.setScore(topicDetail.getFraction() + "");
                answerBean.setIsRight("1");
                list.add(answerBean);
                AnswerConstants.setSjdAnswer(106, list);
                break;
        }
    }
}
