package com.dofun.sxl.activity.xhz;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.FileUtil;
import com.dofun.sxl.util.HttpUtil;
import com.dofun.sxl.util.ImageUtil;
import com.dofun.sxl.util.MD5Util;
import com.dofun.sxl.util.UserImgUtils;
import com.dofun.sxl.view.DialogWaiting;
import com.hjq.permissions.Permission;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dofun.sxl.activity.xhz.UploadWorkImg.Photo_ZOOM;
import static com.dofun.sxl.activity.xhz.UploadWorkImg.TAKE_PICTURE;

public class WriteWordActivity extends BaseActivity {
    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.btn_camera)
    Button btnCamera;
    @BindView(R.id.tv_back_xhz)
    TextView tvBack;
    @BindView(R.id.tv_topic_score)
    TextView tvScore;
    @BindView(R.id.tv_recognize)
    TextView tvRecognize;
    @BindView(R.id.tv_surplus)
    TextView tvSurplus;
    @BindView(R.id.countdown_progress)
    SeekBar countdownProgress;

    private List<TopicDetail> topicDetails = new ArrayList<>();
    //    private UploadWorkImg helper;
    private int topicId;
    private DialogWaiting dialog;

    private int i = 0;
    private MyTimer myTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_word);
        ButterKnife.bind(this);

        //        helper = new UploadWorkImg(mActivity);
        initData();
        initView();
        checkPer(Permission.CAMERA);
        checkPer(Permission.WRITE_EXTERNAL_STORAGE);
        //checkPer(Permission.READ_EXTERNAL_STORAGE);
    }

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
        int homeworkId = getIntent().getIntExtra("homeworkId", 0);
        int fkId = getIntent().getIntExtra("fkId", 0);
        JSONObject param = new JSONObject();
        param.put("homeworkId", homeworkId);
        param.put("fkId", fkId);
        param.put("kind", 112);
        HttpUs.send(Deploy.queryTopicByCondition(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                topicDetails = JSONArray.parseArray(info.getData(), TopicDetail.class);
                String imgPath = Deploy.ImgURL + topicDetails.get(0).getDetail();
                Glide.with(mContext).load(imgPath).into(ivDetail);
                tvScore.setText(topicDetails.get(0).getFraction() + "");
                topicId = topicDetails.get(0).getId();
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        });
    }

    @OnClick({R.id.btn_camera, R.id.tv_back_xhz, R.id.tv_recognize})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_xhz:
                showMyDialog("若未上传作业，退出后需重新拍照上传");
                break;
            case R.id.btn_camera:
                //                helper.showSelectDialog();
                takeCamera();
                tvRecognize.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_recognize:
                dialog = DialogWaiting.build(this);
                getOCR();
                break;
        }
    }

    /**
     * 照相机拍的照片的文件
     **/
    File tempFile;

    private String picPath;

    /**
     * 打开相机，拍照图片
     */
    private void takeCamera() {
        picPath = ImageUtil.getAppTempDir() + File.separator + String.valueOf(System.currentTimeMillis() + ".jpg");
        tempFile = new File(picPath);
        if (tempFile.exists()) {
            tempFile.delete();
        }
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(mActivity, tempFile));
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    private Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.dofun.sxl.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //        if (resultCode == RESULT_OK) {
        //            //helper.onActivityResult(requestCode, resultCode, data);
        //        }
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {

                    if (Build.VERSION.SDK_INT >= 24) {
                        startPhotoZoom(mActivity, getImageContentUri(tempFile));
                    } else {
                        startPhotoZoom(mActivity, Uri.fromFile(tempFile));
                    }
                }
                break;
            case Photo_ZOOM:
                if (resultCode == RESULT_OK) {
                    if (ImageUtil.cropImageUri != null) {
                        uploadPic();
                    }
                }
                break;
        }
    }

    private String filePath = "";
    public static String pathName = "";

    /**
     * 图片上传
     */
    private void uploadPic() {

        File file = new File(UserImgUtils.getImageAbsolutePath(mActivity, ImageUtil.cropImageUri));
        pathName = UserImgUtils.getImageAbsolutePath(mActivity, ImageUtil.cropImageUri);
        //AnswerConstants.workImg = pathName;
        try {
            JSONObject param = new JSONObject();
            Bitmap bitmap = ImageUtils.getBitmap(file);
            param.put("file", UserImgUtils.Bitmap2StrByBase64(bitmap));
            param.put("type", "jpg");
            HttpUs.send(Deploy.getUploadFile(), param, saveCallback);
        } catch (com.alibaba.fastjson.JSONException e) {
            e.printStackTrace();
        }
    }

    private HttpUs.CallBackImp saveCallback = new HttpUs.CallBackImp() {
        @Override
        public void onSuccess(ResInfo info) {
            LogUtils.i(info.toString());
            JSONObject params = JSON.parseObject(info.getData());
            if (params.containsKey("fileName")) {
                Message msg = mHandler.obtainMessage();
                msg.obj = params;
                msg.what = 2;
                mHandler.sendMessage(msg);

                filePath = params.getString("fileName");
            } else {
                mHandler.sendEmptyMessage(3);
            }
        }

        @Override
        public void onFailure(ResInfo info) {
            LogUtils.i(info.toString());
            mHandler.sendEmptyMessage(3);
        }
    };


    /**
     * 7.0以上获取裁剪 Uri
     *
     * @param imageFile
     * @return
     */
    private Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = mActivity.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return mActivity.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 对图片进行裁剪
     *
     * @param fromFile
     */
    private void startPhotoZoom(Context context, Uri fromFile) {

        ImageUtil.cropImageUri = ImageUtil.createImagePathUri(context);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(fromFile, "image/*");
        intent.putExtra("crop", "true");
        //去黑边
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageUtil.cropImageUri);
        //设置为不返回数据
        intent.putExtra("return-data", false);
        mActivity.startActivityForResult(intent, Photo_ZOOM);

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (EmptyUtils.isNotEmpty(pathName)) {
            tvRecognize.setVisibility(View.VISIBLE);
        } else {
            tvRecognize.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showMyDialog("若未上传作业，退出后需重新拍照上传");
        }
        return true;
    }

    private String result = "";
    private String BASE_URL = "http://webapi.xfyun.cn/v1/service/v1/ocr/handwriting";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    result = (String) msg.obj;
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    bundle.putString("filePath", filePath);
                    bundle.putString("topicId", String.valueOf(topicId));
                    ActivityUtils.startActivity(bundle, RecognizeActivity.class);
                    finish();
                    break;
                case 1:
                    result = (String) msg.obj;
                    showTip("识别出错");
                    break;
                case 2:
                    showTip("上传成功");
                    break;
                case 3:
                    showTip("上传失败，请重试");
                    break;
            }
            if (dialog != null) {
                dialog.dismiss();
            }
            //            //上传成功后删掉图片
            //            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures";
            //            FileUtil.delete(dirPath);
        }
    };

    private void getOCR() {
        try {
            String appKey = "a96535545109fbc5f1da844449b6aaee";
            String X_Appid = "5b39f3fc";
            String X_CurTime = String.valueOf(System.currentTimeMillis() / 1000L);
            String param = "{\"language\":\"" + "cn|en" + "\"}";
            String X_Param = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
            String X_CheckSum = MD5Util.encodeMD5(appKey + X_CurTime + X_Param);

            String imgPath = pathName;
            byte[] imageArray = FileUtil.read(imgPath);
            String imageBase64 = new String(Base64.encodeBase64(imageArray), "UTF-8");
            final String urlEncode = URLEncoder.encode(imageBase64, "UTF-8");

            final Map<String, String> header = new HashMap<String, String>();
            header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            header.put("X-Param", X_Param);
            header.put("X-CurTime", X_CurTime);
            header.put("X-CheckSum", X_CheckSum);
            header.put("X-Appid", X_Appid);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.setTitle("识别中...");
                            dialog.show();
                        }
                    });

                    String result = HttpUtil.doPost1(BASE_URL, header, "image=" + urlEncode);
                    Log.i("OCR WebAPI 接口调用结果：", result);
                    try {
                        org.json.JSONObject object = new org.json.JSONObject(result);
                        String code = object.optString("code");
                        if (!code.equals("0")) {
                            String desc = object.optString("desc");
                            Message msg = Message.obtain(mHandler);
                            msg.what = 1;
                            msg.obj = "code:" + code + ",desc:" + desc;
                            mHandler.sendMessage(msg);
                        } else {
                            org.json.JSONObject data = object.optJSONObject("data");
                            org.json.JSONArray block = data.optJSONArray("block");
                            String finalContent = "";
                            for (int i = 0; i < block.length(); i++) {
                                org.json.JSONObject jsonObject = block.optJSONObject(i);
                                org.json.JSONArray line = jsonObject.optJSONArray("line");
                                for (int j = 0; j < line.length(); j++) {
                                    org.json.JSONObject obj = line.optJSONObject(j);
                                    org.json.JSONArray word = obj.optJSONArray("word");
                                    for (int k = 0; k < word.length(); k++) {
                                        org.json.JSONObject jt = word.optJSONObject(k);
                                        String content = jt.optString("content");
                                        finalContent += content + " ";
                                    }
                                }
                            }
                            Message msg = Message.obtain(mHandler);
                            msg.what = 0;
                            msg.obj = finalContent;
                            mHandler.sendMessage(msg);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
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
            countdownProgress.setProgress(20 * 60 - i);
            DateFormat format = new SimpleDateFormat("mm:ss");
            String surplusTime = TimeUtils.millis2String(millisUntilFinished, format);
            String time[] = surplusTime.split(":");
            tvSurplus.setText(time[0] + "分" + time[1] + "秒");
        }

        @Override
        public void onFinish() {
            btnCamera.performClick();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myTimer != null) {
            myTimer.cancel();
        }
    }
}
