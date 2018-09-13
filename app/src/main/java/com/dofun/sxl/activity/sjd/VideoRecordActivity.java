package com.dofun.sxl.activity.sjd;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoRecordActivity extends BaseActivity {

    @BindView(R.id.tv_back_video)
    TextView tvBack;
    @BindView(R.id.camera_view)
    JCameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);
        ButterKnife.bind(this);

        initCameraView();
    }

    private void initCameraView() {
        //设置视频保存路径
        cameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "sxl");

        //设置只能录像或只能拍照或两种都可以（默认两种都可以）
        cameraView.setFeatures(JCameraView.BUTTON_STATE_BOTH);

        //设置视频质量
        cameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);

        //设置监听
        //成功监听
        cameraView.setJCameraLisenter(jCameraListener);
        //错误监听
        cameraView.setErrorLisenter(errorListener);
        //左键监听
        cameraView.setLeftClickListener(clickListener);
    }

    private JCameraListener jCameraListener = new JCameraListener() {
        @Override
        public void captureSuccess(Bitmap bitmap) {
            //获取图片bitmap
            Log.i("JCameraView", "bitmap = " + bitmap.getWidth());
        }

        @Override
        public void recordSuccess(String url, Bitmap firstFrame) {
            //获取视频路径
            Log.i("CJT", "url = " + url);
        }
    };

    private ErrorListener errorListener = new ErrorListener() {
        @Override
        public void onError() {
            //打开Camera失败回调
            Log.i("CJT", "open camera error");
        }

        @Override
        public void AudioPermissionError() {
            //没有录取权限回调
            Log.i("CJT", "AudioPermissionError");
            showTip("无录取权限");
        }
    };

    private ClickListener clickListener = new ClickListener() {
        @Override
        public void onClick() {
            finish();
        }
    };

    @OnClick(R.id.tv_back_video)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.onPause();
    }
}
