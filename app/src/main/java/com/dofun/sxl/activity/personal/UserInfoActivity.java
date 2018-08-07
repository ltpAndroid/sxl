package com.dofun.sxl.activity.personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.bean.UserInfo;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.util.HintDiaUtils;
import com.dofun.sxl.util.SPUtils;
import com.dofun.sxl.util.UserImgUtils;
import com.dofun.sxl.view.CircleImageView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.tv_back_userInfo)
    TextView tvBack;
    @BindView(R.id.user_header)
    CircleImageView userHeader;
    @BindView(R.id.nickname_layout)
    RelativeLayout nicknameLayout;
    @BindView(R.id.gender_layout)
    RelativeLayout genderLayout;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_save)
    TextView tvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        setStateBarColor();

    }

    @OnClick({R.id.tv_back_userInfo, R.id.user_header, R.id.nickname_layout, R.id.gender_layout, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back_userInfo:
                finish();
                break;
            case R.id.user_header:
                choosePic();
                break;
            case R.id.nickname_layout:
                showMyDialog(tvNickname);
                break;
            case R.id.gender_layout:
                setGender();
                break;
            case R.id.tv_save:
                saveUserInfo();
                break;
        }
    }

    private void saveUserInfo() {
        String sexStr = tvGender.getText().toString();
        int sex;
        if (sexStr.equals("男")) {
            sex = 1;
        } else {
            sex = 2;
        }
        String nickname = tvNickname.getText().toString();
        JSONObject params = new JSONObject();
        params.put("sex", sex);
        params.put("nickname", nickname);
        HttpUs.send(Deploy.getModify(), params, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());

                JSONObject data = new JSONObject();
                data.put("user", JSON.parseObject(info.getData()));
                setUserInfo(data);
                //initUserInfo();

                HintDiaUtils.createDialog(mContext).showSucceedDialog("保存成功");
                new Timer().schedule(new TimerTask() {
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

    private void choosePic() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .imageSpanCount(4)
                .previewImage(true)
                .isCamera(true)
                .imageFormat(PictureMimeType.JPEG)
                .compress(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    Glide.with(this).load(selectList.get(0).getPath()).into(userHeader);

                    upLoadImg(selectList.get(0));
            }
        }
    }

    private void upLoadImg(LocalMedia localMedia) {
        Bitmap bitmap = UserImgUtils.getBitmapFromPath(localMedia.getPath());
        if (bitmap == null) {
            showTip("选择上传照片");
            return;
        }
        JSONObject param = new JSONObject();
        param.put("type", "jpg");
        param.put("file", UserImgUtils.Bitmap2StrByBase64(bitmap));
        HttpUs.send(Deploy.getUploadFile(), param, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                PictureFileUtils.deleteCacheDirFile(mContext);

                JSONObject params = JSON.parseObject(info.getData());
                if (params.containsKey("fileName")) {
                    params.put("avatarUrl", params.getString("fileName"));
                    params.remove("fileName");
                    HttpUs.send(Deploy.getModify(), params, new HttpUs.CallBackImp() {
                        @Override
                        public void onSuccess(ResInfo info) {
                            LogUtils.i(info.toString());
                            showTip("头像修改成功");
                            JSONObject data = new JSONObject();
                            data.put("user", JSON.parseObject(info.getData()));
                            setUserInfo(data);
                            initUserInfo();
                        }

                        @Override
                        public void onFailure(ResInfo info) {
                            LogUtils.i(info.toString());
                            showTip(info.getMsg());
                        }
                    });
                }
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                showTip(info.getMsg());
            }
        }, mContext, "正在上传图片");
    }

    private void initUserInfo() {
        UserInfo userInfo = (UserInfo) SPUtils.getBaseBean(SPUtils.USER, UserInfo.class);
        if (userInfo == null) {
            return;
        }

        String nickname = userInfo.getNickname() == null ? "昵称未设置" : userInfo.getNickname();
        tvNickname.setText(nickname);

        if (!StringUtils.isEmpty(userInfo.getAvatarUrl())) {
            Glide.with(this).load(userInfo.getAvatarUrl()).into(userHeader);
        } else {
            Glide.with(this).load(R.drawable.rank_header).into(userHeader);
        }

        String sexStr = userInfo.getSexStr() == null ? "性别未设置" : userInfo.getSexStr();
        tvGender.setText(sexStr);
    }

    private void setGender() {
        new TDialog.Builder(getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_change_avatar)
                .setScreenWidthAspect(this, 1.0f)
                .setGravity(Gravity.BOTTOM)
                .addOnClickListener(R.id.tv_male, R.id.tv_female, R.id.tv_cancel)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        switch (view.getId()) {
                            case R.id.tv_male:
                                tDialog.dismiss();
                                tvGender.setText("男");
                                break;
                            case R.id.tv_female:
                                tDialog.dismiss();
                                tvGender.setText("女");
                                break;
                            case R.id.tv_cancel:
                                tDialog.dismiss();
                                break;
                        }
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUserInfo();
    }
}