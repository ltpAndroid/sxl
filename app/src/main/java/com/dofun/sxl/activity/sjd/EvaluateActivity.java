package com.dofun.sxl.activity.sjd;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.view.CircleProgress;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EvaluateActivity extends BaseActivity {

    @BindView(R.id.tv_back_evaluate)
    TextView tvBack;
    @BindView(R.id.final_score)
    CircleProgress finalScore;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.tv_fluency)
    TextView tvFluency;
    @BindView(R.id.tv_integrity)
    TextView tvIntegrity;
    @BindView(R.id.tv_accuracy)
    TextView tvAccuracy;
    @BindView(R.id.tv_standard)
    TextView tvStandard;
    @BindView(R.id.iv_play_or_pause)
    ImageView btnVoice;
    @BindView(R.id.tv_current_time)
    TextView tvCurrentTime;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.tv_total_time)
    TextView tvTotalTime;
    @BindView(R.id.tv_recite_detail)
    TextView tvReciteDetail;
    @BindView(R.id.btn_again)
    Button btnAgain;
    @BindView(R.id.tv_detail)
    TextView tvDetail;

    //private boolean isPlaying = false;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initData() {
        String duration = getIntent().getStringExtra("duration");
        tvTotalTime.setText(duration);
        String content = getIntent().getStringExtra("content");
        tvReciteDetail.setText(content);

        float totalScore = getIntent().getFloatExtra("totalScore", 0);
        float integrityScore = getIntent().getFloatExtra("integrityScore", 0);
        float phone_score = getIntent().getFloatExtra("phone_score", 0);
        float fluency_score = getIntent().getFloatExtra("fluency_score", 0);
        float tone_score = getIntent().getFloatExtra("tone_score", 0);
        finalScore.setProgress(floatToInt(totalScore));
        tvIntegrity.setText(floatToInt(integrityScore) + "分");
        tvFluency.setText(floatToInt(fluency_score) + "分");
        tvAccuracy.setText(floatToInt(phone_score) + "分");
        tvStandard.setText(floatToInt(tone_score) + "分");
    }

    private void initView() {
        player = new MediaPlayer();
        player.setOnCompletionListener(listener);
        seekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    private int floatToInt(float score) {
        int i = (int) ((score * 10 + 5) / 10);
        return i;
    }

    @OnClick({R.id.tv_back_evaluate, R.id.iv_play_or_pause, R.id.btn_again, R.id.tv_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_evaluate:
                finish();
                break;
            case R.id.iv_play_or_pause:
                if (player.isPlaying()) {
                    //isPlaying = false;
                    btnVoice.setImageResource(R.drawable.play);
                    player.pause();
                    pausePosition = player.getCurrentPosition();
                    stopThread();
                } else {
                    //isPlaying = true;
                    btnVoice.setImageResource(R.drawable.pause);
                    playRecord();
                }
                break;
            case R.id.btn_again:
                //                if (ReciteActivity.count == 0) {
                //                    showTip("次数用尽");
                //                    return;
                //                }
                //ReciteActivity.count--;
                finish();
                break;
            case R.id.tv_detail:
                String detail = getIntent().getStringExtra("result");
                Bundle bundle = new Bundle();
                bundle.putString("detail", detail);
                ActivityUtils.startActivity(bundle, EvaluateDetailActivity.class);
                break;
        }
    }

    private void playRecord() {
        try {
            if (player == null) {
                return;
            }
            player.reset();
            String path = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/msc/ise.wav";
            //int currentProgress = player.getCurrentPosition();
            player.setDataSource(path);
            //player.seekTo(currentProgress);
            player.prepare();
            player.seekTo(pausePosition);
            player.start();
            startThread();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MediaPlayer.OnCompletionListener listener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            showTip("播放完毕");
            btnVoice.setImageResource(R.drawable.play);
            //seekBar.setProgress(seekBar.getMax());
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopThread();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    /**
     * 播放当前时间
     */
    private int pausePosition;

    private boolean isTrack = false;
    private SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            isTrack = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            isTrack = false;
            int progress = seekBar.getProgress();
            int duration = player.getDuration();
            pausePosition = duration * progress / seekBar.getMax();
            playRecord();
            btnVoice.setImageResource(R.drawable.pause);
        }
    };

    /**
     * 定义线程开关
     */
    private boolean isThread;
    /**
     * 定义线程
     */
    private MusicThread thread;

    /**
     * 歌曲调用开启线程
     */
    private void startThread() {

        if (thread == null) {
            isThread = true;
            thread = new MusicThread();
            thread.start();
        }

    }

    /**
     * 歌曲调用关闭线程
     */
    private void stopThread() {
        if (thread != null) {
            isThread = false;
            thread = null;
        }
    }

    /**
     * 歌曲时间线程
     */
    private class MusicThread extends Thread {

        @Override
        public void run() {

            // 优化 把内部类放在循环外面不要重复new对象
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // 1.获取歌曲时长
                    int musicTime = player.getDuration();
                    // 2. 获取歌曲当前时间
                    int musicplaytime = player.getCurrentPosition();
                    // 3.获取当前进度条
                    int currentProgress = seekBar.getProgress();
                    // 4.设置开始时间
                    DateFormat format = new SimpleDateFormat("mm:ss");
                    tvCurrentTime.setText(TimeUtils.millis2String(musicplaytime, format));
                    // 5.计算当前进度
                    int progress = musicplaytime * seekBar.getMax() / musicTime;
                    // 6.设置进度条
                    if (!isTrack) {
                        seekBar.setProgress(progress);
                    }

                }
            };

            while (isThread) {

                runOnUiThread(runnable);

                // 设置休眠
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
