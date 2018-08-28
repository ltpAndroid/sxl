package com.dofun.sxl.activity.xhz;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.bean.TopicDetail;
import com.dofun.sxl.constant.AnswerConstants;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.FileUtil;
import com.dofun.sxl.util.HttpUtil;
import com.dofun.sxl.util.MD5Util;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WriteWordActivity extends BaseActivity {
    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.btn_camera)
    Button btnCamera;
    @BindView(R.id.tv_back_xhz)
    TextView tvBack;
    @BindView(R.id.tv_topic_score)
    TextView tvScore;
    @BindView(R.id.iv_work)
    ImageView ivWork;
    @BindView(R.id.tv_recognize)
    TextView tvRecognize;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;

    private List<TopicDetail> topicDetails = new ArrayList<>();
    private UploadWorkImg helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_word);
        ButterKnife.bind(this);
        setStateBarColor();

        helper = new UploadWorkImg(mActivity, ivWork);
        initData();
        initView();
    }

    private void initView() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/song.ttf");
        tv1.setText("习字指导");
        tv1.setTypeface(typeface);
        tv2.setText("我的临摹");
        tv2.setTypeface(typeface);
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
                helper.showSelectDialog();
                tvRecognize.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_recognize:
                getOCR();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            helper.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(mContext).load(AnswerConstants.workImg).into(ivWork);
        if (EmptyUtils.isNotEmpty(AnswerConstants.workImg)) {
            tvRecognize.setVisibility(View.VISIBLE);
        } else {
            tvRecognize.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AnswerConstants.workImg = "";
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
                    ActivityUtils.startActivity(bundle, RecognizeActivity.class);
                    break;
                case 1:
                    result = (String) msg.obj;
                    showTip("识别出错");
                    break;
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

            String imgPath = AnswerConstants.workImg;
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
}
