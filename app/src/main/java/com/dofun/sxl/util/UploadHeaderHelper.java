package com.dofun.sxl.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dofun.sxl.Deploy;
import com.dofun.sxl.R;
import com.dofun.sxl.activity.BaseActivity;
import com.dofun.sxl.bean.EventBusBean;
import com.dofun.sxl.constant.EventConstants;
import com.dofun.sxl.http.HttpUs;
import com.dofun.sxl.http.ResInfo;
import com.dofun.sxl.view.CircleImageView;
import com.dofun.sxl.view.DialogWaiting;
import com.tandong.sa.eventbus.EventBus;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class UploadHeaderHelper {

    private BaseActivity mActivity;


    private CircleImageView mImgHeader;

    /**
     * 3代表人物头像裁剪
     **/
    private int zoomType = 3;

    /**
     * 照相机拍的照片的文件
     **/
    File tempFile;

    String fileName;

    private String picPath;

    private DialogWaiting waiting;

    public static final int TAKE_PICTURE = 99;
    public static final int Photo_PICTURE = 98;
    public static final int Photo_ZOOM = 100;

    public UploadHeaderHelper(BaseActivity mContext, CircleImageView mImgHeader) {
        super();
        this.mActivity = mContext;
        this.mImgHeader = mImgHeader;
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: //上传图片
                    ToastUtils.showShort("图片上传成功");
                    JSONObject params = (JSONObject) msg.obj;
                    getHttp(params);

                    break;
                case 2: //上传图片失败，关闭提示框

                    ToastUtils.showShort("上传失败，请重试");

                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 点击按钮，弹出选中图片的dialog
     */
    public void showSelectDialog() {
        new ActionSheetDialog(mActivity)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                                takeCamera();

                            }
                        })
                .addSheetItem("从相册中选取", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {

                                takePhoto();

                            }
                        }).show();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

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
            case Photo_PICTURE:
                //图片选择器的返回
                if (resultCode != RESULT_OK) {
                    return;
                }

                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(mActivity, data.getData());

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

    /**
     * 图片上传
     */
    private void uploadPic() {

        File file = new File(UserImgUtils.getImageAbsolutePath(mActivity, ImageUtil.cropImageUri));
        //String pathName = UserImgUtils.getImageAbsolutePath(mActivity, ImageUtil.cropImageUri);
        showDialog();

        try {
            JSONObject param = new JSONObject();
            Bitmap bitmap = /*BitmapFactory.decodeFile(pathName)*/ImageUtils.getBitmap(file);
            param.put("file", UserImgUtils.Bitmap2StrByBase64(bitmap));
            param.put("type", "jpg");
            HttpUs.send(Deploy.getUploadFile(), param, saveCallback);
        } catch (JSONException e) {
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
                msg.what = 1;
                mHandler.sendMessage(msg);
                //上传成功后删掉图片
                //                String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures";
                //                FileUtil.delete(dirPath);
            } else {
                mHandler.sendEmptyMessage(2);
            }
            closeDialog();
        }

        @Override
        public void onFailure(ResInfo info) {
            LogUtils.i(info.toString());
            mHandler.sendEmptyMessage(2);
            closeDialog();
        }
    };


    private void showDialog() {

        if (mActivity != null) {

            waiting = DialogWaiting.show(mActivity);
        }

    }

    private void closeDialog() {

        if (waiting != null && waiting.isShowing()) {
            waiting.dismiss();
        }

    }

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
        mActivity.startActivityForResult(openCameraIntent, TAKE_PICTURE);
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

    /**
     * 打开相册，选择图片
     */
    private void takePhoto() {

        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(intent, Photo_PICTURE);
        mActivity.overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
    }

    private void getHttp(JSONObject params) {
        params.put("avatarUrl", params.getString("fileName"));
        params.put("roleType", "1");
        params.remove("fileName");
        HttpUs.send(Deploy.getModify(), params, new HttpUs.CallBackImp() {
            @Override
            public void onSuccess(ResInfo info) {
                LogUtils.i(info.toString());
                ToastUtils.showShort("头像修改成功");
                JSONObject data = new JSONObject();
                data.put("user", JSON.parseObject(info.getData()));
                mActivity.setUserInfo(data);
                EventBus.getDefault().post(new EventBusBean<String>(0, EventConstants.USER_IMG, ""));
            }

            @Override
            public void onFailure(ResInfo info) {
                LogUtils.i(info.toString());
                ToastUtils.showShort(info.getMsg());
            }
        });
    }

}
